package com.evcas.ddbuswx.scheduled;

import com.evcas.ddbuswx.common.SystemParameter;
import com.evcas.ddbuswx.common.utils.*;
import com.evcas.ddbuswx.dao.IRTBusArriveLeaveDAO;
import com.evcas.ddbuswx.entity.RedPacketDetail;
import com.evcas.ddbuswx.mapper.RedPacketDetailMapper;
import com.evcas.ddbuswx.model.RTBusArriveLeave;
import com.evcas.ddbuswx.service.IWxBusDataInitService;
import com.evcas.ddbuswx.service.impl.RedPacketDetailService;
import com.evcas.ddbuswx.tcp.DiscardServerHandler;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.net.ssl.SSLContext;
import java.io.*;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.*;

/**
 * Created by noxn on 2018/1/31.
 */
@Configuration
@EnableScheduling
public class SchedulingConfig {

    @Autowired
    private IRTBusArriveLeaveDAO irtBusArriveLeaveDAO;

    @Autowired
    private IWxBusDataInitService iWxBusDataInitService;

    @Scheduled(cron = "0 0/1 * * * ?") // 每5分钟执行一次
    public void filterRTBusArriveLeave() {
        List<RTBusArriveLeave> rtBusArriveLeaveList = irtBusArriveLeaveDAO.findAllRTBusArriveLeave();
        if (rtBusArriveLeaveList != null) {
            for (int i = 0; i < rtBusArriveLeaveList.size(); i++) {
                Calendar cal = Calendar.getInstance();
                //当数据未更新时间大于5分钟
                if (Integer.valueOf(String.valueOf(cal.getTimeInMillis()/1000)) - rtBusArriveLeaveList.get(i).getCurrentTime() > 300) {
                    irtBusArriveLeaveDAO.deleteRTBusArriveLeaveByBusTag(rtBusArriveLeaveList.get(i).getAreaid(), rtBusArriveLeaveList.get(i).getFromSys(), rtBusArriveLeaveList.get(i).getBugtag());
                }
            }
        }
    }

    @Scheduled(cron = "0 0 6 * * ?") // 每6点执行一次
    public void initBusData() {
        System.out.println(new Date().getTime());
        iWxBusDataInitService.wxBusDataInit();
    }
}
