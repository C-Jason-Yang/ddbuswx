package com.evcas.ddbuswx.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 红包活动
 * Created by noxn on 2018/9/20.
 */

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RedPacketActivity extends BaseEntity {

    private String areaCode;
    private String activityName;
    private String status;
    private String redPacketNum;
    private Date cancelDate;
    private Date confirmDate;
    private Date sendDate;
    private String cancelUserName;
    private String confirmUserName;
    private String sendUserName;
    private String actualSendRedpacketNum;
    private String planSendAmount;
    private String actualSendAmount;
    private String redPacketFilePath;
    //联表信息
    //区域名称
    private String areaName;
    //转换数据
    private String createStartDate;
    private String createEndDate;
}
