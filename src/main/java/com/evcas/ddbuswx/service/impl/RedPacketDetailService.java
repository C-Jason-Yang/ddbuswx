package com.evcas.ddbuswx.service.impl;

import com.evcas.ddbuswx.common.SystemParameter;
import com.evcas.ddbuswx.common.utils.Md5Util;
import com.evcas.ddbuswx.common.utils.UuidUtil;
import com.evcas.ddbuswx.common.utils.WeChatUtil;
import com.evcas.ddbuswx.common.utils.XmlUtil;
import com.evcas.ddbuswx.entity.RedPacketDetail;
import com.evcas.ddbuswx.mapper.RedPacketDetailMapper;
import com.evcas.ddbuswx.model.DwzPageModel;
import com.google.common.collect.Maps;
import lombok.Cleanup;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.CellType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.net.ssl.SSLContext;
import java.io.*;
import java.math.BigDecimal;
import java.security.*;
import java.security.cert.CertificateException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by noxn on 2018/9/19.
 */
@Log4j2
@SuppressWarnings("ALL")
@Service
public class RedPacketDetailService {


    @Autowired
    private RedPacketDetailMapper redPacketDetailMapper;

    public String batchAddRedPacketDetail(List<RedPacketDetail> redPacketDetailList) {
        if (redPacketDetailList != null && redPacketDetailList.size() > 0) {
            String redPacketActivityId = UuidUtil.getUuid();
            for (int i = 0; i < redPacketDetailList.size(); i++) {
                RedPacketDetail redPacketDetail = redPacketDetailList.get(i);
                redPacketDetail.setStatus("1");
                redPacketDetail.setRedPacketActivityId(redPacketActivityId);
                redPacketDetailList.set(i, redPacketDetail);
            }
            try {
                redPacketDetailMapper.batchInsertRedPacketDetail(redPacketDetailList);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return redPacketActivityId;
        }
        return null;
    }

    @SuppressWarnings("Duplicates")
    public void queryRedPacketAcceptInfo() {
        Date today = new Date();
        Calendar calBegin = Calendar.getInstance();
        calBegin.setTime(today);
        calBegin.add(Calendar.DAY_OF_MONTH, -1);
        Date yesterday = calBegin.getTime();
        calBegin.add(Calendar.DAY_OF_MONTH, -1);
        Date dayBeforeYesterday = calBegin.getTime();
        List<RedPacketDetail> redPacketDetailList = redPacketDetailMapper.queryRedPacketAcceptInfo(today, yesterday, dayBeforeYesterday);
        if (redPacketDetailList != null) {
            for (RedPacketDetail packetDetail : redPacketDetailList) {
                KeyStore keyStore = null;
                try {
                    keyStore = KeyStore.getInstance("PKCS12");
                } catch (KeyStoreException e) {
                    e.printStackTrace();
                }
                try {
                    @Cleanup FileInputStream instream = new FileInputStream(new File(SystemParameter.PKCS12_FILE_PATH));

                    if (keyStore == null) {
                        throw new NullPointerException("keyStore is null");
                    }
                    keyStore.load(instream, "1508163181".toCharArray());
                } catch (IOException | NoSuchAlgorithmException | CertificateException exp) {
                    exp.printStackTrace();
                }
                SSLContext sslcontext = null;
                try {
                    sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, "1508163181".toCharArray()).build();
                } catch (NoSuchAlgorithmException | KeyStoreException | UnrecoverableKeyException | KeyManagementException e) {
                    e.printStackTrace();
                }

                if (sslcontext == null) {
                    throw new NullPointerException("sslcontext is null");
                }
                SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[]{"TLSv1"},
                        null, SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);


                CloseableHttpClient httpclient = null;
                try {
                    httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();

                    HttpPost httpPost = new HttpPost(SystemParameter.WE_CHAT_GETHBINFO);
                    log.info("executing request" + httpPost.getRequestLine());
                    httpPost.addHeader("Connection", "keep-alive");
                    httpPost.addHeader("Accept", "*/*");
                    httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
                    httpPost.addHeader("Host", "api.mch.weixin.qq.com");
                    httpPost.addHeader("X-Requested-With", "XMLHttpRequest");
                    httpPost.addHeader("Cache-Control", "max-age=0");
                    httpPost.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0) ");

                    String nonceStr = UuidUtil.getUuid();
                    String mchBillno = packetDetail.getMchBillno();
                    TreeMap signTreeMap = new TreeMap();
                    signTreeMap.put("nonce_str", nonceStr);
                    signTreeMap.put("mch_billno", mchBillno);
                    signTreeMap.put("mch_id", "1508163181");
                    signTreeMap.put("appid", "wxc609948134e10033");
                    signTreeMap.put("bill_type", "MCHT");

                    String sign = WeChatUtil.getSignBeForeEncryption(signTreeMap);
                    sign = Md5Util.getMD5(sign);

                    StringBuilder xmlSb = new StringBuilder();
                    xmlSb.append("<xml>");
                    xmlSb.append("     <sign>").append(sign).append("</sign>");
                    xmlSb.append("     <mch_billno>").append(mchBillno).append("</mch_billno>");
                    xmlSb.append("     <mch_id>1508163181</mch_id>");
                    xmlSb.append("     <appid>wxc609948134e10033</appid>");
                    xmlSb.append("     <nonce_str>").append(nonceStr).append("</nonce_str>");
                    xmlSb.append("     <bill_type>MCHT</bill_type>");
                    xmlSb.append("</xml>");
                    httpPost.setEntity(new StringEntity(xmlSb.toString(), "UTF-8"));

                    HttpEntity entity = null;
                    try {
                        @Cleanup CloseableHttpResponse response = httpclient.execute(httpPost);
                        if (response == null) {
                            throw new NullPointerException("response is null");
                        }
                        entity = response.getEntity();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    if (entity != null) {
                        Map<String, String> redPacketResult = Maps.newHashMap();
                        try {
                            @Cleanup BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(entity.getContent()));

                            StringBuilder sendResultSb = new StringBuilder();
                            String text;
                            while ((text = bufferedReader.readLine()) != null) {
                                sendResultSb.append(text);
                            }
                            System.err.println(sendResultSb.toString());
                            redPacketResult = XmlUtil.xmlToMap(sendResultSb.toString());
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }

                        //成功
                        if (redPacketResult == null) {
                            throw new NullPointerException("redPacketResult is null");
                        }
                        if (redPacketResult.get("return_code").equals("SUCCESS") && redPacketResult.get("result_code").equals("SUCCESS")) {
                            switch (redPacketResult.get("status")) {
                                case "SENT":

                                    break;
                                case "RECEIVED":
                                    redPacketDetailMapper.updateRedPacketDetailReceiveById(packetDetail.getId(), "2");
                                    RedPacketDetail redPacketDetail = new RedPacketDetail();
                                    redPacketDetail.setId(packetDetail.getId());
                                    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                    try {
                                        redPacketDetail.setReceiveDate(df.parse(redPacketResult.get("rcv_time")));
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                    redPacketDetailMapper.update(redPacketDetail);
                                    break;
                                case "REFUND":
                                    redPacketDetailMapper.updateRedPacketDetailReceiveById(packetDetail.getId(), "3");
                                    break;
                            }
                        }
                    }
                    try {
                        EntityUtils.consume(entity);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } finally {
                    try {
                        if (httpclient != null)
                            httpclient.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public DwzPageModel page(RedPacketDetail redPacketDetail, DwzPageModel dwzPageModel) {
        Integer tempTotalCount = redPacketDetailMapper.countTotal(redPacketDetail);
        dwzPageModel.setTotalCount(tempTotalCount);
        List<RedPacketDetail> redPacketDetailList = redPacketDetailMapper.page((dwzPageModel.getCurrentPage() - 1) * dwzPageModel.getNumPerPage(),
                dwzPageModel.getNumPerPage(), redPacketDetail);
        dwzPageModel.setDataList(redPacketDetailList);
        dwzPageModel.setEntity(redPacketDetail);
        BigDecimal totalCount = new BigDecimal(dwzPageModel.getTotalCount());
        BigDecimal numPerPage = new BigDecimal(dwzPageModel.getNumPerPage());
        dwzPageModel.setTotalPageNum(totalCount.divide(numPerPage, 0, BigDecimal.ROUND_UP).longValue());
        return dwzPageModel;
    }

    public HSSFWorkbook export(RedPacketDetail redPacketDetail) {
        List<RedPacketDetail> redPacketDetailList = redPacketDetailMapper.page(null, null, redPacketDetail);
        HSSFWorkbook workbook = new HSSFWorkbook();                        // 创建工作簿对象
        HSSFSheet sheet = workbook.createSheet("红包明细");
        // 定义所需列数
        String[] titleNameArr = new String[]{"序号", "活动名称", "用户openid", "手机号", "公交卡号", "所属区县", "交易订单号", "发放时间", "红包金额", "领取状态", "领取时间"};
        int columnNum = titleNameArr.length;
        HSSFRow columnTitleRow = sheet.createRow(0);
        for (int i = 0; i < columnNum; i++) {
            HSSFCell cellRowName = columnTitleRow.createCell(i);                //创建列头对应个数的单元格
            cellRowName.setCellType(CellType.STRING);                //设置列头单元格的数据类型
            HSSFRichTextString text = new HSSFRichTextString(titleNameArr[i]);
            cellRowName.setCellValue(text);                                    //设置列头单元格的值
        }
        if (redPacketDetailList != null) {
            for (int i = 0; i < redPacketDetailList.size(); i++) {
                HSSFRow dataColumnRow = sheet.createRow(i + 1);
                for (int a = 0; a < columnNum; a++) {
                    HSSFCell dataCell = dataColumnRow.createCell(a);                //创建列头对应个数的单元格
                    dataCell.setCellType(CellType.STRING);                //设置列头单元格的数据类型
                    HSSFRichTextString text;
                    switch (a) {
                        case 0:
                            text = new HSSFRichTextString(String.valueOf(i + 1));
                            dataCell.setCellValue(text);
                            break;
                        case 1:
                            text = new HSSFRichTextString(String.valueOf(redPacketDetailList.get(i).getActivityName()));
                            dataCell.setCellValue(text);
                            break;
                        case 2:
                            if (redPacketDetailList.get(i).getOpenid() != null) {
                                text = new HSSFRichTextString(String.valueOf(redPacketDetailList.get(i).getOpenid()));
                                dataCell.setCellValue(text);
                            } else {
                                dataCell.setCellValue("");
                            }
                            break;

                        case 3:
                            if (redPacketDetailList.get(i).getPhone() != null) {
                                text = new HSSFRichTextString(String.valueOf(redPacketDetailList.get(i).getPhone()));
                                dataCell.setCellValue(text);
                            } else {
                                dataCell.setCellValue("");
                            }
                            break;
                        case 4:
                            text = new HSSFRichTextString(String.valueOf(redPacketDetailList.get(i).getBusCardNo()));
                            dataCell.setCellValue(text);
                            break;
                        case 5:
                            text = new HSSFRichTextString(String.valueOf(redPacketDetailList.get(i).getAreaName()));
                            dataCell.setCellValue(text);
                            break;
                        case 6:
                            text = new HSSFRichTextString(String.valueOf(redPacketDetailList.get(i).getMchBillno()));
                            dataCell.setCellValue(text);
                            break;
                        case 7:
                            text = new HSSFRichTextString(DateFormatUtils.format(redPacketDetailList.get(i).getSendDate(), "yyyy-MM-dd HH:mm:ss"));
                            dataCell.setCellValue(text);
                            break;
                        case 8:
                            DecimalFormat dFormat = new DecimalFormat("#.00");
                            String tempAmount = dFormat.format(redPacketDetailList.get(i).getAmount());
                            text = new HSSFRichTextString(tempAmount);
                            dataCell.setCellValue(text);
                            break;
                        case 9:
                            switch (redPacketDetailList.get(i).getReceive()) {
                                case "1": {
                                    text = new HSSFRichTextString("未领取");
                                    dataCell.setCellValue(text);
                                    break;
                                }
                                case "2": {
                                    text = new HSSFRichTextString("已领取");
                                    dataCell.setCellValue(text);
                                    break;
                                }
                                case "3": {
                                    text = new HSSFRichTextString("退回");
                                    dataCell.setCellValue(text);
                                    break;
                                }
                                default: {
                                    text = new HSSFRichTextString("发放失败");
                                    dataCell.setCellValue(text);
                                    break;
                                }
                            }
                            break;
                        case 10:
                            if (redPacketDetailList.get(i).getReceiveDate() != null) {
                                text = new HSSFRichTextString(DateFormatUtils.format(redPacketDetailList.get(i).getReceiveDate(), "yyyy-MM-dd HH:mm:ss"));
                                dataCell.setCellValue(text);
                            } else {
                                dataCell.setCellValue("");
                            }
                            break;
                        default:
                            break;
                    }


                }
            }
        }
        return workbook;
    }
}