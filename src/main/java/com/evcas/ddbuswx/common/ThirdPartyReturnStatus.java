package com.evcas.ddbuswx.common;

/**
 * 第三方API返回状态
 * Created by noxn on 2018/8/11.
 */
public enum ThirdPartyReturnStatus {

    Normal("1", "正常"),NoAuth("2", "没有权限");

    private String key;
    private String value;

    ThirdPartyReturnStatus(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }
}
