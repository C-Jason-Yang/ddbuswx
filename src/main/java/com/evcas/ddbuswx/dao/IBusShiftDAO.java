package com.evcas.ddbuswx.dao;

import com.evcas.ddbuswx.model.BusShift;

import java.util.List;

/**
 * Created by noxn on 2018/1/15.
 */
public interface IBusShiftDAO {

    void addBufShiftList(List<BusShift> busShiftList);

    void deleteBusShift(String areaId, String fromSys);

    List<BusShift> getBusShiftByLineId(String areaId, String lineId, Integer direction);
}
