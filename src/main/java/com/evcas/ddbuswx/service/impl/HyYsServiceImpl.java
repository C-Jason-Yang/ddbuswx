package com.evcas.ddbuswx.service.impl;

import com.evcas.ddbuswx.common.utils.*;
import com.evcas.ddbuswx.dao.*;
import com.evcas.ddbuswx.model.*;
import com.evcas.ddbuswx.service.IHyYsService;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by noxn on 2018/1/12.
 */
@Service
public class HyYsServiceImpl implements IHyYsService {

    @Autowired
    private IHyYsDAO iHyYsDAO;

    @Autowired
    private IBusLineDAO iBusLineDAO;

    @Autowired
    private IBusShiftDAO iBusShiftDAO;

    @Autowired
    private IBusStationDAO iBusStationDAO;

    @Override
    public void initializeHyYsBusSys() {
        //清楚恒宇颍上公交数据
        clearYsHyOldData();
        //添加线路信息
        addHyYsBusLine();
    }

    /**
     * 添加恒宇公交线路数据
     */
    public void addHyYsBusLine() {
        try {
            String busLineStr = iHyYsDAO.getBusLine();
            if (!StringUtil.isEmpty(busLineStr)) {
                YsHyLine ysHyLine = XmlUtil.xmlListToList(busLineStr, YsHyLine.class);
                if (ysHyLine != null && ysHyLine.getLines() != null && ysHyLine.getLines().size() > 0) {
                    List<Line> ysHyBusLineList = ysHyLine.getLines();
                    List<BusLine> busLineList = new ArrayList<BusLine>();
                    for (int i = 0; i < ysHyBusLineList.size(); i++) {
                        BusLine busLine = new BusLine("HY");
                        busLine.setLineName(ysHyBusLineList.get(i).getLineCode()+"路");
                        busLine.setLineCode(ysHyBusLineList.get(i).getLineCode());
                        busLine.setId(UuidUtil.getUuid());
                        busLine.setAreaid("341226");
                        busLineList.add(busLine);

                        //添加公交线路班次信息
                        addYsHyBusLineShift(busLine);
                    }
                    iBusLineDAO.addBusLineList(busLineList);
                    //添加公交站点数据
                    addHyYsBusStation(busLineList);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加公交站点数据
     * @param busLineList
     */
    public void addHyYsBusStation(List<BusLine> busLineList) {
        //循环公交线路数据 根据线路编号查询线路下站点信息
        for (int i = 0; i < busLineList.size(); i++) {
            //根据线路编号查询  站点数据
            String busStationStr = iHyYsDAO.getBusStation(busLineList.get(i).getLineCode());
            if (busStationStr != null && busStationStr != "") {
                YsHyStation ysHyStation = XmlUtil.xmlListToList(busStationStr, YsHyStation.class);
                if (ysHyStation != null && ysHyStation.getLines() != null && ysHyStation.getLines().size() > 0) {
                    List<HyStation> hyStationList = ysHyStation.getLines();
                    List<BusStation> busStationList = new ArrayList<BusStation>();
                    List<BusStation> downsideBusStationList = new ArrayList<BusStation>();

                    List<String> wgs84List = new ArrayList<String>();
                    for (int a = 0; a < hyStationList.size(); a++) {
                        BusStation busStation = new BusStation("HY");
                        //线路信息
                        busStation.setLineName(hyStationList.get(a).getLineCode() + "路");
                        busStation.setLineCode(hyStationList.get(a).getLineCode());
                        busStation.setLineId(busLineList.get(i).getId());

                        busStation.setAreaid("341226");
                        busStation.setSitenum(hyStationList.get(a).getStationOrder());
                        busStation.setSiteCode(String.valueOf(hyStationList.get(a).getStationOrder()));
                        busStation.setSiteName(hyStationList.get(a).getStatName());
                        if (hyStationList.get(a).getUpDownName().equals("下行")) {
                            busStation.setDirection(2);
                        } else {
                            busStation.setDirection(1);
                        }

                        Integer jds = Integer.parseInt(hyStationList.get(a).getStatLongitude());
                        Integer wds = Integer.parseInt(hyStationList.get(a).getStatLatitude());
                        Integer jdsz = jds/600000;//经度整数
                        double jdsd = jdsz + (jds - jdsz*600000)/1000000.0 * 100/60;//经度：整数+小数
                        Integer wdsz = wds/600000;//经度整数
                        double wdsd = wdsz + (wds - wdsz*600000)/1000000.0 * 100/60;//纬度：整数+小数

                        if (String.valueOf(wdsd).equals("32.65249666666666") ||
                                String.valueOf(wdsd).equals("116.25906666666667") ||
                                String.valueOf(wdsd).equals("1000.666665") ||
                                String.valueOf(wdsd).equals("32.652496666666664") ||
                                String.valueOf(wdsd).equals("100.666665") ||
                                String.valueOf(wdsd).equals("32.652496666666664") ||
                                String.valueOf(wdsd).equals("32.65246666666667")) {
                            busStation.setLongitude(Double.valueOf("88.0197931975"));
                            busStation.setLatitude(Double.valueOf("40.7465368828"));
                        } else {
                            busStation.setLatitude(wdsd);
                            busStation.setLongitude(jdsd);
                        }
                        if (busStation.getDirection() == 1) {
                            busStationList.add(busStation);
                        } else {
                            downsideBusStationList.add(busStation);
                        }

                        if (String.valueOf(wdsd).equals("32.65249666666666") ||
                                String.valueOf(wdsd).equals("116.25906666666667") ||
                                String.valueOf(wdsd).equals("1000.666665") ||
                                String.valueOf(wdsd).equals("32.652496666666664") ||
                                String.valueOf(wdsd).equals("100.666665") ||
                                String.valueOf(wdsd).equals("32.652496666666664") ||
                                String.valueOf(wdsd).equals("32.65246666666667")) {
                            wgs84List.add(String.valueOf(88.0197931975 + "," + 40.7465368828));
                        } else {
                            wgs84List.add(String.valueOf(jdsd + "," + wdsd));
                        }
                    }
                    Collections.sort(busStationList, new Comparator<BusStation>() {
                        @Override
                        public int compare(BusStation o1, BusStation o2) {
                            return o1.getDirection() - o2.getDirection();
                        }
                    });
                    for (int k = 0; k < busStationList.size(); k++) {
                        busStationList.get(k).setHySiteNum(busStationList.get(k).getSitenum());
                        busStationList.get(k).setSitenum(busStationList.size() - k);
                    }
                    busStationList.addAll(downsideBusStationList);
                    List<Map<String, Object>> baiduLatLogDTOList = new ArrayList<Map<String, Object>>();
                    try {
                        if (wgs84List.size() > 100) {
                            for(int a = 0; a < wgs84List.size(); a = a + 99) {
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
                        Double [] coordinates = new Double[2];
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

    public void addYsHyBusLineShift(BusLine busLine) {
        String busLineShiftXmlStr = iHyYsDAO.getBusLineShift(busLine.getLineCode());
        if (!StringUtil.isEmpty(busLineShiftXmlStr)) {
            YsHyBusLineShift ysHyBusLineShift = XmlUtil.xmlListToList(busLineShiftXmlStr, YsHyBusLineShift.class);
            if (ysHyBusLineShift.getLines() != null) {
                List<HyBusLineShift> busLineShiftList = ysHyBusLineShift.getLines();
                List<BusShift> busShiftList = new ArrayList<BusShift>();
                for (int i = 0; i < busLineShiftList.size(); i++) {
                    BusShift busShift = new BusShift();
                    busShift.setFromSys("HY");
                    busShift.setAreaid("341226");

                    busShift.setLineId(busLine.getId());
                    busShift.setLineName(busLine.getLineName());
                    busShift.setLineCode(busLine.getLineCode());
                    if (busLineShiftList.get(i).getSxx().equals("上行")) {
                        busShift.setBiztype(1);
                    } else {
                        busShift.setBiztype(2);
                    }
                    String sTime = busLineShiftList.get(i).getPlanBegin();
                    String sHour = sTime.substring(0, 2);
                    String sMin = sTime.substring(2, 4);
                    busShift.setStime(Integer.valueOf(sHour) * 60 + Integer.valueOf(sMin));
                    busShiftList.add(busShift);
                }
                iBusShiftDAO.addBufShiftList(busShiftList);
            }
        }
    }

    /**
     * 清除颍上恒宇公交数据
     */
    public void clearYsHyOldData() {
        iBusLineDAO.deleteBusLineByAreaId("341226", "HY");
        iBusStationDAO.deleteBusStationByAreaId("341226", "HY");
        iBusShiftDAO.deleteBusShift("341226", "HY");
    }
}
