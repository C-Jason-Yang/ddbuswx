package com.evcas.ddbuswx.scheduled;

import cn.hutool.core.date.DateUtil;
import com.evcas.ddbuswx.dao.IRTBusArriveLeaveDAO;
import com.evcas.ddbuswx.model.RTBusArriveLeave;
import com.evcas.ddbuswx.service.IWxBusDataInitService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by noxn on 2018/1/31.
 */
@Log4j2
@Configuration
@EnableScheduling
public class SchedulingConfig {

    @Autowired
    private IRTBusArriveLeaveDAO irtBusArriveLeaveDAO;

    @Autowired
    private IWxBusDataInitService iWxBusDataInitService;

    @Scheduled(cron = "0 0/1 * * * ?") // 每5分钟执行一次
    public void filterRTBusArriveLeave() {
        log.info("task ======== filterRTBusArriveLeave  begin ========:{}", DateUtil::now);
        List<RTBusArriveLeave> rtBusArriveLeaveList = irtBusArriveLeaveDAO.findAllRTBusArriveLeave();
        if (rtBusArriveLeaveList != null) {
            for (RTBusArriveLeave rtBusArriveLeave : rtBusArriveLeaveList) {
                Calendar cal = Calendar.getInstance();
                //当数据未更新时间大于5分钟
                if(rtBusArriveLeave != null){
                    if (Integer.parseInt(String.valueOf(cal.getTimeInMillis() / 1000)) - rtBusArriveLeave.getCurrentTime() > 300) {
                        irtBusArriveLeaveDAO.deleteRTBusArriveLeaveByBusTag(rtBusArriveLeave.getAreaid(), rtBusArriveLeave.getFromSys(), rtBusArriveLeave.getBugtag());
                    }
                }
            }
        }
        log.info("task ======== filterRTBusArriveLeave  end ========:{}", DateUtil::now);
    }

//    @Scheduled(cron = "0 0 6 * * ?") // 每6点执行一次
    public void initBusData() {
        log.info("task ======== initBusData  begin ========:{}", DateUtil::now);
        iWxBusDataInitService.wxBusDataInit();
        log.info("task ======== initBusData  end ========:{}", DateUtil::now);
    }
}
