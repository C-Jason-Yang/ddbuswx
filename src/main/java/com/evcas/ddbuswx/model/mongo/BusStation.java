package com.evcas.ddbuswx.model.mongo;

import cn.hutool.core.date.DateUtil;
import com.evcas.ddbuswx.model.SpaceObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by noxn on 2018/1/10.
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "站点")
@Document(collection = "busStation")
public class BusStation {

    private String siteCode;
    @ApiModelProperty(value = "站点名称", name = "siteName")
    private String siteName;
    /**
     * /经度（WGS84坐标系）
     */
    @ApiModelProperty(value = "经度（WGS84坐标系）", name = "longitude")
    private double longitude;
    /**
     * /纬度（WGS84坐标系）
     */
    @ApiModelProperty(value = "纬度（WGS84坐标系）", name = "latitude")
    private double latitude;
    /**
     * 半径（单位：米）
     */
    private Integer radius;
    /**
     * 到下一个站的距离（单位：米
     */
    private Integer distance;
    /**
     * 上下行标志 1：上行  2：下行
     */
    @ApiModelProperty(value = "上下行标志 1：上行  2：下行", name = "direction")
    private Integer direction;
    /**
     * 站点序号
     */
    @ApiModelProperty(value = "站点序号", name = "sitenum")
    private Integer sitenum;
    /**
     * 备注
     */
    private String remark;
    private String areaid;
    /**
     * 百度经纬度
     */
    @ApiModelProperty(value = "百度纬度", name = "bdlat")
    private double bdlat;
    @ApiModelProperty(value = "百度经度", name = "bdlog")
    private double bdlog;
    /**
     * 站点所属线路信息
     */
    private String lineCode;
    private String lineName;
    private String lineId;
    private String fromSys;
    private Integer hySiteNum;
    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
    private SpaceObject location;
    private String createTime = DateUtil.now();

    public BusStation(String fromSys) {
        this.fromSys = fromSys;
    }
}
