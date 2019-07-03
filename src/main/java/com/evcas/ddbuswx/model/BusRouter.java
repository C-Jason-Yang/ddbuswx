package com.evcas.ddbuswx.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by noxn on 2019/5/17.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "换乘线路详情")
public class BusRouter {

    @ApiModelProperty(value = "线路名称", name = "lineName")
    private String lineName;
    @ApiModelProperty(value = "线路编码", name = "lineCode")
    private String lineCode;
    @ApiModelProperty(value = "开始站点名称", name = "startStationName")
    private String startStationName;
    @ApiModelProperty(value = "开始站点纬度", name = "startLat")
    private String startLat;
    @ApiModelProperty(value = "开始站点经度", name = "startLon")
    private String startLon;
    @ApiModelProperty(value = "结束站点名称", name = "endStationName")
    private String endStationName;
    @ApiModelProperty(value = "结束站点纬度", name = "endLat")
    private String endLat;
    @ApiModelProperty(value = "结束站点经度", name = "endLon")
    private String endLon;
    @ApiModelProperty(value = "经过站点数", name = "viaStationNum")
    private String viaStationNum;
    @ApiModelProperty(value = "站点集合", name = "busStationList")
    private List<BusStation> busStationList;
}
