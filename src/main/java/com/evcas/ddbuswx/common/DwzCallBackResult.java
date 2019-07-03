package com.evcas.ddbuswx.common;

/**
 * Created by noxn on 2018/3/8.
 */
public class DwzCallBackResult {

    private Integer statusCode;
    private String message;
    private String navTabId;
    private String rel;
    private String callbackType;
    private String forwardUrl;

    public Integer getStatusCode() {
        return statusCode;
    }
    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public String getNavTabId() {
        return navTabId;
    }
    public void setNavTabId(String navTabId) {
        this.navTabId = navTabId;
    }
    public String getRel() {
        return rel;
    }
    public void setRel(String rel) {
        this.rel = rel;
    }
    public String getCallbackType() {
        return callbackType;
    }
    public void setCallbackType(String callbackType) {
        this.callbackType = callbackType;
    }
    public String getForwardUrl() {
        return forwardUrl;
    }
    public void setForwardUrl(String forwardUrl) {
        this.forwardUrl = forwardUrl;
    }
}
