package com.evcas.ddbuswx.dao;

import com.evcas.ddbuswx.model.mongo.Bus;

import java.util.List;

/**
 * Created by noxn on 2018/1/11.
 */
public interface IBusDAO {

    /**
     * 批量添加车辆信息
     * @param busList
     */
    public void addBusInfoList(List<Bus> busList);

    /**
     * 根据区域id和来自系统删除车辆信息
     * @param areaId
     * @param fromSys
     */
    public void deleteBusInfoByAreaId(String areaId, String fromSys);


}
