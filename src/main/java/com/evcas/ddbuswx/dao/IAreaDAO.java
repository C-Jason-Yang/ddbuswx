package com.evcas.ddbuswx.dao;

import com.evcas.ddbuswx.model.Area;

/**
 * 区域信息
 * Created by noxn on 2018/8/11.
 */
public interface IAreaDAO {

    /**
     * 根据区域id获取区域信息
     * @param areaCode
     * @return
     */
    Area getAreaByAreaCode(String areaCode);

    /**
     * 根据区域id获取区域信息
     * @param areaId
     * @return
     */
    Area getAreaByAreaId(String areaId);
}
