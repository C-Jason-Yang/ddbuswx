package com.evcas.ddbuswx.service.impl;

import com.evcas.ddbuswx.dao.IUserDAO;
import com.evcas.ddbuswx.entity.BusCardBindingLog;
import com.evcas.ddbuswx.mapper.BusCardBindingLogMapper;
import com.evcas.ddbuswx.model.DwzPageModel;
import com.evcas.ddbuswx.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by noxn on 2018/9/23.
 */
@Service
public class BusCardBindingLogService {

    @Autowired
    private BusCardBindingLogMapper busCardBindingLogMapper;

    @Autowired
    private IUserDAO iUserDAO;

    public DwzPageModel page(DwzPageModel dwzPageModel, String wcOpenid) {
        Integer tempTotalCount = busCardBindingLogMapper.countTotal(wcOpenid);
        dwzPageModel.setTotalCount(tempTotalCount);
        List<BusCardBindingLog> busCardBindingLogList = busCardBindingLogMapper.page((dwzPageModel.getCurrentPage() - 1) * dwzPageModel.getNumPerPage(),
                dwzPageModel.getNumPerPage(), wcOpenid);
        if (busCardBindingLogList != null) {
            for (int i = 0; i < busCardBindingLogList.size(); i++) {
                BusCardBindingLog busCardBindingLog = busCardBindingLogList.get(i);
                if (busCardBindingLog.getCreateUserId() != null && !busCardBindingLog.getCreateUserId().equals("")) {
                    User user = iUserDAO.findUserById(busCardBindingLog.getCreateUserId());
                    busCardBindingLog.setCreateUserName(user.getUserName());
                    busCardBindingLogList.set(i, busCardBindingLog);
                }
            }
        }
        dwzPageModel.setDataList(busCardBindingLogList);
        BigDecimal totalCount = new BigDecimal(dwzPageModel.getTotalCount());
        BigDecimal numPerPage = new BigDecimal(dwzPageModel.getNumPerPage());
        dwzPageModel.setTotalPageNum(totalCount.divide(numPerPage, 0, BigDecimal.ROUND_UP).longValue());
        return dwzPageModel;
    }
}
