package com.evcas.ddbuswx.common;

import lombok.Data;

/**
 * Created by noxn on 2018/3/8.
 */
@Data
public class DwzCallBackResult {
    private Integer statusCode;
    private String message;
    private String navTabId;
    private String rel;
    private String callbackType;
    private String forwardUrl;
}
