package com.evcas.ddbuswx.dao;

import com.evcas.ddbuswx.model.mongo.BusStation;

import java.util.List;

/**
 * Created by noxn on 2018/1/10.
 */
public interface IBusStationDAO {

    /**
     * 批量添加站点信息
     * @param busStationList
     */
    void addBusStation(List<BusStation> busStationList);

    /**
     * 根据区域id删除区域下站点信息
     * @param areaId
     * @param fromSys
     */
    void deleteBusStationByAreaId(String areaId, String fromSys);

    /**
     * 根据区域id和线路id获取站点集合
     * @param lineCode
     * @param direction
     * @param areaCode
     * @return
     */
    List<BusStation> getBusStationByLineCode(String lineCode, String direction, String areaCode);

    /**
     * 根据经纬度查询附近的站点2km
     * @param lat
     * @param lon
     * @param distance
     * @return
     */
    List<BusStation> getNearStationByGps(String lat, String lon, String distance);

    /**
     * 根据站点名称和区域id查询
     * @param stationName
     * @param areaId
     * @return
     */
    List<BusStation> queryStationLikeName(String stationName, String areaId);

    /**
     * 根据区域id查询所有的站点
     * @param areaId
     * @return
     */
    List<BusStation> findAllBusStation(String areaId);

    /**
     * 根据站点名称和上下行标识模糊查询站点
     * @param stationName
     * @param direction
     * @param areaId
     * @return
     */
    List<BusStation> findBusStationByStationName(String stationName, String direction, String areaId);

    /**
     *
     * @param stationName
     * @param areaId
     * @return
     */
    List<BusStation> findBusStationByStationName(String stationName, String areaId);
}
