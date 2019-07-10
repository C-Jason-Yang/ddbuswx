package com.evcas.ddbuswx.service.impl;

import com.evcas.ddbuswx.dao.IAreaDAO;
import com.evcas.ddbuswx.model.mongo.Area;
import com.evcas.ddbuswx.service.IAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by noxn on 2018/8/31.
 */
@Service
public class AreaService implements IAreaService {

    @Autowired
    private IAreaDAO iAreaDAO;

    @Override
    public Area getAreaByAreaId(String areaId) {
        return iAreaDAO.getAreaByAreaId(areaId);
    }
}
