package com.evcas.ddbuswx.common;

import lombok.Getter;

/**
 * 公交方向（上行，下行）
 * Created by noxn on 2018/8/11.
 */
@Getter
public enum BusDirection {

    UpLink("上行", "1"),DownLink("下行", "2");

    private String key;
    private String value;

    BusDirection(String key, String value) {
        this.key = key;
        this.value = value;
    }

}
