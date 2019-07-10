package com.evcas.ddbuswx.model.mongo;

import cn.hutool.core.date.DateUtil;
import com.evcas.ddbuswx.common.utils.UuidUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by noxn on 2018/3/7.
 */
@Data
@AllArgsConstructor
@ApiModel(value = "热门站点")
@Document(collection = "evcas_hot_bus_site")
public class HotSite {

    private String id;
    @ApiModelProperty(value = "区域id", name = "areaId")
    private String areaId;
    private String areaMark;
    @ApiModelProperty(value = "站点名称", name = "siteName")
    private String siteName;
    private String createTime = DateUtil.now();

    public HotSite() {
        this.id = UuidUtil.getUuid();
    }
}
