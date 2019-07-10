package com.evcas.ddbuswx.dao;

import com.evcas.ddbuswx.model.mongo.BusLine;

import java.util.List;

/**
 * Created by noxn on 2018/1/10.
 */
public interface IBusLineDAO {

    void addBusLineList(List<BusLine> busLineList);

    /**
     * 根据区域id和来自系统删除线路信息
     * @param areaId
     * @param fromSys
     */
    void deleteBusLineByAreaId(String areaId, String fromSys);

    /**
     * 根据区域编码查询线路信息
     * @param areaCode
     * @return
     */
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
     * 根据线路编码和区域id查询线路信息
     * @param lineCode
     * @param areaId
     * @return
     */
    BusLine queryBusLineByLineCodeAndAreaId(String lineCode, String areaId);
}
