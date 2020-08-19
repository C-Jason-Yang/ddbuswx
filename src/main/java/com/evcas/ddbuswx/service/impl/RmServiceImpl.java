package com.evcas.ddbuswx.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.thread.ThreadUtil;
import com.alibaba.fastjson.JSONObject;
import com.evcas.ddbuswx.common.utils.JsonTools;
import com.evcas.ddbuswx.common.utils.PositionUtil;
import com.evcas.ddbuswx.common.utils.StringUtil;
import com.evcas.ddbuswx.dao.*;
import com.evcas.ddbuswx.entity.PlatformAccount;
import com.evcas.ddbuswx.entity.bus.init.BusLineListBean;
import com.evcas.ddbuswx.entity.bus.init.Param;
import com.evcas.ddbuswx.entity.bus.init.Result;
import com.evcas.ddbuswx.entity.bus.init.SiteListBean;
import com.evcas.ddbuswx.mapper.PlatformAccountMapper;
import com.evcas.ddbuswx.model.SpaceObject;
import com.evcas.ddbuswx.model.mongo.Bus;
import com.evcas.ddbuswx.model.mongo.BusLine;
import com.evcas.ddbuswx.model.mongo.BusShift;
import com.evcas.ddbuswx.model.mongo.BusStation;
import com.evcas.ddbuswx.service.IRmService;
import com.google.common.base.Strings;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by noxn on 2018/1/10.
 */
@Log4j2
@SuppressWarnings("unchecked")
@Service
public class RmServiceImpl implements IRmService {
    @Autowired
    private IRmDAO iRmDAO;
    @Autowired
    private IBusDAO iBusDAO;
    @Autowired
    private IBusLineDAO iBusLineDAO;
    @Autowired
    private IBusShiftDAO iBusShiftDAO;
    @Autowired
    private IBusStationDAO iBusStationDAO;
    @Autowired
    private PlatformAccountMapper platformAccountMapper;

    private static final ThreadPoolExecutor executor = ThreadUtil.newExecutor(10, 30);

    @Override
    public void initializeRmBusRoute() {
        List<PlatformAccount> platformAccountList = platformAccountMapper.findAllPlatformAccount();
        if (platformAccountList != null && platformAccountList.size() > 0) {
            final CountDownLatch countDownLatch = new CountDownLatch(platformAccountList.size());
//            platformAccountList.forEach(platformAccount -> executor.execute(() -> {
//                run(platformAccount);
//                countDownLatch.countDown();
//            }));
            platformAccountList.forEach(platformAccount -> CompletableFuture.runAsync(() -> {
                run(platformAccount);
                countDownLatch.countDown();
            }, executor));
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void run(PlatformAccount platformAccount) {
        String threadName = Thread.currentThread().getName();
        long beginT = System.currentTimeMillis();
        log.info("{}  {}  ======== >>>>> 开始！time： {}  ", threadName, platformAccount.getUserName(), new DateTime().toString());
        String regKey = iRmDAO.rmLogin(platformAccount.getUserName(), platformAccount.getPassword());
        if (!Strings.isNullOrEmpty(regKey)) {
            String queryBusLineJsonResult = iRmDAO.getBusLineAndStation(regKey);
            if (!StringUtil.isEmpty(queryBusLineJsonResult)) {
                Result result = JSONObject.parseObject(queryBusLineJsonResult).toJavaObject(Result.class);
                Param param = JSONObject.parseObject(result.getParam()).toJavaObject(Param.class);
                addBusLineList(param, platformAccount.getAreaMark(), regKey);
                addBusInfoList(regKey, platformAccount.getAreaMark());
            }
        }
        long endT = System.currentTimeMillis();
        log.info("{}   {}  ======== >>>>> 完成！time： {}  时常：{}", threadName, platformAccount.getUserName(), new DateTime().toString(), endT - beginT);
    }

    /**
     * 根据区域id清空区域公交线路
     *
     * @param areaId
     */
    private void clearBusLineOldData(String areaId) {
        iBusLineDAO.deleteBusLineByAreaId(areaId, "RM");
        iBusStationDAO.deleteBusStationByAreaId(areaId, "RM");
        iBusDAO.deleteBusInfoByAreaId(areaId, "RM");
        iBusShiftDAO.deleteBusShift(areaId, "RM");
    }

    /**
     * 添加公交线路
     */
    @SuppressWarnings("Duplicates")
    private void addBusLineList(Param param, String areaId, String regKey) {
        if (param.getErrorCode() != 0) {
            throw new RuntimeException("初始化公交数据异常！");
        }
        List<BusLineListBean> busLineList = param.getBusLineList();
        if (!CollUtil.isEmpty(busLineList)) {
            //删除旧的公交线路数据
            clearBusLineOldData(areaId);
            //循环添加公交线路数据
            List<BusLine> busLineModelList = new ArrayList<BusLine>();
            for (BusLineListBean line : busLineList) {
                if ("88".equals(line.getLineCode())) {
                    continue;
                }
                BusLine busLine = new BusLine("RM");
                busLine.setAreaCode(areaId);
                busLine.setId(line.getId().toString());
                busLine.setReMark(line.getRemark());
                busLine.setGroupId(line.getGroupId());
                busLine.setAreaid(areaId);
                busLine.setLineCode(line.getLineCode());
                busLine.setSendType(line.getSendType());
                String lineName = line.getLineName().trim();
                if (!lineName.substring(lineName.length() - 1).equals("路")) {
                    busLine.setSortLineName(lineName);
                    lineName = lineName + "路";
                } else {
                    busLine.setSortLineName(lineName.substring(0, lineName.length() - 1));
                }
                busLine.setLineName(lineName);
                busLine.setIsannulus(line.getIsAnnulus());
                busLine.setLineLength(line.getLineLength());
                busLine.setLimitSpeed(line.getLimitSpeed());
                busLine.setIsoperations(line.getIsOperations());
                busLine.setScheduleMode(line.getScheduleMode());
                busLine.setIslongtransport(line.getIsLongTransport());
                //默认运营时间6:30-19:30
                busLine.setUpLinkStartTime("6:30");
                busLine.setUpLinkEndTime("19:30");
                //添加站点信息
                addStationList(line, areaId);
                //添加线路班次信息
                addBusLineShift(regKey, busLine);
                busLineModelList.add(busLine);
            }
            iBusLineDAO.addBusLineList(busLineModelList);
        }
    }

    /**
     * 添加公交站点
     */
    @SuppressWarnings("Duplicates")
    private void addStationList(BusLineListBean line, String areaId) {
        try {
            List<SiteListBean> siteList = line.getSiteList();
            if (!CollUtil.isEmpty(siteList)) {
                //获取线路下站点数据
                List<BusStation> busStationList = new ArrayList<BusStation>();
                for (SiteListBean listBean : siteList) {
                    BusStation busStation = new BusStation("RM");
                    busStation.setSiteCode(listBean.getSiteCode());
                    busStation.setSiteName(listBean.getSiteName());
                    if (listBean.getLongitude() == 0.0) {
                        busStation.setLongitude(88.0197931975);
                        busStation.setLatitude(40.7465368828);
                    } else {
                        busStation.setLongitude(listBean.getLongitude());
                        busStation.setLatitude(listBean.getLatitude());
                        //添加百度经纬度
                        Double[] coordinates = PositionUtil.gcj02tobd09(listBean.getLongitude(), listBean.getLatitude());
                        busStation.setBdlog(coordinates[0]);
                        busStation.setBdlat(coordinates[1]);
                        //2D 索引
                        SpaceObject loc = new SpaceObject();
                        loc.setType("Point");
                        loc.setCoordinates(coordinates);
                        busStation.setLocation(loc);
                    }
                    busStation.setDirection(listBean.getDirection());
                    busStation.setSitenum(listBean.getSiteNum());
                    //站点所属线路信息
                    busStation.setAreaid(areaId);
                    busStation.setLineCode(line.getLineCode());
                    busStation.setLineName(line.getLineName());
                    busStation.setLineId(line.getId().toString());
                    busStationList.add(busStation);
                }
                iBusStationDAO.addBusStation(busStationList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加车辆信息
     *
     * @param regKey
     * @param areaId
     */
    private void addBusInfoList(String regKey, String areaId) {
        try {
            List<Map<String, Object>> busInfoList = iRmDAO.getBusInfoList(regKey);
            if (busInfoList != null && busInfoList.size() > 0) {
                List<Bus> busList = new ArrayList<Bus>();
                for (Map<String, Object> stringObjectMap : busInfoList) {
                    Bus bus = new Bus("RM");
                    bus.setVehicleid(Integer.valueOf(stringObjectMap.get("VEHICLEID").toString().split("\\.")[0]));
                    bus.setVehiclelicense(stringObjectMap.get("VEHICLELICENSE").toString());
                    bus.setAreaid(areaId);
                    busList.add(bus);
                }
                iBusDAO.addBusInfoList(busList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addBusLineShift(String regKey, BusLine busLine) {
        String busLineShiftStr = iRmDAO.getBusShiftList(regKey, Integer.valueOf(busLine.getLineCode()));
        if (!StringUtil.isEmpty(busLineShiftStr)) {
            Map<String, Object> busShiftInfoJsonMap = JsonTools.gson.fromJson(busLineShiftStr, Map.class);
            String busShiftInfoParamJsonStr = busShiftInfoJsonMap.get("PARAM").toString();
            Map<String, Object> busShiftInfoParamJsonMap = JsonTools.gson.fromJson(busShiftInfoParamJsonStr, Map.class);
            List<Map<String, Object>> busShiftInfoList = (List<Map<String, Object>>) busShiftInfoParamJsonMap.get("PLANS");
            //当公交线路班次信息不为空时
            if (busShiftInfoList != null && busShiftInfoList.size() > 0) {
                List<BusShift> busLineShiftList = new ArrayList<BusShift>();
                //循环公交班次信息
                for (Map<String, Object> tempBusShiftInfoMap : busShiftInfoList) {
                    BusShift busShift = new BusShift();
                    //线路ID
                    busShift.setLineId(busLine.getId().toString());
                    //线路编码
                    busShift.setLineCode(busLine.getLineCode());
                    //线路名称
                    busShift.setLineName(busLine.getLineName());
                    //区域id
                    busShift.setAreaid(busLine.getAreaid());
                    busShift.setFromSys("RM");

                    busShift.setVehicleid(Integer.valueOf(tempBusShiftInfoMap.get("VEHICLEID").toString().split("\\.")[0]));
                    busShift.setVehicleno(tempBusShiftInfoMap.get("VEHICLENO").toString());
                    busShift.setBiztype(Integer.valueOf(tempBusShiftInfoMap.get("BIZTYPE").toString().split("\\.")[0]));
                    busShift.setStime(Integer.valueOf(tempBusShiftInfoMap.get("STIME").toString().split("\\.")[0]));
                    busShift.setEtime(Integer.valueOf(tempBusShiftInfoMap.get("ETIME").toString().split("\\.")[0]));

                    busLineShiftList.add(busShift);
                }
                iBusShiftDAO.addBufShiftList(busLineShiftList);
            }
        }
    }
}