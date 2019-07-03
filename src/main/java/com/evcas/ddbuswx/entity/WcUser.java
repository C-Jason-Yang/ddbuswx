package com.evcas.ddbuswx.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 微信用户信息
 * Created by noxn on 2018/9/14.
 */

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WcUser extends BaseEntity {

    private String wcName;
    private String wcSex;
    private String wcProfilePhoto;
    private String wcOpenid;
    private String phone;
    private String busCard;
    private String areaCode;
    //联表信息
    //区域名称
    private String areaName;
    //转换数据
    private String registerStartDate;
    private String registerEndDate;
    private Integer bindingPhoneUserNum;
    private Integer bindingBusCardUserNum;
    //查询条件
    //手机号是否为空 1 为空 2 不为空
    private Integer phoneNumIsNull;
    //公交卡是否为空 1 为空 2 不为空
    private Integer busCardIsNull;
}
