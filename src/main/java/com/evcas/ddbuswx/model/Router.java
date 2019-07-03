package com.evcas.ddbuswx.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 公交线路
 * Created by noxn on 2019/5/17.
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "换乘方案")
public class Router {
    @ApiModelProperty(value = "换乘方案详情", name = "busRouterList")
    private List<BusRouter> busRouterList;

}