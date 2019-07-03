package com.evcas.ddbuswx.dao.impl;

import com.evcas.ddbuswx.common.SystemParameter;
import com.evcas.ddbuswx.common.hysys.MyServiceStub;
import com.evcas.ddbuswx.common.hysys.YsHyService;
import com.evcas.ddbuswx.dao.IHyYsDAO;
import org.springframework.stereotype.Repository;

/**
 * Created by noxn on 2018/1/12.
 */
@Repository
public class HyYsDAOImpl implements IHyYsDAO {

    @Override
    public String getBusLine() {
        try {
            MyServiceStub.SelectLineInfo selectLineInfo = new MyServiceStub.SelectLineInfo();
            selectLineInfo.setUserName(SystemParameter.YS_HY_BUS_ACCOUNT);
            selectLineInfo.setPassword(SystemParameter.YS_HY_BUS_PASSWORD);
            return YsHyService.myServiceStub.selectLineInfo(selectLineInfo).get_return();
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
            return YsHyService.myServiceStub.selectStation(selectStation).get_return();
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
            return YsHyService.myServiceStub.selectFirstLast(selectFirstLast).get_return();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
