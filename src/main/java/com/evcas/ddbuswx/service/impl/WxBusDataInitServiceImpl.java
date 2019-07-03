package com.evcas.ddbuswx.service.impl;

import com.evcas.ddbuswx.service.IHyLxService;
import com.evcas.ddbuswx.service.IHyYsService;
import com.evcas.ddbuswx.service.IRmService;
import com.evcas.ddbuswx.service.IWxBusDataInitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by noxn on 2018/1/12.
 */
@Service
public class WxBusDataInitServiceImpl implements IWxBusDataInitService {

    @Autowired
    private IRmService iRmService;

    @Autowired
    private IHyYsService iHyYsService;

    @Autowired
    private IHyLxService iHyLxService;

    @Override
    public void wxBusDataInit() {
        //初始化瑞明公交数据
        iRmService.initializeRmBusRoute();
//        初始化恒宇颍上公交数据
        iHyYsService.initializeHyYsBusSys();
//        初始化恒宇利辛公交数据
        iHyLxService.initializeHyLxBusSys();
    }
}
