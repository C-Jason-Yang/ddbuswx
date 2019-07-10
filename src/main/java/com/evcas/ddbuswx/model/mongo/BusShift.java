package com.evcas.ddbuswx.model.mongo;

import cn.hutool.core.date.DateUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by noxn on 2018/1/15.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "busShift")
public class BusShift {

    private String lineId;
    private String lineCode;
    private String lineName;
    private String areaid;
    private Integer vehicleid;
    private Integer biztype;
    private Integer stime;
    private Integer etime;
    private String vehicleno;
    private String fromSys;
    private String createTime = DateUtil.now();

}
