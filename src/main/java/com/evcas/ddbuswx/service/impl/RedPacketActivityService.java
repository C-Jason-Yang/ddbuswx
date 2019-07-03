package com.evcas.ddbuswx.service.impl;

import com.evcas.ddbuswx.common.SystemParameter;
import com.evcas.ddbuswx.common.utils.*;
import com.evcas.ddbuswx.dao.IUserDAO;
import com.evcas.ddbuswx.entity.RedPacketActivity;
import com.evcas.ddbuswx.entity.RedPacketDetail;
import com.evcas.ddbuswx.entity.WcUser;
import com.evcas.ddbuswx.mapper.RedPacketActivityMapper;
import com.evcas.ddbuswx.mapper.RedPacketDetailMapper;
import com.evcas.ddbuswx.mapper.WcUserMapper;
import com.evcas.ddbuswx.model.DwzPageModel;
import com.evcas.ddbuswx.model.User;
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
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.net.ssl.SSLContext;
import java.io.*;
import java.math.BigDecimal;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by noxn on 2018/9/20.
 */
@Service
public class RedPacketActivityService {

    @Autowired
    private RedPacketActivityMapper redPacketActivityMapper;

    @Autowired
    private RedPacketDetailMapper redPacketDetailMapper;

    @Autowired
    private WcUserMapper wcUserMapper;

    @Autowired
    private IUserDAO iUserDAO;

    public void addRedPacketActivity(RedPacketActivity redPacketActivity, String tempRedPacketActivityId, String userId) {
        //统计红包总数和修改红包详情关联的红包活动id
        Integer redPacketNum = redPacketDetailMapper.countRedPacketDetailNumByActivityId(tempRedPacketActivityId);
        //统计红包计划发放金额
        RedPacketDetail sumAmountParam = new RedPacketDetail();
        sumAmountParam.setRedPacketActivityId(tempRedPacketActivityId);
        String totalAmount = redPacketDetailMapper.sumAmount(sumAmountParam);
        redPacketDetailMapper.updateRedPacketDetailActivityId(tempRedPacketActivityId, redPacketActivity.getId());
        //待确认
        redPacketActivity.setStatus("1");
        redPacketActivity.setRedPacketNum(String.valueOf(redPacketNum));
        redPacketActivity.setCreateUserId(userId);
        redPacketActivity.setActualSendRedpacketNum("0");
        redPacketActivity.setPlanSendAmount(String.valueOf(totalAmount));
        redPacketActivity.setActualSendAmount("0");
        redPacketActivityMapper.insertRedPacketActivity(redPacketActivity);
    }

    public void updateRedPacketActivityStatus(String id, String status, String userId) {
        User user = iUserDAO.findUserById(userId);
        if (status.equals("2")) {
            redPacketActivityMapper.updateRedPacketActivityStatus(id, status, null,
                    new Date(), null, null, user.getUserName(), null);
        } else if (status.equals("4")) {
            redPacketActivityMapper.updateRedPacketActivityStatus(id, status, new Date(),
                    null, null, user.getUserName(), null, null);
        }
    }

    public DwzPageModel page(RedPacketActivity redPacketActivity, DwzPageModel dwzPageModel) {
        Integer tempTotalCount = redPacketActivityMapper.countTotal(redPacketActivity);
        dwzPageModel.setTotalCount(tempTotalCount);
        List<RedPacketActivity> redPacketActivityList = redPacketActivityMapper.page((dwzPageModel.getCurrentPage() - 1) * dwzPageModel.getNumPerPage(),
                dwzPageModel.getNumPerPage(), redPacketActivity);
        if (redPacketActivityList != null) {
            for (int i = 0; i < redPacketActivityList.size(); i++) {
                User user = iUserDAO.findUserById(redPacketActivityList.get(i).getCreateUserId());
                redPacketActivityList.get(i).setCreateUserName(user.getUserName());
            }
        }
        dwzPageModel.setDataList(redPacketActivityList);
        dwzPageModel.setEntity(redPacketActivity);
        BigDecimal totalCount = new BigDecimal(dwzPageModel.getTotalCount());
        BigDecimal numPerPage = new BigDecimal(dwzPageModel.getNumPerPage());
        dwzPageModel.setTotalPageNum(totalCount.divide(numPerPage, 0, BigDecimal.ROUND_UP).longValue());
        return dwzPageModel;
    }

    @Async
    public void sendRedPacket(String id, String userId) {
        Integer actualSendRedpacketNum = 0;
        Integer actualSendAmount = 0;
        RedPacketActivity redPacketActivity = redPacketActivityMapper.getById(id);
        List<RedPacketDetail> redPacketDetailList = redPacketDetailMapper.findRedPacketDetailByActivityId(id);
        if (redPacketDetailList != null) {
            for (int i = 0; i < redPacketDetailList.size(); i++) {
                if (redPacketDetailList.get(i).getStatus() != null && redPacketDetailList.get(i).getStatus().equals("2")) {
                    //跳过已成功发放的
                    continue;
                }
                //商户订单号
                String mchBillno = RandomStr.getNumCharUppercaseRandomStr(28);
                WcUser wcUser = wcUserMapper.findUserByBusCardNo(redPacketDetailList.get(i).getBusCardNo(),
                        redPacketActivity.getAreaCode());
                if (wcUser == null) {
                    redPacketDetailMapper.updateRedPacketDetailStatusById(redPacketDetailList.get(i).getId(), "3");

                    RedPacketDetail tempRedPacketDetail = new RedPacketDetail();
                    tempRedPacketDetail.setId(redPacketDetailList.get(i).getId());

                    tempRedPacketDetail.setMchBillno(mchBillno);
                    //发放时间
                    tempRedPacketDetail.setSendDate(new Date());
                    tempRedPacketDetail.setReceive("4");
                    redPacketDetailMapper.updateRedPacketDetailById(tempRedPacketDetail);
                    continue;
                }
                KeyStore keyStore = null;
                try {
                    keyStore = KeyStore.getInstance("PKCS12");
                } catch (KeyStoreException e) {
                    e.printStackTrace();
                }
                FileInputStream instream = null;
                try {
                    instream = new FileInputStream(new File(SystemParameter.PKCS12_FILE_PATH));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                try {
                    keyStore.load(instream, "1508163181".toCharArray());
                } catch (IOException exp) {
                    exp.printStackTrace();
                } catch (NoSuchAlgorithmException ex) {
                    ex.printStackTrace();
                } catch (CertificateException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        instream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                SSLContext sslcontext = null;
                try {
                    sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, "1508163181".toCharArray()).build();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (KeyStoreException ke) {
                    ke.printStackTrace();
                } catch (UnrecoverableKeyException uke) {
                    uke.printStackTrace();
                } catch (KeyManagementException kme) {
                    kme.printStackTrace();
                }
                SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[]{"TLSv1"},
                        null, SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
                CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
                try {
                    String actName = redPacketActivity.getActivityName();
                    HttpPost httpPost = new HttpPost(SystemParameter.WE_CHAT_SENDREDPACK);
                    System.out.println("executing request" + httpPost.getRequestLine());
                    httpPost.addHeader("Connection", "keep-alive");
                    httpPost.addHeader("Accept", "*/*");
                    httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
                    httpPost.addHeader("Host", "api.mch.weixin.qq.com");
                    httpPost.addHeader("X-Requested-With", "XMLHttpRequest");
                    httpPost.addHeader("Cache-Control", "max-age=0");
                    httpPost.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0) ");

                    String nonceStr = UuidUtil.getUuid();
                    Double amount = redPacketDetailList.get(i).getAmount().doubleValue();
                    Integer tempamount = Integer.valueOf(String.valueOf(amount * 100).split("\\.")[0]);
                    TreeMap signTreeMap = new TreeMap();
                    signTreeMap.put("nonce_str", nonceStr);
                    signTreeMap.put("mch_billno", mchBillno);
                    signTreeMap.put("mch_id", "1508163181");
                    signTreeMap.put("wxappid", SystemParameter.WE_CHAT_DADAO_APPID);
                    signTreeMap.put("send_name", actName);
                    signTreeMap.put("re_openid", wcUser.getWcOpenid());
                    signTreeMap.put("total_amount", tempamount);
                    signTreeMap.put("total_num", 1);
                    signTreeMap.put("wishing", "鑫大道公交祝您乘车愉快");
                    signTreeMap.put("client_ip", SystemParameter.WE_CHAT_API_CLIENT_IP);
                    signTreeMap.put("act_name", actName);
                    signTreeMap.put("remark", "鑫大道公交");
//                    signTreeMap.put("scene_id", "PRODUCT_2");

                    String sign = WeChatUtil.getSignBeForeEncryption(signTreeMap);
                    sign = Md5Util.getMD5(sign);

                    StringBuilder xmlSb = new StringBuilder();
                    xmlSb.append("<xml>");
                    xmlSb.append("     <sign>" + sign + "</sign>");
                    xmlSb.append("     <mch_billno>" + mchBillno + "</mch_billno>");
                    xmlSb.append("     <mch_id>1508163181</mch_id>");
                    xmlSb.append("     <wxappid>"+ SystemParameter.WE_CHAT_DADAO_APPID +"</wxappid>");
                    xmlSb.append("     <send_name>"+ actName +"</send_name>");
                    xmlSb.append("     <re_openid>"+ wcUser.getWcOpenid() +"</re_openid>");
                    xmlSb.append("     <total_amount>" + tempamount + "</total_amount>");
                    xmlSb.append("     <total_num>" + 1 + "</total_num>");
                    xmlSb.append("     <wishing>鑫大道公交祝您乘车愉快</wishing>");
                    xmlSb.append("     <client_ip>"+ SystemParameter.WE_CHAT_API_CLIENT_IP +"</client_ip>");
                    xmlSb.append("     <act_name>"+ actName +"</act_name>");
                    xmlSb.append("     <remark>鑫大道公交</remark>");
                    xmlSb.append("     <nonce_str>" + nonceStr + "</nonce_str>");
//                    xmlSb.append("     <scene_id>PRODUCT_2</scene_id>");
                    xmlSb.append("</xml>");
                    httpPost.setEntity(new StringEntity(xmlSb.toString(), "UTF-8"));
                    CloseableHttpResponse response = null;
                    try {
                        response = httpclient.execute(httpPost);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        HttpEntity entity = response.getEntity();
                        System.out.println("----------------------------------------");
                        System.out.println(response.getStatusLine());
                        if (entity != null) {
                            System.out.println("Response content length: " + entity.getContentLength());
                            BufferedReader bufferedReader = null;
                            try {
                                bufferedReader = new BufferedReader(new InputStreamReader(entity.getContent()));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            StringBuilder sendResultSb = new StringBuilder();
                            String text;
                            try {
                                while ((text = bufferedReader.readLine()) != null) {
                                    sendResultSb.append(text);
                                }
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                            System.err.println(sendResultSb.toString());
                            Map<String, String> sendRedPacketResult = XmlUtil.xmlToMap(sendResultSb.toString());
                            //成功
                            if (sendRedPacketResult.get("return_code").equals("SUCCESS") && sendRedPacketResult.get("result_code").equals("SUCCESS")) {
                                redPacketDetailMapper.updateRedPacketDetailStatusById(redPacketDetailList.get(i).getId(), "2");
                                RedPacketDetail redPacketDetail = new RedPacketDetail();
                                redPacketDetail.setId(redPacketDetailList.get(i).getId());
                                if (wcUser.getPhone() != null && !wcUser.getPhone().equals("")) {
                                    redPacketDetail.setPhone(wcUser.getPhone());
                                }
                                redPacketDetail.setOpenid(wcUser.getWcOpenid());
                                //商户订单号
                                redPacketDetail.setMchBillno(mchBillno);
                                //红包状态 未领取
                                redPacketDetail.setReceive("1");
                                //发放时间
                                redPacketDetail.setSendDate(new Date());
                                redPacketDetailMapper.updateRedPacketDetailById(redPacketDetail);
                                actualSendRedpacketNum++;
                                actualSendAmount = actualSendAmount + tempamount;
                            } else {
                                //失败
                                if (sendRedPacketResult.get("err_code").equals("NOTENOUGH")) {
                                    redPacketDetailMapper.updateRedPacketDetailStatusById(redPacketDetailList.get(i).getId(), "3");
                                }
                                if (sendRedPacketResult.get("err_code").equals("SENDNUM_LIMIT")) {
                                    redPacketDetailMapper.updateRedPacketDetailStatusById(redPacketDetailList.get(i).getId(), "3");
                                }
                                RedPacketDetail redPacketDetail = new RedPacketDetail();
                                redPacketDetail.setId(redPacketDetailList.get(i).getId());
                                if (wcUser.getPhone() != null && !wcUser.getPhone().equals("")) {
                                    redPacketDetail.setPhone(wcUser.getPhone());
                                }
                                redPacketDetail.setMchBillno(mchBillno);
                                redPacketDetail.setOpenid(wcUser.getWcOpenid());
                                //发放时间
                                redPacketDetail.setSendDate(new Date());
                                redPacketDetail.setReceive("4");
                                redPacketDetailMapper.updateRedPacketDetailById(redPacketDetail);
                            }
                        }
                        try {
                            EntityUtils.consume(entity);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } finally {
                        try {
                            response.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } finally {
                    try {
                        httpclient.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            User user = iUserDAO.findUserById(userId);
            RedPacketActivity redPacketActivityUpdateParam = new RedPacketActivity();
            redPacketActivityUpdateParam.setId(id);
            redPacketActivityUpdateParam.setActualSendRedpacketNum(String.valueOf(actualSendRedpacketNum));
            redPacketActivityUpdateParam.setActualSendAmount(String.valueOf(actualSendAmount/100) + "." + String.valueOf(actualSendAmount%100));
            redPacketActivityMapper.update(redPacketActivityUpdateParam);
            redPacketActivityMapper.updateRedPacketActivityStatus(id, "3", null,
                    null, new Date(), null, null, user.getUserName());
            SystemParameter.RED_PACKET_ID_LIST.remove(id);
        }
    }

    public RedPacketActivity getRedPacketInfo(String id) {
        RedPacketActivity redPacketActivity = redPacketActivityMapper.getById(id);
        User user = iUserDAO.findUserById(redPacketActivity.getCreateUserId());
        redPacketActivity.setCreateUserName(user.getUserName());
        return redPacketActivity;
    }
}