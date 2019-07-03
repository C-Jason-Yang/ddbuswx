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
public class Line {

    private String lineCode;
    private String lineType;
    private String lineTypeName;

}
