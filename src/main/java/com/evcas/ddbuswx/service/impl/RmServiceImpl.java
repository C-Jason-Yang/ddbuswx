package com.evcas.ddbuswx.service.impl;

import com.evcas.ddbuswx.common.utils.BaiDuMapUtil;
import com.evcas.ddbuswx.common.utils.JsonTools;
import com.evcas.ddbuswx.common.utils.StringUtil;
import com.evcas.ddbuswx.dao.*;
import com.evcas.ddbuswx.entity.PlatformAccount;
import com.evcas.ddbuswx.mapper.PlatformAccountMapper;
import com.evcas.ddbuswx.model.*;
import com.evcas.ddbuswx.model.mongo.Bus;
import com.evcas.ddbuswx.model.mongo.BusLine;
import com.evcas.ddbuswx.model.mongo.BusShift;
import com.evcas.ddbuswx.model.mongo.BusStation;
import com.evcas.ddbuswx.service.IRmService;
import com.google.common.base.Strings;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by noxn on 2018/1/10.
 */
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


    @Override
    public void initializeRmBusRoute() {
        List<PlatformAccount> platformAccountList = platformAccountMapper.findAllPlatformAccount();
        if (platformAccountList != null && platformAccountList.size() > 0) {
            for (PlatformAccount platformAccount : platformAccountList) {
                String regKey = iRmDAO.rmLogin(platformAccount.getUserName(), platformAccount.getPassword());
                if (!Strings.isNullOrEmpty(regKey)) {
                    String queryBusLineJsonResult = iRmDAO.getBusLineAndStation(regKey);
                    if (!StringUtil.isEmpty(queryBusLineJsonResult)) {
                        addBusLineList(queryBusLineJsonResult, platformAccount.getAreaMark(), regKey);
                        addBusInfoList(regKey, platformAccount.getAreaMark());
                    }
                }
            }
        }
//        iRmDAO.rmLogin(SystemParameter.YS_BUS_ACCOUNT, SystemParameter.YS_BUS_PASSWORD);
//        iRmDAO.rmLogin(SystemParameter.TH_BUS_ACCOUNT, SystemParameter.TH_BUS_PASSWORD);
//        iRmDAO.rmLogin(SystemParameter.TC_BUS_ACCOUNT, SystemParameter.TC_BUS_PASSWORD);
//        iRmDAO.rmLogin(SystemParameter.HZ_BUS_ACCOUNT, SystemParameter.HZ_BUS_PASSWORD);
//        iRmDAO.rmLogin(SystemParameter.XJ_BUS_ACCOUNT, SystemParameter.XJ_BUS_PASSWORD);
//        iRmDAO.rmLogin(SystemParameter.WC_BUS_ACCOUNT, SystemParameter.WC_BUS_PASSWORD);
//
//        //判断  颍上  公交管理员 regkey
//        if (SystemParameter.BUS_SYSTEM_REG_KEY_MAP.get(SystemParameter.YS_BUS_ACCOUNT) == null || SystemParameter.BUS_SYSTEM_REG_KEY_MAP.get(SystemParameter.YS_BUS_ACCOUNT) == "") {
//        }
//        //判断  太和  公交管理员 regkey
//        if (SystemParameter.BUS_SYSTEM_REG_KEY_MAP.get(SystemParameter.TH_BUS_ACCOUNT) == null || SystemParameter.BUS_SYSTEM_REG_KEY_MAP.get(SystemParameter.TH_BUS_ACCOUNT) == "") {
//        }
//        //判断  屯昌  公交管理员 regkey
//        if (SystemParameter.BUS_SYSTEM_REG_KEY_MAP.get(SystemParameter.TC_BUS_ACCOUNT) == null || SystemParameter.BUS_SYSTEM_REG_KEY_MAP.get(SystemParameter.TC_BUS_ACCOUNT) == "") {
//        }
//        //判断  徽州  公交管理员 regkey
//        if (SystemParameter.BUS_SYSTEM_REG_KEY_MAP.get(SystemParameter.HZ_BUS_ACCOUNT) == null || SystemParameter.BUS_SYSTEM_REG_KEY_MAP.get(SystemParameter.HZ_BUS_ACCOUNT) == "") {
//        }
//
////        查询太和公交线路数据
//        String queryThBusLineJsonResult = iRmDAO.getBusLineAndStation(SystemParameter.BUS_SYSTEM_REG_KEY_MAP.get(SystemParameter.TH_BUS_ACCOUNT));
//        //查询颍上公交线路数据
//        String queryYsBusLineJsonResult = iRmDAO.getBusLineAndStation(SystemParameter.BUS_SYSTEM_REG_KEY_MAP.get(SystemParameter.YS_BUS_ACCOUNT));
//        //查询屯昌公交线路数据
//        String queryTcBusLineJsonResult = iRmDAO.getBusLineAndStation(SystemParameter.BUS_SYSTEM_REG_KEY_MAP.get(SystemParameter.TC_BUS_ACCOUNT));
//        //查询徽州公交线路数据
//        String queryHzBusLineJsonResult = iRmDAO.getBusLineAndStation(SystemParameter.BUS_SYSTEM_REG_KEY_MAP.get(SystemParameter.HZ_BUS_ACCOUNT));
//        //查询辛集公交线路数据
//        String queryXjBusLineJsonResult = iRmDAO.getBusLineAndStation(SystemParameter.BUS_SYSTEM_REG_KEY_MAP.get(SystemParameter.XJ_BUS_ACCOUNT));
//        //查询文昌公交线路数据
//        String queryWcBusLineJsonResult = iRmDAO.getBusLineAndStation(SystemParameter.BUS_SYSTEM_REG_KEY_MAP.get(SystemParameter.WC_BUS_ACCOUNT));
//
//        if (!StringUtil.isEmpty(queryThBusLineJsonResult)) {
//            addBusLineList(queryThBusLineJsonResult, "TH", SystemParameter.BUS_SYSTEM_REG_KEY_MAP.get(SystemParameter.TH_BUS_ACCOUNT));
//        }
//        if (!StringUtil.isEmpty(queryYsBusLineJsonResult)) {
//            addBusLineList(queryYsBusLineJsonResult, "YS", SystemParameter.BUS_SYSTEM_REG_KEY_MAP.get(SystemParameter.YS_BUS_ACCOUNT));
//        }
//        if (!StringUtil.isEmpty(queryTcBusLineJsonResult)) {
//            addBusLineList(queryTcBusLineJsonResult, "TC", SystemParameter.BUS_SYSTEM_REG_KEY_MAP.get(SystemParameter.TC_BUS_ACCOUNT));
//        }
//        if (!StringUtil.isEmpty(queryHzBusLineJsonResult)) {
//            addBusLineList(queryHzBusLineJsonResult, "HZ", SystemParameter.BUS_SYSTEM_REG_KEY_MAP.get(SystemParameter.HZ_BUS_ACCOUNT));
//        }
//        if (!StringUtil.isEmpty(queryXjBusLineJsonResult)) {
//            addBusLineList(queryXjBusLineJsonResult, "XJ", SystemParameter.BUS_SYSTEM_REG_KEY_MAP.get(SystemParameter.XJ_BUS_ACCOUNT));
//        }
//        if (!StringUtil.isEmpty(queryWcBusLineJsonResult)) {
//            addBusLineList(queryWcBusLineJsonResult, "WC", SystemParameter.BUS_SYSTEM_REG_KEY_MAP.get(SystemParameter.WC_BUS_ACCOUNT));
//        }
//
////        添加车辆数据
//        addBusInfoList(SystemParameter.BUS_SYSTEM_REG_KEY_MAP.get(SystemParameter.TH_BUS_ACCOUNT), "TH");
//        addBusInfoList(SystemParameter.BUS_SYSTEM_REG_KEY_MAP.get(SystemParameter.YS_BUS_ACCOUNT), "YS");
//        addBusInfoList(SystemParameter.BUS_SYSTEM_REG_KEY_MAP.get(SystemParameter.TC_BUS_ACCOUNT), "TC");
//        addBusInfoList(SystemParameter.BUS_SYSTEM_REG_KEY_MAP.get(SystemParameter.HZ_BUS_ACCOUNT), "HZ");
//        addBusInfoList(SystemParameter.BUS_SYSTEM_REG_KEY_MAP.get(SystemParameter.XJ_BUS_ACCOUNT), "XJ");
//        addBusInfoList(SystemParameter.BUS_SYSTEM_REG_KEY_MAP.get(SystemParameter.WC_BUS_ACCOUNT), "WC");
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
     *
     * @param busLineJsonStr
     * @param areaId
     */
    @SuppressWarnings("Duplicates")
    private void addBusLineList(String busLineJsonStr, String areaId, String regKey) {
        Map<String, Object> lineSiteResultMap = JsonTools.gson.fromJson(busLineJsonStr, Map.class);
        Map<String, Object> lineSiteResultParamMap = JsonTools.gson.fromJson(lineSiteResultMap.get("PARAM").toString(), Map.class);
        List<Map<String, Object>> busLineList = (List<Map<String, Object>>) lineSiteResultParamMap.get("BUSLINELIST");
        if (busLineList != null && busLineList.size() > 0) {
            //删除旧的公交线路数据
            clearBusLineOldData(areaId);
            //循环添加公交线路数据
            List<BusLine> busLineModelList = new ArrayList<BusLine>();
            for (Map<String, Object> stringObjectMap : busLineList) {
                if (stringObjectMap.get("LINECODE").equals("88"))
                    continue;
                BusLine busLine = new BusLine("RM");
                busLine.setAreaCode(areaId);
                busLine.setId(stringObjectMap.get("ID").toString().split("\\.")[0]);
                busLine.setReMark(String.valueOf(stringObjectMap.get("REMARK")));
                busLine.setGroupId(Integer.valueOf(String.valueOf(stringObjectMap.get("GROUPID")).split("\\.")[0]));
                busLine.setAreaid(areaId);
                busLine.setLineCode(String.valueOf(stringObjectMap.get("LINECODE")));
                busLine.setSendType(Integer.valueOf(String.valueOf(stringObjectMap.get("SENDTYPE")).split("\\.")[0]));
                String lineName = String.valueOf(stringObjectMap.get("LINENAME")).trim();
                if (!lineName.substring(lineName.length() - 1).equals("路")) {
                    busLine.setSortLineName(lineName);
                    lineName = lineName + "路";
                } else {
                    busLine.setSortLineName(lineName.substring(0, lineName.length() - 1));
                }
                busLine.setLineName(lineName);
                busLine.setIsannulus(Integer.valueOf(stringObjectMap.get("ISANNULUS").toString().split("\\.")[0]));
                busLine.setLineLength(Double.valueOf(stringObjectMap.get("ISANNULUS").toString()));
                busLine.setLimitSpeed(Integer.valueOf(stringObjectMap.get("LIMITSPEED").toString().split("\\.")[0]));
                busLine.setIsoperations(Integer.valueOf(stringObjectMap.get("ISOPERATIONS").toString().split("\\.")[0]));
                busLine.setScheduleMode(Integer.valueOf(stringObjectMap.get("SCHEDULEMODE").toString().split("\\.")[0]));
                busLine.setIslongtransport(Integer.valueOf(stringObjectMap.get("ISLONGTRANSPORT").toString().split("\\.")[0]));
//                busLine.setLateTime(Integer.valueOf(String.valueOf(busLineList.get(i).get("LATETIME")).split("\\.")[0]));
//                busLine.setOnTime(Integer.valueOf(busLineList.get(i).get("ONTIME").toString().split("\\.")[0]));

                //添加站点信息
                addStationList(stringObjectMap, areaId);

                //添加线路班次信息
                addBusLineShift(regKey, busLine);

                List<BusShift> upLinkBusShiftList = iBusShiftDAO.getBusShiftByLineId(areaId, busLine.getId(), 1);
                List<BusShift> downLinkBusShiftList = iBusShiftDAO.getBusShiftByLineId(areaId, busLine.getId(), 2);
                //默认运营时间6:30-19:30
                if (upLinkBusShiftList != null && upLinkBusShiftList.size() > 0) {
                    String upLinkStartTimeMin = String.valueOf(upLinkBusShiftList.get(0).getStime() % 60);
                    if (upLinkStartTimeMin.length() == 1) {
                        upLinkStartTimeMin = "0" + upLinkStartTimeMin;
                    }
                    busLine.setUpLinkStartTime(upLinkBusShiftList.get(0).getStime() / 60 + ":" + upLinkStartTimeMin);
                    String upLinkEndTimeMin = String.valueOf(upLinkBusShiftList.get(upLinkBusShiftList.size() - 1).getStime() % 60);
                    if (upLinkEndTimeMin.length() == 1) {
                        upLinkEndTimeMin = "0" + upLinkEndTimeMin;
                    }
                    busLine.setUpLinkEndTime(upLinkBusShiftList.get(upLinkBusShiftList.size() - 1).getStime() / 60 + ":" + upLinkEndTimeMin);
                } else {
                    busLine.setUpLinkStartTime("6:30");
                    busLine.setUpLinkEndTime("19:30");
                }
                if (downLinkBusShiftList != null && downLinkBusShiftList.size() > 0) {
                    String downLinkStartTimeMin = String.valueOf(downLinkBusShiftList.get(0).getStime() % 60);
                    if (downLinkStartTimeMin.length() == 1) {
                        downLinkStartTimeMin = "0" + downLinkStartTimeMin;
                    }
                    busLine.setDownLinkStartTime(downLinkBusShiftList.get(0).getStime() / 60 + ":" + downLinkStartTimeMin);
                    String downLinkEndTimeMin = String.valueOf(downLinkBusShiftList.get(downLinkBusShiftList.size() - 1).getStime() % 60);
                    if (downLinkEndTimeMin.length() == 1) {
                        downLinkEndTimeMin = "0" + downLinkEndTimeMin;
                    }
                    busLine.setDownLinkEndTime(downLinkBusShiftList.get(downLinkBusShiftList.size() - 1).getStime() / 60 + ":" + downLinkEndTimeMin);
                } else {
                    busLine.setDownLinkStartTime("6:30");
                    busLine.setDownLinkEndTime("19:30");
                }
                busLineModelList.add(busLine);
            }
            iBusLineDAO.addBusLineList(busLineModelList);
        }
    }

    /**
     * 添加公交站点
     *
     * @param busLineMap
     * @param areaId
     */
    @SuppressWarnings("Duplicates")
    private void addStationList(Map<String, Object> busLineMap, String areaId) {
        try {
            List<Map<String, Object>> tempStationList = (List<Map<String, Object>>) busLineMap.get("SITELIST");
            if (tempStationList != null && tempStationList.size() > 0) {

                //获取车辆经纬度 => 百度经纬度结果
                List<String> latitudeAndLongitudeList = getWgs84LatitudeAndLongitude(tempStationList);
                List<Map<String, Object>> baiduLatLogDTOList = new ArrayList<Map<String, Object>>();

                if (latitudeAndLongitudeList == null) throw new NullPointerException("baiduLatLogDTOList is null");

                if (latitudeAndLongitudeList.size() > 100) {
                    for (int a = 0; a < latitudeAndLongitudeList.size(); a = a + 99) {
                        if ((a + 99) > latitudeAndLongitudeList.size()) {
                            Map<String, JSONArray> transformLatLogResult = BaiDuMapUtil.geoConv(latitudeAndLongitudeList.subList(a, latitudeAndLongitudeList.size()), "1", "5");
                            baiduLatLogDTOList.addAll(JsonTools.gson.fromJson(JsonTools.gson.toJson(transformLatLogResult.get("coords")), List.class));
                        } else {
                            Map<String, JSONArray> transformLatLogResult = BaiDuMapUtil.geoConv(latitudeAndLongitudeList.subList(a, a + 99), "1", "5");
                            if (a == 0) {
                                baiduLatLogDTOList = JsonTools.gson.fromJson(JsonTools.gson.toJson(transformLatLogResult.get("coords")), List.class);
                            } else {
                                baiduLatLogDTOList.addAll(JsonTools.gson.fromJson(JsonTools.gson.toJson(transformLatLogResult.get("coords")), List.class));
                            }
                        }
                    }
                } else {
                    Map<String, JSONArray> transformLatLogResult = BaiDuMapUtil.geoConv(latitudeAndLongitudeList, "1", "5");
                    baiduLatLogDTOList = JsonTools.gson.fromJson(JsonTools.gson.toJson(transformLatLogResult.get("coords")), List.class);
                }

                //获取线路下站点数据
                List<BusStation> busStationList = new ArrayList<BusStation>();
                for (int a = 0; a < tempStationList.size(); a++) {
                    BusStation busStation = new BusStation("RM");
                    busStation.setSiteCode(tempStationList.get(a).get("SITECODE").toString());
                    busStation.setSiteName(tempStationList.get(a).get("SITENAME").toString());
                    if (tempStationList.get(a).get("LONGITUDE").toString().equals("0.0")) {
                        busStation.setLongitude(Double.valueOf("88.0197931975"));
                        busStation.setLatitude(Double.valueOf("40.7465368828"));
                    } else {
                        busStation.setLongitude(Double.valueOf(tempStationList.get(a).get("LONGITUDE").toString()));
                        busStation.setLatitude(Double.valueOf(tempStationList.get(a).get("LATITUDE").toString()));
                    }
                    busStation.setDirection(Integer.valueOf(tempStationList.get(a).get("DIRECTION").toString().split("\\.")[0]));
                    busStation.setSitenum(Integer.valueOf(tempStationList.get(a).get("SITENUM").toString().split("\\.")[0]));
                    //添加百度经纬度
                    busStation.setBdlat(Double.valueOf(baiduLatLogDTOList.get(a).get("y").toString()));
                    busStation.setBdlog(Double.valueOf(baiduLatLogDTOList.get(a).get("x").toString()));

                    SpaceObject loc = new SpaceObject();
                    loc.setType("Point");
                    Double[] coordinates = new Double[2];
                    coordinates[0] = Double.valueOf(tempStationList.get(a).get("LONGITUDE").toString());
                    coordinates[1] = Double.valueOf(tempStationList.get(a).get("LATITUDE").toString());
                    loc.setCoordinates(coordinates);
                    busStation.setLocation(loc);

                    //站点所属线路信息
                    busStation.setAreaid(areaId);
                    busStation.setLineCode(String.valueOf(busLineMap.get("LINECODE")));
                    String lineName = String.valueOf(busLineMap.get("LINENAME")).trim();
                    if (!lineName.substring(lineName.length() - 1).equals("路")) {
                        lineName = lineName + "路";
                    }
                    busStation.setLineName(lineName);
                    busStation.setLineId(String.valueOf(busLineMap.get("ID")).split("\\.")[0]);

//                  busStation.setRemark(tempStationList.get(a).get("REMARK").toString());
//                  busStation.setDistance(Integer.valueOf(tempStationList.get(a).get("DISTANCE").toString()));
//                  busStation.setRadius(Integer.valueOf(tempStationList.get(a).get("RADIUS").toString()));
                    busStationList.add(busStation);
                }
                iBusStationDAO.addBusStation(busStationList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取站点数据中的wgs84标准经纬度
     *
     * @param stationList
     * @return
     */
    private List<String> getWgs84LatitudeAndLongitude(List<Map<String, Object>> stationList) {
        try {
            List<String> latitudeAndLongitudeList = new ArrayList<String>();
            if (stationList != null) {
                for (Map<String, Object> stringObjectMap : stationList) {
                    latitudeAndLongitudeList.add((stringObjectMap.get("LONGITUDE") + "," + stringObjectMap.get("LATITUDE")));
                }
            }
            return latitudeAndLongitudeList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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