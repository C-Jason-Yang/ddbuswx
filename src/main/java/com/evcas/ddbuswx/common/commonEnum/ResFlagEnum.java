package com.evcas.ddbuswx.common.commonEnum;

import lombok.Getter;

/**
 * Created by noxn on 2019/5/9.
 */
@Getter
public enum ResFlagEnum {

    Normal("1"),Empty("2");

    private String flag;

    ResFlagEnum(String flag) {
        this.flag = flag;
    }
}
