package com.evcas.ddbuswx.service.impl;

import com.evcas.ddbuswx.common.utils.*;
import com.evcas.ddbuswx.dao.IBusLineDAO;
import com.evcas.ddbuswx.dao.IBusShiftDAO;
import com.evcas.ddbuswx.dao.IBusStationDAO;
import com.evcas.ddbuswx.dao.IHyLxDAO;
import com.evcas.ddbuswx.model.*;
import com.evcas.ddbuswx.model.mongo.BusLine;
import com.evcas.ddbuswx.model.mongo.BusShift;
import com.evcas.ddbuswx.model.mongo.BusStation;
import com.evcas.ddbuswx.service.IHyLxService;
import com.google.common.base.Strings;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by noxn on 2018/1/17.
 */
@Service
public class HyLxServiceImpl implements IHyLxService {

    @Autowired
    private IHyLxDAO iHyLxDAO;

    @Autowired
    private IBusLineDAO iBusLineDAO;

    @Autowired
    private IBusShiftDAO iBusShiftDAO;

    @Autowired
    private IBusStationDAO iBusStationDAO;

    @Override
    public void initializeHyLxBusSys() {
//        //清楚恒宇利辛公交数据
        clearLxHyOldData();
//        //添加线路信息
        addHyLxBusLine();
    }


    /**
     * 添加利辛恒宇公交线路数据
     */
    private void addHyLxBusLine() {
        try {
            String busLineStr = iHyLxDAO.getBusLine();
            if (!StringUtil.isEmpty(busLineStr)) {
                YsHyLine lxHyLine = XmlUtil.xmlListToList(busLineStr, YsHyLine.class);
                if (lxHyLine != null && lxHyLine.getLines() != null && lxHyLine.getLines().size() > 0) {
                    List<Line> lxHyBusLineList = lxHyLine.getLines();
                    List<BusLine> busLineList = new ArrayList<BusLine>();
                    for (Line line : lxHyBusLineList) {
                        BusLine busLine = new BusLine("HY");
                        busLine.setLineName(line.getLineCode() + "路");
                        busLine.setLineCode(line.getLineCode());
                        busLine.setId(UuidUtil.getUuid());
                        busLine.setAreaid("341623");
                        busLineList.add(busLine);

                        //添加公交线路班次信息
                        addLxHyBusLineShift(busLine);
                    }
                    iBusLineDAO.addBusLineList(busLineList);
                    //添加公交站点数据
                    addHyLxBusStation(busLineList);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加公交站点数据
     *
     * @param busLineList
     */
    @SuppressWarnings({"Duplicates", "unchecked"})
    private void addHyLxBusStation(List<BusLine> busLineList) {
        //循环公交线路数据 根据线路编号查询线路下站点信息
        for (BusLine busLine : busLineList) {
            //根据线路编号查询  站点数据
            String busStationStr = iHyLxDAO.getBusStation(busLine.getLineCode());
            if (!Strings.isNullOrEmpty(busStationStr)) {
                YsHyStation ysHyStation = XmlUtil.xmlListToList(busStationStr, YsHyStation.class);
                if (ysHyStation != null && ysHyStation.getLines() != null && ysHyStation.getLines().size() > 0) {
                    List<HyStation> hyStationList = ysHyStation.getLines();
                    List<BusStation> busStationList = new ArrayList<>();
                    List<BusStation> downsideBusStationList = new ArrayList<>();

                    List<String> wgs84List = new ArrayList<>();
                    for (HyStation hyStation : hyStationList) {
                        BusStation busStation = new BusStation("HY");
                        //线路信息
                        busStation.setLineName(hyStation.getLineCode() + "路");
                        busStation.setLineCode(hyStation.getLineCode());
                        busStation.setLineId(busLine.getId());

                        busStation.setAreaid("341623");
                        busStation.setSitenum(hyStation.getStationOrder());
                        busStation.setSiteCode(String.valueOf(hyStation.getStationOrder()));
                        busStation.setSiteName(hyStation.getStatName());
                        if (hyStation.getUpDownName().equals("下行")) {
                            busStation.setDirection(2);
                        } else {
                            busStation.setDirection(1);
                        }

                        int jds = Integer.parseInt(hyStation.getStatLongitude());
                        int wds = Integer.parseInt(hyStation.getStatLatitude());
                        int jdsz = jds / 600000;//经度整数
                        double jdsd = jdsz + (jds - jdsz * 600000) / 1000000.0 * 100 / 60;//经度：整数+小数
                        int wdsz = wds / 600000;//经度整数
                        double wdsd = wdsz + (wds - wdsz * 600000) / 1000000.0 * 100 / 60;//纬度：整数+小数

                        busStation.setLatitude(wdsd);
                        busStation.setLongitude(jdsd);
                        if (busStation.getDirection() == 1) {
                            busStationList.add(busStation);
                        } else {
                            downsideBusStationList.add(busStation);
                        }

                        wgs84List.add(jdsd + "," + wdsd);
                    }
                    busStationList.sort(Comparator.comparingInt(BusStation::getDirection));
                    for (int k = 0; k < busStationList.size(); k++) {
                        busStationList.get(k).setHySiteNum(busStationList.get(k).getSitenum());
                        busStationList.get(k).setSitenum(busStationList.size() - k);
                    }
                    busStationList.addAll(downsideBusStationList);
                    List<Map<String, Object>> baiduLatLogDTOList = new ArrayList<Map<String, Object>>();
                    try {
                        if (wgs84List.size() > 100) {
                            for (int a = 0; a < wgs84List.size(); a = a + 99) {
                                if ((a + 99) > wgs84List.size()) {
                                    Map<String, JSONArray> transformLatLogResult = BaiDuMapUtil.geoConv(wgs84List.subList(a, wgs84List.size()), "1", "5");
                                    baiduLatLogDTOList.addAll(JsonTools.gson.fromJson(JsonTools.gson.toJson(transformLatLogResult.get("coords")), List.class));
                                } else {
                                    Map<String, JSONArray> transformLatLogResult = BaiDuMapUtil.geoConv(wgs84List.subList(a, a + 99), "1", "5");
                                    if (a == 0) {
                                        baiduLatLogDTOList = JsonTools.gson.fromJson(JsonTools.gson.toJson(transformLatLogResult.get("coords")), List.class);
                                    } else {
                                        baiduLatLogDTOList.addAll(JsonTools.gson.fromJson(JsonTools.gson.toJson(transformLatLogResult.get("coords")), List.class));
                                    }
                                }
                            }
                        } else {
                            Map<String, JSONArray> transformLatLogResult = BaiDuMapUtil.geoConv(wgs84List, "1", "5");
                            baiduLatLogDTOList = JsonTools.gson.fromJson(JsonTools.gson.toJson(transformLatLogResult.get("coords")), List.class);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    for (int a = 0; a < busStationList.size(); a++) {
                        busStationList.get(a).setBdlog(Double.valueOf(baiduLatLogDTOList.get(a).get("x").toString()));
                        busStationList.get(a).setBdlat(Double.valueOf(baiduLatLogDTOList.get(a).get("y").toString()));

                        SpaceObject loc = new SpaceObject();
                        loc.setType("Point");
                        Double[] coordinates = new Double[2];
                        coordinates[0] = Double.valueOf(baiduLatLogDTOList.get(a).get("x").toString());
                        coordinates[1] = Double.valueOf(baiduLatLogDTOList.get(a).get("y").toString());
                        loc.setCoordinates(coordinates);
                        busStationList.get(a).setLocation(loc);
                    }
                    iBusStationDAO.addBusStation(busStationList);
                }
            }
        }
    }

    private void addLxHyBusLineShift(BusLine busLine) {
        String busLineShiftXmlStr = iHyLxDAO.getBusLineShift(busLine.getLineCode());
        if (!StringUtil.isEmpty(busLineShiftXmlStr)) {
            YsHyBusLineShift lxHyBusLineShift = XmlUtil.xmlListToList(busLineShiftXmlStr, YsHyBusLineShift.class);
            if (lxHyBusLineShift == null) {
                throw new NullPointerException("lxHyBusLineShift is null");
            }
            if (lxHyBusLineShift.getLines() != null) {
                List<HyBusLineShift> busLineShiftList = lxHyBusLineShift.getLines();
                List<BusShift> busShiftList = new ArrayList<BusShift>();
                for (HyBusLineShift hyBusLineShift : busLineShiftList) {
                    BusShift busShift = new BusShift();
                    busShift.setFromSys("HY");
                    busShift.setAreaid("341623");

                    busShift.setLineId(busLine.getId());
                    busShift.setLineName(busLine.getLineName());
                    busShift.setLineCode(busLine.getLineCode());
                    if (hyBusLineShift.getSxx().equals("上行")) {
                        busShift.setBiztype(1);
                    } else {
                        busShift.setBiztype(2);
                    }
                    String sTime = hyBusLineShift.getPlanBegin();
                    String sHour = sTime.substring(0, 2);
                    String sMin = sTime.substring(2, 4);
                    busShift.setStime(Integer.parseInt(sHour) * 60 + Integer.parseInt(sMin));
                    busShiftList.add(busShift);
                }
                iBusShiftDAO.addBufShiftList(busShiftList);
            }
        }
    }

    /**
     * 清除利辛恒宇公交数据
     */
    private void clearLxHyOldData() {
        iBusLineDAO.deleteBusLineByAreaId("341623", "HY");
        iBusStationDAO.deleteBusStationByAreaId("341623", "HY");
        iBusShiftDAO.deleteBusShift("341623", "HY");
    }
}
