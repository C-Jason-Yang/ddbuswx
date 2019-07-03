package com.evcas.ddbuswx.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Created by noxn on 2018/9/23.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusCardBindingLog extends BaseEntity {

    private String openid;
    private String operationType;
    private String areaCode;
    private String areaName;
    private String busCardNo;


}
