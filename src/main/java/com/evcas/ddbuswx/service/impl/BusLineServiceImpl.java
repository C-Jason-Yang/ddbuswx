package com.evcas.ddbuswx.service.impl;

import com.evcas.ddbuswx.common.BusDirection;
import com.evcas.ddbuswx.common.utils.BusLineListComparator;
import com.evcas.ddbuswx.dao.IBusLineDAO;
import com.evcas.ddbuswx.dao.IBusStationDAO;
import com.evcas.ddbuswx.dao.IRTBusArriveLeaveDAO;
import com.evcas.ddbuswx.model.*;
import com.evcas.ddbuswx.model.mongo.BusLine;
import com.evcas.ddbuswx.model.BusRouter;
import com.evcas.ddbuswx.model.mongo.BusStation;
import com.evcas.ddbuswx.model.Router;
import com.evcas.ddbuswx.service.IBusLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

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

    @SuppressWarnings("Duplicates")
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

    @SuppressWarnings("Duplicates")
    @Override
    public List<BusLine> findLineByStationName(String stationName, String areaId) {
        List<BusStation> busStationList = iBusStationDAO.findBusStationByStationName(stationName, "1", areaId);
        List<BusLine> busLineList = new ArrayList<>();
        if (busStationList != null) {
            for (BusStation busStation : busStationList) {
                BusLine busLine = iBusLineDAO.queryBusLineByLineCodeAndAreaId(busStation.getLineCode(), areaId);

                List<BusStation> upLinkBusStationList = iBusStationDAO.getBusStationByLineCode(busStation.getLineCode(),
                        BusDirection.UpLink.getValue(), areaId);
                busLine.setUpLinkStartStation(upLinkBusStationList.get(0).getSiteName());
                busLine.setUpLinkEndStation(upLinkBusStationList.get(upLinkBusStationList.size() - 1).getSiteName());
//                busLine.setUpBusStationList(upLinkBusStationList);

                List<BusStation> downLinkBusStationList = iBusStationDAO.getBusStationByLineCode(busStation.getLineCode(),
                        BusDirection.DownLink.getValue(), areaId);
                busLine.setDownLinkStartStation(downLinkBusStationList.get(0).getSiteName());
                busLine.setDownLinkEndStation(downLinkBusStationList.get(downLinkBusStationList.size() - 1).getSiteName());
                busLineList.add(busLine);
            }
        }
        busLineList.sort(new BusLineListComparator());
        return busLineList;
    }

    @SuppressWarnings("Duplicates")
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
        for (String s1 : startStationNameList) {
            for (String s : endStationNameList) {
                //查询起始站点
                List<BusStation> startStationList = iBusStationDAO.findBusStationByStationName(s1, areaId);
                //查询结束站点
                List<BusStation> endStationList = iBusStationDAO.findBusStationByStationName(s, areaId);
                //最优结果  不需要换乘
                for (BusStation busStation3 : startStationList) {
                    endStationFor:
                    for (BusStation busStation2 : endStationList) {
                        //判断条件 如果通过 则属于直达线路
                        if (
                            //线路是否一致
                                busStation3.getLineCode().equals(busStation2.getLineCode())
                                        &&
                                        //上下行是否一致
                                        busStation3.getDirection().equals(busStation2.getDirection())
                                        &&
                                        //结束站点需要处于起始站点可到达的一侧
                                        busStation3.getSitenum() < busStation2.getSitenum()
                        ) {
                            Router router = new Router();
                            List<BusRouter> busRouterList = new ArrayList<>();
                            BusRouter busRouter = new BusRouter();

                            busRouter.setLineName(busStation3.getLineName());
                            busRouter.setLineCode(busStation3.getLineCode());
                            busRouter.setStartStationName(s1);
                            busRouter.setStartLat(String.valueOf(busStation3.getBdlat()));
                            busRouter.setStartLon(String.valueOf(busStation3.getBdlog()));
                            busRouter.setEndStationName(s);
                            busRouter.setEndLat(String.valueOf(busStation2.getBdlat()));
                            busRouter.setEndLon(String.valueOf(busStation2.getBdlog()));
                            busRouter.setViaStationNum(String.valueOf(busStation2.getSitenum() - busStation3.getSitenum()));

                            List<BusStation> tempStartBusStationList = iBusStationDAO.getBusStationByLineCode(busStation3.getLineCode(),
                                    String.valueOf(busStation3.getDirection()), busStation3.getAreaid());
                            List<BusStation> busRouterBusStation = new ArrayList<>();
                            for (BusStation busStation : tempStartBusStationList) {
                                if (busStation.getSitenum() > busStation3.getSitenum() &&
                                        busStation.getSitenum() < busStation2.getSitenum()) {
                                    busRouterBusStation.add(busStation);
                                }
                            }
                            busRouter.setBusStationList(busRouterBusStation);
                            busRouterList.add(busRouter);
                            router.setBusRouterList(busRouterList);
                            routerList.add(router);
                        }
                        if (!busStation3.getLineCode().equals(busStation2.getLineCode())) {
                            //查询当前起始站点的线路下的所有站点 升序
                            List<BusStation> tempStartBusStationList = iBusStationDAO.getBusStationByLineCode(busStation3.getLineCode(),
                                    String.valueOf(busStation3.getDirection()), busStation3.getAreaid());
                            //查询当前结束站点的线路下的所有站点 升序
                            List<BusStation> tempEndBusStationList = iBusStationDAO.getBusStationByLineCode(busStation2.getLineCode(),
                                    String.valueOf(busStation2.getDirection()), busStation2.getAreaid());

                            for (BusStation busStation1 : tempStartBusStationList) {
                                if (busStation2.getSiteName().equals(busStation1.getSiteName())) {
                                    continue endStationFor;
                                }
                            }
                            for (BusStation element : tempEndBusStationList) {
                                if (busStation3.getSiteName().equals(element.getSiteName())) {
                                    continue endStationFor;
                                }
                            }
                            //循环线路起始站点下游的所有站点
                            for (BusStation item : tempStartBusStationList) {
                                //如果换乘站点站序小于起始站点站序（换乘站点在起始站点的上游）
                                if (item.getSitenum() <= busStation3.getSitenum()) {
                                    continue;
                                }
                                //循环结束站点线路下的所有站点
                                for (BusStation value : tempEndBusStationList) {
                                    //如果换乘站点站序小于结束站点站序（换乘站点在结束站点的上游）
                                    if (value.getSitenum() >= busStation2.getSitenum()) {
                                        continue;
                                    }
                                    //一次换乘
                                    if (
                                        //站点名称相同
                                            item.getSiteName().equals(value.getSiteName())
                                                    &&
                                                    //起始站点站序小于换乘站点序号
                                                    item.getSitenum() > busStation3.getSitenum()
                                                    &&
                                                    //换乘站点站序小于结束站点站序
                                                    value.getSitenum() < busStation2.getSitenum()
                                    ) {

                                        Router router = new Router();
                                        List<BusRouter> busRouterList = new ArrayList<>();
                                        //换乘起始线路
                                        BusRouter startBusRouter = new BusRouter();
                                        startBusRouter.setLineName(item.getLineName());
                                        startBusRouter.setLineCode(item.getLineCode());
                                        startBusRouter.setStartStationName(s1);
                                        startBusRouter.setStartLat(String.valueOf(busStation3.getBdlat()));
                                        startBusRouter.setStartLon(String.valueOf(busStation3.getBdlog()));
                                        startBusRouter.setEndStationName(item.getSiteName());
                                        startBusRouter.setEndLat(String.valueOf(item.getBdlat()));
                                        startBusRouter.setEndLon(String.valueOf(item.getBdlog()));
                                        startBusRouter.setViaStationNum(String.valueOf(item.getSitenum() - busStation3.getSitenum()));

                                        List<BusStation> tempStartLineBusStationList = iBusStationDAO.getBusStationByLineCode(busStation3.getLineCode(),
                                                String.valueOf(busStation3.getDirection()), busStation3.getAreaid());
                                        List<BusStation> startBusRouterBusStation = new ArrayList<>();
                                        for (BusStation station : tempStartLineBusStationList) {
                                            if (station.getSitenum() > busStation3.getSitenum() &&
                                                    station.getSitenum() < item.getSitenum()) {
                                                startBusRouterBusStation.add(station);
                                            }
                                        }
                                        startBusRouter.setBusStationList(startBusRouterBusStation);

                                        BusRouter endBusRouter = new BusRouter();
                                        endBusRouter.setLineName(value.getLineName());
                                        endBusRouter.setLineCode(value.getLineCode());
                                        endBusRouter.setStartStationName(value.getSiteName());
                                        endBusRouter.setStartLat(String.valueOf(value.getBdlat()));
                                        endBusRouter.setStartLon(String.valueOf(value.getBdlog()));
                                        endBusRouter.setEndStationName(s);
                                        endBusRouter.setEndLat(String.valueOf(busStation2.getBdlat()));
                                        endBusRouter.setEndLon(String.valueOf(busStation2.getBdlog()));
                                        endBusRouter.setViaStationNum(String.valueOf(busStation2.getSitenum() - value.getSitenum()));

                                        List<BusStation> tempEndLineBusStationList = iBusStationDAO.getBusStationByLineCode(busStation2.getLineCode(),
                                                String.valueOf(busStation2.getDirection()), busStation2.getAreaid());
                                        List<BusStation> endBusRouterBusStation = new ArrayList<>();
                                        for (BusStation busStation : tempEndLineBusStationList) {
                                            if (busStation.getSitenum() > value.getSitenum() &&
                                                    busStation.getSitenum() < busStation2.getSitenum()) {
                                                endBusRouterBusStation.add(busStation);
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
        for (Router value : routerList) {
            sortRouterList.add(value.getBusRouterList().get(0).getLineName());
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
        for (Router router : routerList1) {
            sortRouterList.add(router.getBusRouterList().get(0).getLineName());
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
