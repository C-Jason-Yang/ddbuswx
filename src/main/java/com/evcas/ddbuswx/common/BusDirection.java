package com.evcas.ddbuswx.common;

/**
 * 公交方向（上行，下行）
 * Created by noxn on 2018/8/11.
 */
public enum BusDirection {

    UpLink("上行", "1"),DownLink("下行", "2");

    private String key;
    private String value;

    BusDirection(String key, String value) {
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
