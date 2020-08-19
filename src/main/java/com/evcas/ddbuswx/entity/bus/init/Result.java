package com.evcas.ddbuswx.entity.bus.init;


import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Result {
    @JSONField(name = "MSGTYPE")
    private Integer msgType;
    @JSONField(name = "TRANSNO")
    private Integer transNo;
    @JSONField(name = "PARAM")
    private String param;
}
