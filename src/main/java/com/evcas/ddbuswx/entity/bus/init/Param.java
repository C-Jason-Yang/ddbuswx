package com.evcas.ddbuswx.entity.bus.init;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class Param {

    @JSONField(name = "ERRORCODE")
    private Integer errorCode;

    @JSONField(name = "BUSLINELIST")
    private List<BusLineListBean> busLineList;
}
