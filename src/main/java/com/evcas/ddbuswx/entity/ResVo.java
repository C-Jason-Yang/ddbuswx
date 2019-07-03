package com.evcas.ddbuswx.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by noxn on 2019/5/9.
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "返回数据集")
public class ResVo<T> {

    @ApiModelProperty(value = "结果标识(1 正常 2 没有数据)", name = "flag")
    private String flag;
    private String msg;
    private T data;

}
