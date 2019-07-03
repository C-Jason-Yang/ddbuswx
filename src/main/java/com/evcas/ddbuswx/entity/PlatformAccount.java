package com.evcas.ddbuswx.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 公交调度平台账号
 * Created by noxn on 2018/6/13.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlatformAccount {

    private String id;
    private String userName;
    private String password;
    private String areaMark;
    private String areaCode;
}
