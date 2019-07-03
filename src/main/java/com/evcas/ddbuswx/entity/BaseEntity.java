package com.evcas.ddbuswx.entity;

import com.evcas.ddbuswx.common.utils.ExcelUtil.annotation.ExcelImportField;
import com.evcas.ddbuswx.common.utils.ExcelUtil.constant.AutoCreateTypeEnum;
import com.evcas.ddbuswx.common.utils.UuidUtil;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

/**
 * Created by noxn on 2018/9/14.
 */

@Data
@AllArgsConstructor
public class BaseEntity {

    @ExcelImportField(autoCreate = true, autoCreateType = AutoCreateTypeEnum.UUID, sort = -1)
    public String id;
    public Date gmtCreate;
    public Date gmtModified;
    public String createUserId;
    public String updateUserId;
    //联表信息
    //创建人名称
    public String createUserName;

    public BaseEntity() {
        this.id = UuidUtil.getUuid();
        this.gmtCreate = new Date();
    }

}
