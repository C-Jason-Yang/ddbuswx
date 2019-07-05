package com.evcas.ddbuswx.dao.impl;

import com.evcas.ddbuswx.common.SystemParameter;
import com.evcas.ddbuswx.common.lxhysys.LxHyService;
import com.evcas.ddbuswx.common.lxhysys.MyServiceStub;
import com.evcas.ddbuswx.dao.IHyLxDAO;
import org.springframework.stereotype.Repository;

/**
 * Created by noxn on 2018/1/17.
 */
@Repository
public class HyLxDAOImpl implements IHyLxDAO {


    @Override
    public String getBusLine() {
        try {
            MyServiceStub.SelectLineInfo selectLineInfo = new MyServiceStub.SelectLineInfo();
            selectLineInfo.setUserName(SystemParameter.YS_HY_BUS_ACCOUNT);
            selectLineInfo.setPassword(SystemParameter.YS_HY_BUS_PASSWORD);
            return LxHyService.myServiceStub.selectLineInfo(selectLineInfo).get_return();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getBusStation(String lineCode) {
        try {
            MyServiceStub.SelectStation selectStation = new MyServiceStub.SelectStation();
            selectStation.setLineCode(lineCode);
            selectStation.setUserName(SystemParameter.YS_HY_BUS_ACCOUNT);
            selectStation.setPassword(SystemParameter.YS_HY_BUS_PASSWORD);
            return LxHyService.myServiceStub.selectStation(selectStation).get_return();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getBusLineShift(String lineCode) {
        try {
            MyServiceStub.SelectFirstLast selectFirstLast = new MyServiceStub.SelectFirstLast();
            selectFirstLast.setLineCode(lineCode);
            selectFirstLast.setUserName(SystemParameter.YS_HY_BUS_ACCOUNT);
            selectFirstLast.setPassword(SystemParameter.YS_HY_BUS_PASSWORD);
            return LxHyService.myServiceStub.selectFirstLast(selectFirstLast).get_return();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
