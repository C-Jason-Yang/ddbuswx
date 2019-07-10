package com.evcas.ddbuswx.model.mongo;

import cn.hutool.core.date.DateUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by noxn on 2018/1/11.
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "bus")
public class Bus {

    private Integer vehicleid;
    private String vehiclelicense;
    private String areaid;
    private String fromSys;
    private String createTime = DateUtil.now();

    public Bus(String fromSys) {
        this.fromSys = fromSys;
    }

}