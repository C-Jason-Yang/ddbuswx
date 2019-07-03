package com.evcas.ddbuswx.service;

import com.evcas.ddbuswx.model.BusStation;

import java.util.List;
import java.util.Map;

/**
 * Created by noxn on 2018/8/11.
 */
public interface IBusStationService {

    Map<String, List> getBusStationByLineCode(String areaCode, String lineCode);

    List<BusStation> getNearStationByGps(String lat, String lon);

    List<BusStation> queryStationLikeName(String stationName, String areaId);
}
