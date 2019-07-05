package com.evcas.ddbuswx.common;

import lombok.Getter;

/**
 * 第三方API返回状态
 * Created by noxn on 2018/8/11.
 */
@Getter
public enum ThirdPartyReturnStatus {

    Normal("1", "正常"),NoAuth("2", "没有权限");

    private String key;
    private String value;

    ThirdPartyReturnStatus(String key, String value) {
        this.key = key;
        this.value = value;
    }

}
