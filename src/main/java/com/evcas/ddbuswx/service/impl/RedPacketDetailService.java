package com.evcas.ddbuswx.service.impl;

import com.evcas.ddbuswx.common.SystemParameter;
import com.evcas.ddbuswx.common.utils.Md5Util;
import com.evcas.ddbuswx.common.utils.UuidUtil;
import com.evcas.ddbuswx.common.utils.WeChatUtil;
import com.evcas.ddbuswx.common.utils.XmlUtil;
import com.evcas.ddbuswx.entity.RedPacketDetail;
import com.evcas.ddbuswx.entity.WcUser;
import com.evcas.ddbuswx.mapper.RedPacketDetailMapper;
import com.evcas.ddbuswx.mapper.WcUserMapper;
import com.evcas.ddbuswx.model.DwzPageModel;
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
@Service
public class RedPacketDetailService {

    @Autowired
    private WcUserMapper wcUserMapper;

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
            for (int i = 0; i < redPacketDetailList.size(); i++) {
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
                    HttpPost httpPost = new HttpPost(SystemParameter.WE_CHAT_GETHBINFO);
                    System.out.println("executing request" + httpPost.getRequestLine());
                    httpPost.addHeader("Connection", "keep-alive");
                    httpPost.addHeader("Accept", "*/*");
                    httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
                    httpPost.addHeader("Host", "api.mch.weixin.qq.com");
                    httpPost.addHeader("X-Requested-With", "XMLHttpRequest");
                    httpPost.addHeader("Cache-Control", "max-age=0");
                    httpPost.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0) ");

                    String nonceStr = UuidUtil.getUuid();
                    String mchBillno = redPacketDetailList.get(i).getMchBillno();
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
                    xmlSb.append("     <sign>" + sign + "</sign>");
                    xmlSb.append("     <mch_billno>" + mchBillno + "</mch_billno>");
                    xmlSb.append("     <mch_id>1508163181</mch_id>");
                    xmlSb.append("     <appid>wxc609948134e10033</appid>");
                    xmlSb.append("     <nonce_str>" + nonceStr + "</nonce_str>");
                    xmlSb.append("     <bill_type>MCHT</bill_type>");
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
                        if (entity != null) {
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
                            Map<String, String> redPacketResult = XmlUtil.xmlToMap(sendResultSb.toString());
                            //成功
                            if (redPacketResult.get("return_code").equals("SUCCESS") && redPacketResult.get("result_code").equals("SUCCESS")) {
                                if (redPacketResult.get("status").equals("SENT")) {

                                } else if (redPacketResult.get("status").equals("RECEIVED")) {
                                    redPacketDetailMapper.updateRedPacketDetailReceiveById(redPacketDetailList.get(i).getId(), "2");
                                    RedPacketDetail redPacketDetail = new RedPacketDetail();
                                    redPacketDetail.setId(redPacketDetailList.get(i).getId());
                                    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                    try {
                                        redPacketDetail.setReceiveDate(df.parse(redPacketResult.get("rcv_time")));
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                    redPacketDetailMapper.update(redPacketDetail);
                                } else if (redPacketResult.get("status").equals("REFUND")) {
                                    redPacketDetailMapper.updateRedPacketDetailReceiveById(redPacketDetailList.get(i).getId(), "3");
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
        String [] titleNameArr = new String [] {"序号", "活动名称", "用户openid", "手机号", "公交卡号", "所属区县", "交易订单号", "发放时间", "红包金额", "领取状态", "领取时间"};
        int columnNum = titleNameArr.length;
        HSSFRow columnTitleRow = sheet.createRow(0);
        for(int i = 0; i < columnNum; i++){
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
                    if (a == 0) {
                        HSSFRichTextString text = new HSSFRichTextString(String.valueOf(i + 1));
                        dataCell.setCellValue(text);
                    } else if (a == 1) {
                        HSSFRichTextString text = new HSSFRichTextString(String.valueOf(redPacketDetailList.get(i).getActivityName()));
                        dataCell.setCellValue(text);
                    } else if (a == 2) {
                        if (redPacketDetailList.get(i).getOpenid() != null) {
                            HSSFRichTextString text = new HSSFRichTextString(String.valueOf(redPacketDetailList.get(i).getOpenid()));
                            dataCell.setCellValue(text);
                        } else {
                            dataCell.setCellValue("");
                        }
                    } else if (a == 3) {
                        if (redPacketDetailList.get(i).getPhone() != null) {
                            HSSFRichTextString text = new HSSFRichTextString(String.valueOf(redPacketDetailList.get(i).getPhone()));
                            dataCell.setCellValue(text);
                        } else {
                            dataCell.setCellValue("");
                        }
                    } else if (a == 4) {
                        HSSFRichTextString text = new HSSFRichTextString(String.valueOf(redPacketDetailList.get(i).getBusCardNo()));
                        dataCell.setCellValue(text);
                    } else if (a == 5) {
                        HSSFRichTextString text = new HSSFRichTextString(String.valueOf(redPacketDetailList.get(i).getAreaName()));
                        dataCell.setCellValue(text);
                    } else if (a == 6) {
                        HSSFRichTextString text = new HSSFRichTextString(String.valueOf(redPacketDetailList.get(i).getMchBillno()));
                        dataCell.setCellValue(text);
                    } else if (a == 7) {
                        HSSFRichTextString text = new HSSFRichTextString(DateFormatUtils.format(redPacketDetailList.get(i).getSendDate(), "yyyy-MM-dd HH:mm:ss"));
                        dataCell.setCellValue(text);
                    } else if (a == 8) {
                        DecimalFormat dFormat = new DecimalFormat("#.00");
                        String tempAmount = dFormat.format(redPacketDetailList.get(i).getAmount());
                        HSSFRichTextString text = new HSSFRichTextString(tempAmount);
                        dataCell.setCellValue(text);
                    } else if (a == 9) {
                        if (redPacketDetailList.get(i).getReceive().equals("1")) {
                            HSSFRichTextString text = new HSSFRichTextString(String.valueOf("未领取"));
                            dataCell.setCellValue(text);
                        } else if (redPacketDetailList.get(i).getReceive().equals("2")){
                            HSSFRichTextString text = new HSSFRichTextString(String.valueOf("已领取"));
                            dataCell.setCellValue(text);
                        } else if (redPacketDetailList.get(i).getReceive().equals("3")){
                            HSSFRichTextString text = new HSSFRichTextString(String.valueOf("退回"));
                            dataCell.setCellValue(text);
                        } else {
                            HSSFRichTextString text = new HSSFRichTextString(String.valueOf("发放失败"));
                            dataCell.setCellValue(text);
                        }
                    } else if (a == 10) {
                        if (redPacketDetailList.get(i).getReceiveDate() != null) {
                            HSSFRichTextString text = new HSSFRichTextString(DateFormatUtils.format(redPacketDetailList.get(i).getReceiveDate(), "yyyy-MM-dd HH:mm:ss"));
                            dataCell.setCellValue(text);
                        } else {
                            dataCell.setCellValue("");
                        }
                    }
                }
            }
        }
        return workbook;
    }
}