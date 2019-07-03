package com.evcas.ddbuswx.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by noxn on 2018/1/15.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusShift {

    private String lineId;
    private String lineCode;
    private String lineName;
    private String areaid;
    private Integer vehicleid;
    private Integer biztype;
    private Integer stime;
    private Integer etime;
    private String vehicleno;
    private String fromSys;

}
