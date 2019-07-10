package com.evcas.ddbuswx.model;

import com.evcas.ddbuswx.model.mongo.BusStation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by noxn on 2019/5/15.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusStationInfo {
    private String siteName;
    /**
     * 经度（WGS84坐标系）
     */
    private double longitude;
    /**
     * 纬度（WGS84坐标系）
     */
    private double latitude;
    private String areaid;
    /**
     * 百度经纬度
     */
    private double bdlat;
    private double bdlog;
    private List<BusStation> busStations;
}
