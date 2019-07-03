package com.evcas.ddbuswx.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Calendar;

/**
 * 车辆到离站数据
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "到离站信息")
public class RTBusArriveLeave {

    @ApiModelProperty(value = "区域id", name = "areaId")
    private String areaid;
    private Integer vehicleid;
    @ApiModelProperty(value = "线路编码", name = "line")
    private Integer line;
    @ApiModelProperty(value = "上下行标志 1：上行  2：下行", name = "biztype")
    private Integer biztype;
    @ApiModelProperty(value = "进出站类型1：进站；2：离站；3客流", name = "iostatus")
    private Integer iostatus;
    private Integer sid;
    @ApiModelProperty(value = "站点编码", name = "sno")
    private Integer sno;
    private float gpslat;
    private float gpslon;
    private Integer time;
    private String bugtag;
    private String fromSys;
    private Integer currentTime;

}
