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
public class HyBusLineShift {

    private String lineCode;
    private String planBegin;
    private String sxx;
    private String firstLast;
    private String areaid;
    private String fromSys;
}
