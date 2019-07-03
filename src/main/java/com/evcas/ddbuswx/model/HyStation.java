package com.evcas.ddbuswx.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by noxn on 2018/1/12.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HyStation {

    private String lineCode;
    private String statName;
    private String upDownName;
    private String statLongitude;
    private String statLatitude;
    private Integer stationOrder;
    private double statSpaceInterval;
}
