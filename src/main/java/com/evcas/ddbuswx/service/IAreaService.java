package com.evcas.ddbuswx.service;

import com.evcas.ddbuswx.model.mongo.Area;

/**
 * Created by noxn on 2018/8/31.
 */
public interface IAreaService {

    /**
     * 根据区域id获取区域信息
     * @param areaId
     * @return
     */
    Area getAreaByAreaId(String areaId);
}
