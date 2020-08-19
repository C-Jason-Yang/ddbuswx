package com.evcas.ddbuswx.entity.bus.init;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class SiteListBean {
    @JSONField(name = "SITENAME")
    private String siteName;
    @JSONField(name = "DISTANCE")
    private Integer distance;
    @JSONField(name = "LONGITUDE")
    private Double longitude;
    @JSONField(name = "DIRECTION")
    private Integer direction;
    @JSONField(name = "SITENUM")
    private Integer siteNum;
    @JSONField(name = "RADIUS")
    private Integer radius;
    @JSONField(name = "MAINSITE")
    private Integer mainSite;
    @JSONField(name = "LINEID")
    private Integer lineId;
    @JSONField(name = "ID")
    private Integer id;
    @JSONField(name = "BIGSITE")
    private Integer bigSite;
    @JSONField(name = "LATITUDE")
    private Double latitude;
    @JSONField(name = "SITECODE")
    private String siteCode;
}