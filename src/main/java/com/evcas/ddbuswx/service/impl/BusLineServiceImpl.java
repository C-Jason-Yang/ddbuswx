package com.evcas.ddbuswx.service.impl;

import com.evcas.ddbuswx.common.BusDirection;
import com.evcas.ddbuswx.common.utils.BusLineListComparator;
import com.evcas.ddbuswx.dao.IAreaDAO;
import com.evcas.ddbuswx.dao.IBusLineDAO;
import com.evcas.ddbuswx.dao.IBusStationDAO;
import com.evcas.ddbuswx.dao.IRTBusArriveLeaveDAO;
import com.evcas.ddbuswx.model.*;
import com.evcas.ddbuswx.service.IBusLineService;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by noxn on 2018/8/11.
 */
@Service
public class BusLineServiceImpl implements IBusLineService {

    @Autowired
    private IBusLineDAO iBusLineDAO;
    @Autowired
    private IBusStationDAO iBusStationDAO;
    @Autowired
    private IRTBusArriveLeaveDAO irtBusArriveLeaveDAO;

    @Override
    public List<BusLine> getBusLineByAreaCode(String areaCode) {
//        Area area = iAreaDAO.getAreaByAreaCode(areaCode);
//        if (area != null) {
//            return iBusLineDAO.getBusLineByAreaCode(area.getAreaOldCode());
//        }
//        return null;
        return iBusLineDAO.getBusLineByAreaCode(areaCode);
    }

    @Override
    public BusLine getBusLineByLineCode(String lineCode) {
        return iBusLineDAO.getBusLineByLineCode(lineCode);
    }

    @Override
    public BusLine getBusLineByLineName(String lineName, String areaCode) {
        return iBusLineDAO.getBusLineByLineName(lineName, areaCode);
    }

    @Override
    public List<BusLine> queryBusLineByLikeLineName(String lineName, String areaId) {
        List<BusLine> busLineList = iBusLineDAO.queryBusLineByLikeLineName(lineName, areaId);
        if (busLineList != null) {
            for (BusLine busLine : busLineList) {
                List<BusStation> upLinkBusStationList = iBusStationDAO.getBusStationByLineCode(busLine.getLineCode(),
                        BusDirection.UpLink.getValue(), areaId);
                busLine.setUpLinkStartStation(upLinkBusStationList.get(0).getSiteName());
                busLine.setUpLinkEndStation(upLinkBusStationList.get(upLinkBusStationList.size() - 1).getSiteName());
                List<BusStation> downLinkBusStationList = iBusStationDAO.getBusStationByLineCode(busLine.getLineCode(),
                        BusDirection.DownLink.getValue(), areaId);
                busLine.setDownLinkStartStation(downLinkBusStationList.get(0).getSiteName());
                busLine.setDownLinkEndStation(downLinkBusStationList.get(downLinkBusStationList.size() - 1).getSiteName());
            }
        }
        if (busLineList == null) {
            throw new NullPointerException("busLineList is null ");
        }
        busLineList.sort(new BusLineListComparator());
        return busLineList;
    }

    @Override
    public BusLine getLineInfoById(String lineCode, String areaId) {
        BusLine busLine = iBusLineDAO.queryBusLineByLineCodeAndAreaId(lineCode, areaId);
        busLine.setUpBusStationList(iBusStationDAO.getBusStationByLineCode(lineCode, BusDirection.UpLink.getValue(), areaId));
        busLine.setDownBusStationList(iBusStationDAO.getBusStationByLineCode(lineCode, BusDirection.DownLink.getValue(), areaId));
        return busLine;
    }

    @Override
    public List<BusLine> findLineByStationName(String stationName, String areaId) {
        List<BusStation> busStationList = iBusStationDAO.findBusStationByStationName(stationName, "1", areaId);
        List<BusLine> busLineList = new ArrayList<>();
        if (busStationList != null) {
            for (int i = 0; i < busStationList.size(); i++) {
                BusLine busLine = iBusLineDAO.queryBusLineByLineCodeAndAreaId(busStationList.get(i).getLineCode(), areaId);

                List<BusStation> upLinkBusStationList = iBusStationDAO.getBusStationByLineCode(busStationList.get(i).getLineCode(),
                        BusDirection.UpLink.getValue(), areaId);
                busLine.setUpLinkStartStation(upLinkBusStationList.get(0).getSiteName());
                busLine.setUpLinkEndStation(upLinkBusStationList.get(upLinkBusStationList.size() - 1).getSiteName());
//                busLine.setUpBusStationList(upLinkBusStationList);

                List<BusStation> downLinkBusStationList = iBusStationDAO.getBusStationByLineCode(busStationList.get(i).getLineCode(),
                        BusDirection.DownLink.getValue(), areaId);
                busLine.setDownLinkStartStation(downLinkBusStationList.get(0).getSiteName());
                busLine.setDownLinkEndStation(downLinkBusStationList.get(downLinkBusStationList.size() - 1).getSiteName());
                busLineList.add(busLine);
            }
        }
        Collections.sort(busLineList, new BusLineListComparator());
        return busLineList;
    }

    @Override
    public List<Router> findBusRouters(String startStationName, String endStationName, String areaId, String startLat, String startLon, String endLat, String endLon) {
        List<String> startStationNameList = new ArrayList<>();
        if (startStationName == null || startStationName.equals("")) {
            List<BusStation> startBusStationList = iBusStationDAO.getNearStationByGps(startLat, startLon, "1000");
            if (startBusStationList == null || startBusStationList.size() == 0) {
                return null;
            }
            startStationNameList.add(startBusStationList.get(0).getSiteName());
//            for (int i = 0; i < startBusStationList.size(); i++) {
//                startStationNameList.add(startBusStationList.get(i).getSiteName());
//            }
//            if (startStationNameList.size() > 3) {
//                startStationNameList = startStationNameList.subList(0, 4);
//            }
        } else {
            startStationNameList.add(startStationName);
        }
        List<String> endStationNameList = new ArrayList<>();
        if (endStationName == null || endStationName.equals("")) {
            List<BusStation> endBusStationList = iBusStationDAO.getNearStationByGps(endLat, endLon, "1000");
            if (endBusStationList == null || endBusStationList.size() == 0) {
                return null;
            }
//            for (int i = 0; i < endBusStationList.size(); i++) {
//                endStationNameList.add(endBusStationList.get(i).getSiteName());
//            }
//            if (endStationNameList.size() > 3) {
//                endStationNameList = endStationNameList.subList(0, 4);
//            }
            endStationNameList.add(endBusStationList.get(0).getSiteName());
        } else {
            endStationNameList.add(endStationName);
        }
        List<Router> routerList = new ArrayList<>();
        List<Router> routerList1 = new ArrayList<>();
        for (int e = 0; e < startStationNameList.size(); e++) {
            for (int f = 0; f < endStationNameList.size(); f++) {
                //查询起始站点
                List<BusStation> startStationList = iBusStationDAO.findBusStationByStationName(startStationNameList.get(e), areaId);
                //查询结束站点
                List<BusStation> endStationList = iBusStationDAO.findBusStationByStationName(endStationNameList.get(f), areaId);
                //最优结果  不需要换乘
                for (int a = 0; a < startStationList.size(); a++) {
                    endStationFor:
                    for (int b = 0; b < endStationList.size(); b++) {
                        //判断条件 如果通过 则属于直达线路
                        if (
                            //线路是否一致
                                startStationList.get(a).getLineCode().equals(endStationList.get(b).getLineCode())
                                        &&
                                        //上下行是否一致
                                        startStationList.get(a).getDirection().equals(endStationList.get(b).getDirection())
                                        &&
                                        //结束站点需要处于起始站点可到达的一侧
                                        startStationList.get(a).getSitenum() < endStationList.get(b).getSitenum()
                        ) {
                            Router router = new Router();
                            List<BusRouter> busRouterList = new ArrayList<>();
                            BusRouter busRouter = new BusRouter();

                            busRouter.setLineName(startStationList.get(a).getLineName());
                            busRouter.setLineCode(startStationList.get(a).getLineCode());
                            busRouter.setStartStationName(startStationNameList.get(e));
                            busRouter.setStartLat(String.valueOf(startStationList.get(a).getBdlat()));
                            busRouter.setStartLon(String.valueOf(startStationList.get(a).getBdlog()));
                            busRouter.setEndStationName(endStationNameList.get(f));
                            busRouter.setEndLat(String.valueOf(endStationList.get(b).getBdlat()));
                            busRouter.setEndLon(String.valueOf(endStationList.get(b).getBdlog()));
                            busRouter.setViaStationNum(String.valueOf(endStationList.get(b).getSitenum() - startStationList.get(a).getSitenum()));

                            List<BusStation> tempStartBusStationList = iBusStationDAO.getBusStationByLineCode(startStationList.get(a).getLineCode(),
                                    String.valueOf(startStationList.get(a).getDirection()), startStationList.get(a).getAreaid());
                            List<BusStation> busRouterBusStation = new ArrayList<>();
                            for (int i = 0; i < tempStartBusStationList.size(); i++) {
                                if (tempStartBusStationList.get(i).getSitenum() > startStationList.get(a).getSitenum() &&
                                        tempStartBusStationList.get(i).getSitenum() < endStationList.get(b).getSitenum()) {
                                    busRouterBusStation.add(tempStartBusStationList.get(i));
                                }
                            }
                            busRouter.setBusStationList(busRouterBusStation);
                            busRouterList.add(busRouter);
                            router.setBusRouterList(busRouterList);
                            routerList.add(router);
                        }
                        if (!startStationList.get(a).getLineCode().equals(endStationList.get(b).getLineCode())) {
                            //查询当前起始站点的线路下的所有站点 升序
                            List<BusStation> tempStartBusStationList = iBusStationDAO.getBusStationByLineCode(startStationList.get(a).getLineCode(),
                                    String.valueOf(startStationList.get(a).getDirection()), startStationList.get(a).getAreaid());
                            //查询当前结束站点的线路下的所有站点 升序
                            List<BusStation> tempEndBusStationList = iBusStationDAO.getBusStationByLineCode(endStationList.get(b).getLineCode(),
                                    String.valueOf(endStationList.get(b).getDirection()), endStationList.get(b).getAreaid());

                            for (int d = 0; d < tempStartBusStationList.size(); d++) {
                                if (endStationList.get(b).getSiteName().equals(tempStartBusStationList.get(d).getSiteName())) {
                                    continue endStationFor;
                                }
                            }
                            for (int d = 0; d < tempEndBusStationList.size(); d++) {
                                if (startStationList.get(a).getSiteName().equals(tempEndBusStationList.get(d).getSiteName())) {
                                    continue endStationFor;
                                }
                            }
                            //循环线路起始站点下游的所有站点
                            for (int c = 0; c < tempStartBusStationList.size(); c++) {
                                //如果换乘站点站序小于起始站点站序（换乘站点在起始站点的上游）
                                if (tempStartBusStationList.get(c).getSitenum() <= startStationList.get(a).getSitenum()) {
                                    continue;
                                }
                                //循环结束站点线路下的所有站点
                                for (int d = 0; d < tempEndBusStationList.size(); d++) {
                                    //如果换乘站点站序小于结束站点站序（换乘站点在结束站点的上游）
                                    if (tempEndBusStationList.get(d).getSitenum() >= endStationList.get(b).getSitenum()) {
                                        continue;
                                    }
                                    //一次换乘
                                    if (
                                        //站点名称相同
                                            tempStartBusStationList.get(c).getSiteName().equals(tempEndBusStationList.get(d).getSiteName())
                                                    &&
                                                    //起始站点站序小于换乘站点序号
                                                    tempStartBusStationList.get(c).getSitenum() > startStationList.get(a).getSitenum()
                                                    &&
                                                    //换乘站点站序小于结束站点站序
                                                    tempEndBusStationList.get(d).getSitenum() < endStationList.get(b).getSitenum()
                                    ) {

                                        Router router = new Router();
                                        List<BusRouter> busRouterList = new ArrayList<>();
                                        //换乘起始线路
                                        BusRouter startBusRouter = new BusRouter();
                                        startBusRouter.setLineName(tempStartBusStationList.get(c).getLineName());
                                        startBusRouter.setLineCode(tempStartBusStationList.get(c).getLineCode());
                                        startBusRouter.setStartStationName(startStationNameList.get(e));
                                        startBusRouter.setStartLat(String.valueOf(startStationList.get(a).getBdlat()));
                                        startBusRouter.setStartLon(String.valueOf(startStationList.get(a).getBdlog()));
                                        startBusRouter.setEndStationName(tempStartBusStationList.get(c).getSiteName());
                                        startBusRouter.setEndLat(String.valueOf(tempStartBusStationList.get(c).getBdlat()));
                                        startBusRouter.setEndLon(String.valueOf(tempStartBusStationList.get(c).getBdlog()));
                                        startBusRouter.setViaStationNum(String.valueOf(tempStartBusStationList.get(c).getSitenum() - startStationList.get(a).getSitenum()));

                                        List<BusStation> tempStartLineBusStationList = iBusStationDAO.getBusStationByLineCode(startStationList.get(a).getLineCode(),
                                                String.valueOf(startStationList.get(a).getDirection()), startStationList.get(a).getAreaid());
                                        List<BusStation> startBusRouterBusStation = new ArrayList<>();
                                        for (int i = 0; i < tempStartLineBusStationList.size(); i++) {
                                            if (tempStartLineBusStationList.get(i).getSitenum() > startStationList.get(a).getSitenum() &&
                                                    tempStartLineBusStationList.get(i).getSitenum() < tempStartBusStationList.get(c).getSitenum()) {
                                                startBusRouterBusStation.add(tempStartLineBusStationList.get(i));
                                            }
                                        }
                                        startBusRouter.setBusStationList(startBusRouterBusStation);

                                        BusRouter endBusRouter = new BusRouter();
                                        endBusRouter.setLineName(tempEndBusStationList.get(d).getLineName());
                                        endBusRouter.setLineCode(tempEndBusStationList.get(d).getLineCode());
                                        endBusRouter.setStartStationName(tempEndBusStationList.get(d).getSiteName());
                                        endBusRouter.setStartLat(String.valueOf(tempEndBusStationList.get(d).getBdlat()));
                                        endBusRouter.setStartLon(String.valueOf(tempEndBusStationList.get(d).getBdlog()));
                                        endBusRouter.setEndStationName(endStationNameList.get(f));
                                        endBusRouter.setEndLat(String.valueOf(endStationList.get(b).getBdlat()));
                                        endBusRouter.setEndLon(String.valueOf(endStationList.get(b).getBdlog()));
                                        endBusRouter.setViaStationNum(String.valueOf(endStationList.get(b).getSitenum() - tempEndBusStationList.get(d).getSitenum()));

                                        List<BusStation> tempEndLineBusStationList = iBusStationDAO.getBusStationByLineCode(endStationList.get(b).getLineCode(),
                                                String.valueOf(endStationList.get(b).getDirection()), endStationList.get(b).getAreaid());
                                        List<BusStation> endBusRouterBusStation = new ArrayList<>();
                                        for (int i = 0; i < tempEndLineBusStationList.size(); i++) {
                                            if (tempEndLineBusStationList.get(i).getSitenum() > tempEndBusStationList.get(d).getSitenum() &&
                                                    tempEndLineBusStationList.get(i).getSitenum() < endStationList.get(b).getSitenum()) {
                                                endBusRouterBusStation.add(tempEndLineBusStationList.get(i));
                                            }
                                        }
                                        endBusRouter.setBusStationList(endBusRouterBusStation);

                                        busRouterList.add(startBusRouter);
                                        busRouterList.add(endBusRouter);
                                        router.setBusRouterList(busRouterList);
                                        routerList1.add(router);
                                        continue endStationFor;
                                    }
//                            //查询的起始站点或结束站点已在换乘线路上 无需换乘
//                            if (tempStartBusStationList.get(c).getSiteName().equals(tempEndBusStationList.get(d).getSiteName())
//                                    &&
//                                    (tempStartBusStationList.get(c).getSitenum() == startStationList.get(a).getSitenum()
//                                    ||
//                                    tempEndBusStationList.get(d).getSitenum() == endStationList.get(b).getSitenum())) {
//
//                            }
                                }
                            }
                        }
                    }
                }
            }
        }
        //排序
        List<Router> tempRouterList = new ArrayList<>();
        TreeSet<String> sortRouterList = new TreeSet<String>();
        for (int i = 0; i < routerList.size(); i++) {
            sortRouterList.add(routerList.get(i).getBusRouterList().get(0).getLineName());
        }
        for (; routerList.size() != 0; ) {
            String tempLineName = sortRouterList.first();
            sortRouterList.remove(tempLineName);
            for (int i = 0; i < routerList.size(); i++) {
                if (routerList.get(i).getBusRouterList().get(0).getLineName().equals(tempLineName)) {
                    tempRouterList.add(routerList.get(i));
                    routerList.remove(i);
                    i--;
                }
            }
        }
        List<Router> tempRouterList1 = new ArrayList<>();
        sortRouterList = new TreeSet<String>();
        for (int i = 0; i < routerList1.size(); i++) {
            sortRouterList.add(routerList1.get(i).getBusRouterList().get(0).getLineName());
        }
        for (; routerList1.size() != 0; ) {
            String tempLineName = sortRouterList.first();
            sortRouterList.remove(tempLineName);
            for (int i = 0; i < routerList1.size(); i++) {
                if (routerList1.get(i).getBusRouterList().get(0).getLineName().equals(tempLineName)) {
                    tempRouterList1.add(routerList1.get(i));
                    routerList1.remove(i);
                    i--;
                }
            }
        }
        tempRouterList.addAll(tempRouterList1);
        return tempRouterList;
    }

    @Override
    public List<RTBusArriveLeave> findRTBusArriveLeaveBy(String areaId, String lineCode, String direction) {
        return irtBusArriveLeaveDAO.findRTBusArriveLeaveBy(areaId, lineCode, direction);
    }

}
