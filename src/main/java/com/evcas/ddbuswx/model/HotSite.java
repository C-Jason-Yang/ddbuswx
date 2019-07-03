package com.evcas.ddbuswx.model;

import com.evcas.ddbuswx.common.utils.UuidUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by noxn on 2018/3/7.
 */
@Data
@AllArgsConstructor
@ApiModel(value = "热门站点")
public class HotSite {

    private String id;
    @ApiModelProperty(value = "区域id", name = "areaId")
    private String areaId;
    private String areaMark;
    @ApiModelProperty(value = "站点名称", name = "siteName")
    private String siteName;

    public HotSite() {
        this.id = UuidUtil.getUuid();
    }
}
