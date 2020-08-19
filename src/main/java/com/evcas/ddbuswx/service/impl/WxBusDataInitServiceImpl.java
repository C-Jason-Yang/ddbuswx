package com.evcas.ddbuswx.service.impl;

import com.evcas.ddbuswx.dao.IAreaDAO;
import com.evcas.ddbuswx.mapper.AreaMapper;
import com.evcas.ddbuswx.service.IHyLxService;
import com.evcas.ddbuswx.service.IHyYsService;
import com.evcas.ddbuswx.service.IRmService;
import com.evcas.ddbuswx.service.IWxBusDataInitService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by noxn on 2018/1/12.
 */
@Log4j2
@Service
public class WxBusDataInitServiceImpl implements IWxBusDataInitService {

    @Autowired
    private AreaMapper areaMapper;

    @Autowired
    private IAreaDAO areaDAO;

    @Autowired
    private IRmService iRmService;

    @Autowired
    private IHyYsService iHyYsService;

    @Autowired
    private IHyLxService iHyLxService;

    @Override
    public void wxBusDataInit() {

        //初始化区域信息信息到mongo
        long t = System.currentTimeMillis();
        areaDAO.initArea(areaMapper.findAll());

        long t1 = System.currentTimeMillis();
        log.info("初始化区域信息信息到mongo ======> 耗时：{}", t1 - t);

        //初始化瑞明公交数据
        iRmService.initializeRmBusRoute();

        long t2 = System.currentTimeMillis();
        log.info("初始化瑞明公交数据 ======> 耗时：{}", t2 - t1);

        //初始化恒宇颍上公交数据
//        iHyYsService.initializeHyYsBusSys();
//        long t3 = System.currentTimeMillis();
//        log.info("初始化恒宇颍上公交数据 ======> 耗时：{}", t3 - t2);

        //初始化恒宇利辛公交数据
//        iHyLxService.initializeHyLxBusSys();
//        long t4 = System.currentTimeMillis();
//        log.info("初始化恒宇利辛公交数据 ======> 耗时：{}", t4 - t3);
    }
}
