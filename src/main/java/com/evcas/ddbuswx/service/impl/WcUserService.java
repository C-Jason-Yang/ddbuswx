package com.evcas.ddbuswx.service.impl;

import com.evcas.ddbuswx.entity.BusCardBindingLog;
import com.evcas.ddbuswx.entity.WcUser;
import com.evcas.ddbuswx.mapper.BusCardBindingLogMapper;
import com.evcas.ddbuswx.mapper.WcUserMapper;
import com.evcas.ddbuswx.model.DwzPageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by noxn on 2018/9/18.
 */
@Service
public class WcUserService {

    @Autowired
    private WcUserMapper wcUserMapper;

    @Autowired
    private BusCardBindingLogMapper busCardBindingLogMapper;

    @SuppressWarnings("unchecked")
    public DwzPageModel page(WcUser wcUserParam, DwzPageModel dwzPageModel) {
        Integer tempTotalCount = wcUserMapper.countTotal(wcUserParam);
        dwzPageModel.setTotalCount(tempTotalCount);
        wcUserParam.setPhoneNumIsNull(2);
        wcUserParam.setBindingPhoneUserNum(wcUserMapper.countTotal(wcUserParam));
        wcUserParam.setBusCardIsNull(2);
        wcUserParam.setBindingBusCardUserNum(wcUserMapper.countTotal(wcUserParam));
        List<WcUser> wcUserList = wcUserMapper.page((dwzPageModel.getCurrentPage() - 1) * dwzPageModel.getNumPerPage(),
                dwzPageModel.getNumPerPage(), wcUserParam);
        dwzPageModel.setDataList(wcUserList);
        dwzPageModel.setEntity(wcUserParam);
        BigDecimal totalCount = new BigDecimal(dwzPageModel.getTotalCount());
        BigDecimal numPerPage = new BigDecimal(dwzPageModel.getNumPerPage());
        dwzPageModel.setTotalPageNum(totalCount.divide(numPerPage, 0, BigDecimal.ROUND_UP).longValue());
        return dwzPageModel;
    }

    public WcUser getWcUserInfoById(String id) {
        return wcUserMapper.getWcUserInfoById(id);
    }

    public void removeBusCardBinding(String wcOpenid, String userId) {
        WcUser wcUser = wcUserMapper.getWcUserInfoByOpenid(wcOpenid);
        wcUserMapper.removeBusCardBinding(wcOpenid);
        BusCardBindingLog busCardBindingLog = new BusCardBindingLog();
        busCardBindingLog.setOpenid(wcOpenid);
        busCardBindingLog.setOperationType("2");
        busCardBindingLog.setAreaCode(wcUser.getAreaCode());
        busCardBindingLog.setAreaName(wcUser.getAreaName());
        busCardBindingLog.setBusCardNo(wcUser.getBusCard());
        busCardBindingLog.setCreateUserId(userId);
        busCardBindingLogMapper.insertBusCardBindingLog(busCardBindingLog);
    }
}
