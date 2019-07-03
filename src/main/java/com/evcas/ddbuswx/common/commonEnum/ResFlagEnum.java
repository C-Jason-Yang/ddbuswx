package com.evcas.ddbuswx.common.commonEnum;

/**
 * Created by noxn on 2019/5/9.
 */
public enum ResFlagEnum {

    Normal("1"),Empty("2");

    private String flag;

    ResFlagEnum(String flag) {
        this.flag = flag;
    }
    public String getFlag() {
        return flag;
    }
    public void setFlag(String flag) {
        this.flag = flag;
    }
}
