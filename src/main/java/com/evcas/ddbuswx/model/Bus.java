package com.evcas.ddbuswx.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by noxn on 2018/1/11.
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Bus {

    private Integer vehicleid;
    private String vehiclelicense;
    private String areaid;
    private String fromSys;

    public Bus(String fromSys) {
        this.fromSys = fromSys;
    }

}