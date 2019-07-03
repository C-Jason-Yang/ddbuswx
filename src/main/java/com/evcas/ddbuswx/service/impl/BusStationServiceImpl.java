package com.evcas.ddbuswx.service.impl;

import com.evcas.ddbuswx.common.BusDirection;
import com.evcas.ddbuswx.dao.IAreaDAO;
import com.evcas.ddbuswx.dao.IBusStationDAO;
import com.evcas.ddbuswx.model.Area;
import com.evcas.ddbuswx.model.BusStation;
import com.evcas.ddbuswx.service.IBusStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by noxn on 2018/8/11.
 */
@Service
public class BusStationServiceImpl implements IBusStationService {

    @Autowired
    private IBusStationDAO iBusStationDAO;

    @Autowired
    private IAreaDAO iAreaDAO;

    @Override
    public Map<String, List> getBusStationByLineCode(String areaCode, String lineCode) {
        Map<String, List> result = new HashMap<>();
//        Area area = iAreaDAO.getAreaByAreaCode(areaCode);
//        if (area != null) {
//            result.put(BusDirection.UpLink.name(), iBusStationDAO.getBusStationByLineCode(lineCode, BusDirection.UpLink.getValue(), area.getAreaOldCode()));
//            result.put(BusDirection.DownLink.name(), iBusStationDAO.getBusStationByLineCode(lineCode, BusDirection.DownLink.getValue(), area.getAreaOldCode()));
//            return result;
//        }
//        return null;
        result.put(BusDirection.UpLink.name(), iBusStationDAO.getBusStationByLineCode(lineCode, BusDirection.UpLink.getValue(), areaCode));
        result.put(BusDirection.DownLink.name(), iBusStationDAO.getBusStationByLineCode(lineCode, BusDirection.DownLink.getValue(), areaCode));
        return result;
    }

    @Override
    public List<BusStation> getNearStationByGps(String lat, String lon) {
        return iBusStationDAO.getNearStationByGps(lat, lon, "2000");
    }

    @Override
    public List<BusStation> queryStationLikeName(String stationName, String areaId) {
        return iBusStationDAO.queryStationLikeName(stationName, areaId);
    }
}
