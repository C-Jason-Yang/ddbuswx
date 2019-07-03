package com.evcas.ddbuswx.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by noxn on 2019/5/14.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LineLink {

    private String startStation;
    private String endStation;
    private String startTime;
    private String endTime;

}
