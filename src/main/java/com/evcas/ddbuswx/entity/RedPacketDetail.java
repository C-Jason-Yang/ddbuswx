package com.evcas.ddbuswx.entity;

import com.evcas.ddbuswx.common.utils.ExcelUtil.annotation.ExcelImportField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 红包详情
 * Created by noxn on 2018/9/19.
 */

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RedPacketDetail extends BaseEntity {

    private String openid;
    private String phone;
    @ExcelImportField(autoCreate = false, sort = 1, validate = true, regex = "(?=^(([1-9]\\d*)(\\.\\d{1,2})?|0\\.([1-9]|\\d[1-9])|0)$)\\d+(\\.\\d{1,2})$", cellValueType = "NUMERIC")
    private Double amount;
    @ExcelImportField(autoCreate = false, sort = 0, validate = true, regex = "([A-Z a-z 0-9]{1,32})", cellValueType = "STRING")
    private String busCardNo;
    private String redPacketActivityId;
    private String status;
    private String mchBillno;
    private String receive;
    private Date sendDate;
    private Date receiveDate;
    //联表信息
    //区域名称
    private String areaName;
    private String areaCode;
    //活动名称
    private String activityName;
    //转换数据
    private String sendStartDateStr;
    private String sendEndDateStr;
}
