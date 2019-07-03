package com.evcas.ddbuswx.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by noxn on 2018/1/10.
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "线路")
public class BusLine {


    private String id;
    private Integer sendType;
    @ApiModelProperty(value = "线路编码", name = "lineCode")
    private String lineCode;
    @ApiModelProperty(value = "线路名称", name = "lineName")
    private String lineName;
    private Integer isannulus;
    private Integer scheduleMode;
    private Integer isoperations;
    private Integer islongtransport;
    private Integer groupId;
    private String reMark;
    private Integer limitSpeed;
    private double lineLength;
    private Integer onTime;
    private Integer lateTime;
    @ApiModelProperty(value = "区域id", name = "areaid")
    private String areaid;
    private String fromSys;
    @ApiModelProperty(value = "上行运营开始时间", name = "upLinkStartTime")
    private String upLinkStartTime;
    @ApiModelProperty(value = "上行运营结束时间", name = "upLinkEndTime")
    private String upLinkEndTime;
    @ApiModelProperty(value = "下行运营开始时间", name = "downLinkStartTime")
    private String downLinkStartTime;
    @ApiModelProperty(value = "下行运营结束时间", name = "downLinkEndTime")
    private String downLinkEndTime;
    private String areaCode;
    @ApiModelProperty(value = "上行开始站点", name = "upLinkStartStation")
    private String upLinkStartStation;
    @ApiModelProperty(value = "上行结束站点", name = "upLinkEndStation")
    private String upLinkEndStation;
    @ApiModelProperty(value = "下行开始站点", name = "downLinkStartStation")
    private String downLinkStartStation;
    @ApiModelProperty(value = "下行结束站点", name = "downLinkEndStation")
    private String downLinkEndStation;
    @ApiModelProperty(value = "上行站点集合", name = "upBusStationList")
    private List<BusStation> upBusStationList;
    @ApiModelProperty(value = "下行站点集合", name = "downLinkStartStation")
    private List<BusStation> downBusStationList;
    private String sortLineName;

    public BusLine(String fromSys) {
        this.fromSys = fromSys;
    }
}
