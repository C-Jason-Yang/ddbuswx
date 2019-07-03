package com.evcas.ddbuswx.common;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by noxn on 2018/1/10.
 */
public class SystemParameter {

    /******************************  ddbus manager system account start  *****************************************/

//    //锐明系统账号
//    public static Map<String, String> BUS_SYSTEM_REG_KEY_MAP = new HashMap<String, String>();
//    //颍上
//    public static String YS_BUS_ACCOUNT = "ysbus";
//    public static String YS_BUS_PASSWORD = "123456";
//    //太和
//    public static String TH_BUS_ACCOUNT = "thbus";
//    public static String TH_BUS_PASSWORD = "123456";
//    //屯昌
//    public static String TC_BUS_ACCOUNT = "tcbusadmin";
//    public static String TC_BUS_PASSWORD = "123456";
//    //徽州
//    public static String HZ_BUS_ACCOUNT = "huizhouapp";
//    public static String HZ_BUS_PASSWORD = "123456";
//    //河北辛集
//    public static String XJ_BUS_ACCOUNT = "xjbusapp";
//    public static String XJ_BUS_PASSWORD = "123456";
//    //海南文昌
//    public static String WC_BUS_ACCOUNT = "wczsgj";
//    public static String WC_BUS_PASSWORD= "123456";

    //恒宇系统账号
    public static String YS_HY_BUS_ACCOUNT = "100001";
    public static String YS_HY_BUS_PASSWORD = "pbrraarraarrbt";

    /******************************  ddbus manager system account end  *****************************************/


    public static String WE_CHAT_SENDREDPACK = "https://api.mch.weixin.qq.com/mmpaymkttransfers/sendredpack";

    public static String WC_MERCHANTS_API_SECRET_KEY = "ced9041ed72a4e6ea4c77feda8248e4b";

    public static String WE_CHAT_GETHBINFO = "https://api.mch.weixin.qq.com/mmpaymkttransfers/gethbinfo";

    public static String WE_CHAT_API_CLIENT_IP = "120.55.62.88";

    public static String WE_CHAT_DADAO_APPID = "wxc609948134e10033";

//        public static String UPLOAD_FILE_PATH = "D:\\work\\";
    public static String UPLOAD_FILE_PATH = File.separator + "home" + File.separator + "ddbusstatic" + File.separator;

//    public static String PKCS12_FILE_PATH = File.separator + "D:\\work\\apiclient_cert.p12";
    public static String PKCS12_FILE_PATH = File.separator + "home" + File.separator + "ddbusstatic" + File.separator + "apiclient_cert.p12";

    public static List<String> RED_PACKET_ID_LIST = new ArrayList<String>();
}
