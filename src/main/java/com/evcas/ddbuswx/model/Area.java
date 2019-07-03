package com.evcas.ddbuswx.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 区域信息
 *
 * @author noxn
 * @date 2018/8/11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Area {

    private String areaName;
    private String areaCode;
    private String areaOldCode;

}
