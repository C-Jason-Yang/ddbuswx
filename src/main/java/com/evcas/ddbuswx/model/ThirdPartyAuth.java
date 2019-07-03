package com.evcas.ddbuswx.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 第三方权限
 * Created by noxn on 2018/8/11.
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ThirdPartyAuth {

    private String ip;
    private String pid;
    private String areaAuth;
}
