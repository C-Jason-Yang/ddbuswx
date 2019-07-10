package com.evcas.ddbuswx.model.mongo;

import cn.hutool.core.date.DateUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 区域信息
 *
 * @author noxn
 * @date 2018/8/11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "area")
public class Area {

    private String areaName;
    private String areaCode;
    private String areaOldCode;
    private String createTime = DateUtil.now();

}
