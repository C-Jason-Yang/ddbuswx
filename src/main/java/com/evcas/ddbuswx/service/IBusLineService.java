package com.evcas.ddbuswx.service;

import com.evcas.ddbuswx.model.mongo.BusLine;
import com.evcas.ddbuswx.model.RTBusArriveLeave;
import com.evcas.ddbuswx.model.Router;

import java.util.List;

/**
 * 公交线路业务
 * Created by noxn on 2018/8/11.
 */
public interface IBusLineService {

    List<BusLine> getBusLineByAreaCode(String areaCode);

    /**
     * 根据线路编码查询线路信息
     * @param lineCode
     * @return
     */
    BusLine getBusLineByLineCode(String lineCode);

    /**
     * 根据线路名称查询线路信息
     * @param lineName
     * @param areaCode
     * @return
     */
    BusLine getBusLineByLineName(String lineName, String areaCode);

    /**
     * 根据线路名称和区域id模糊查询线路
     * @param lineName
     * @param areaId
     * @return
     */
    List<BusLine > queryBusLineByLikeLineName(String lineName, String areaId);

    /**
     * 根据线路编号和区域id查询线路信息
     * @param lineCode
     * @param areaId
     * @return
     */
    BusLine getLineInfoById(String lineCode, String areaId);

    /**
     * 根据站点名称查询经过线路
     * @param stationName
     * @param areaId
     * @return
     */
    List<BusLine> findLineByStationName(String stationName, String areaId);

    /**
     * 根据起始站点名称(或者经纬度)和区域id查询公交线路
     * @param startStationName
     * @param endStationName
     * @param areaId
     * @return
     */
    List<Router> findBusRouters(String startStationName, String endStationName, String areaId, String startLat, String startLon, String endLat, String endLon);

    List<RTBusArriveLeave> findRTBusArriveLeaveBy(String areaId, String lineCode, String direction);
}
