package com.evcas.ddbuswx.entity.bus.init;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class BusLineListBean {

    @JSONField(name = "ISOPERATIONS")
    private Integer isOperations;
    @JSONField(name = "LINELENGTH")
    private Integer lineLength;
    @JSONField(name = "LINECODE")
    private String lineCode;
    @JSONField(name = "ISANNULUS")
    private Integer isAnnulus;
    @JSONField(name = "LIMITSPEED")
    private Integer limitSpeed;
    @JSONField(name = "SENDTYPE")
    private Integer sendType;
    @JSONField(name = "DOWNMILEAGE")
    private Integer downMileage;
    @JSONField(name = "GROUPID")
    private Integer groupId;
    @JSONField(name = "LINENAME")
    private String lineName;
    @JSONField(name = "SCHEDULEMODE")
    private Integer scheduleMode;
    @JSONField(name = "UPMILEAGE")
    private Integer upMileage;
    @JSONField(name = "ISLONGTRANSPORT")
    private Integer isLongTransport;
    @JSONField(name = "ID")
    private Integer id;
    @JSONField(name = "REMARK")
    private String remark;
    @JSONField(name = "SITELIST")
    private List<SiteListBean> siteList;
}
