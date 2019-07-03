package com.evcas.ddbuswx.dao;

import com.evcas.ddbuswx.model.RTBusArriveLeave;

import java.util.List;

/**
 * Created by noxn on 2018/1/31.
 */
public interface IRTBusArriveLeaveDAO {

    List<RTBusArriveLeave> findAllRTBusArriveLeave();

    void deleteRTBusArriveLeaveByBusTag(String areaId, String fromSys, String busTag);

    List<RTBusArriveLeave> findRTBusArriveLeaveBy(String areaId, String lineCode, String direction);
}
