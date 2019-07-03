package com.evcas.ddbuswx.common.utils;

import java.util.UUID;

/**
 * Created by noxn on 2018/1/12.
 */
public class UuidUtil {

    public static String getUuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
