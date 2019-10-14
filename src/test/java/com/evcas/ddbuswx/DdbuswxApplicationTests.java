package com.evcas.ddbuswx;


import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.google.common.base.Strings;
import lombok.Data;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DdbuswxApplicationTests {

    @Data
    class SiteQueryVo {
        private String lineCode;
        private String siteCode;//站点编号
        private String siteName;//站点名称
        private String lat;//站点经度
        private String log;//站点纬度
        private String radius;//半径
        private String distance;//到下一站的距离
        private String direction;//上下行标志
        private int siteNum;//站点编号(顺序)
        private String remark;//备注
    }

    @Test
    public void t() {
        //region jsonStr
        String jsonStr = "{\n" +
                "    \"siteQueryListVo\": [\n" +
                "        {\n" +
                "            \"direction\": \"1\",\n" +
                "            \"distance\": \"\",\n" +
                "            \"lat\": \"30.296508\",\n" +
                "            \"lineCode\": \"12001\",\n" +
                "            \"log\": \"118.524838\",\n" +
                "            \"radius\": \"\",\n" +
                "            \"remark\": \"\",\n" +
                "            \"siteCode\": \"\",\n" +
                "            \"siteName\": \"高铁站\",\n" +
                "            \"siteNum\": 1\n" +
                "        },\n" +
                "        {\n" +
                "            \"direction\": \"1\",\n" +
                "            \"distance\": \"\",\n" +
                "            \"lat\": \"30.296508\",\n" +
                "            \"lineCode\": \"12001\",\n" +
                "            \"log\": \"118.524838\",\n" +
                "            \"radius\": \"\",\n" +
                "            \"remark\": \"\",\n" +
                "            \"siteCode\": \"\",\n" +
                "            \"siteName\": \"高铁站\",\n" +
                "            \"siteNum\": 1\n" +
                "        },\n" +
                "        {\n" +
                "            \"direction\": \"1\",\n" +
                "            \"distance\": \"\",\n" +
                "            \"lat\": \"30.298713\",\n" +
                "            \"lineCode\": \"12001\",\n" +
                "            \"log\": \"118.530202\",\n" +
                "            \"radius\": \"\",\n" +
                "            \"remark\": \"\",\n" +
                "            \"siteCode\": \"\",\n" +
                "            \"siteName\": \"灵芝公园\",\n" +
                "            \"siteNum\": 2\n" +
                "        },\n" +
                "        {\n" +
                "            \"direction\": \"1\",\n" +
                "            \"distance\": \"\",\n" +
                "            \"lat\": \"30.298713\",\n" +
                "            \"lineCode\": \"12001\",\n" +
                "            \"log\": \"118.530202\",\n" +
                "            \"radius\": \"\",\n" +
                "            \"remark\": \"\",\n" +
                "            \"siteCode\": \"\",\n" +
                "            \"siteName\": \"灵芝公园\",\n" +
                "            \"siteNum\": 2\n" +
                "        },\n" +
                "        {\n" +
                "            \"direction\": \"1\",\n" +
                "            \"distance\": \"\",\n" +
                "            \"lat\": \"30.297197\",\n" +
                "            \"lineCode\": \"12001\",\n" +
                "            \"log\": \"118.53391\",\n" +
                "            \"radius\": \"\",\n" +
                "            \"remark\": \"\",\n" +
                "            \"siteCode\": \"\",\n" +
                "            \"siteName\": \"交运局\",\n" +
                "            \"siteNum\": 3\n" +
                "        },\n" +
                "        {\n" +
                "            \"direction\": \"1\",\n" +
                "            \"distance\": \"\",\n" +
                "            \"lat\": \"30.297197\",\n" +
                "            \"lineCode\": \"12001\",\n" +
                "            \"log\": \"118.53391\",\n" +
                "            \"radius\": \"\",\n" +
                "            \"remark\": \"\",\n" +
                "            \"siteCode\": \"\",\n" +
                "            \"siteName\": \"交运局\",\n" +
                "            \"siteNum\": 3\n" +
                "        },\n" +
                "        {\n" +
                "            \"direction\": \"1\",\n" +
                "            \"distance\": \"\",\n" +
                "            \"lat\": \"30.293288\",\n" +
                "            \"lineCode\": \"12001\",\n" +
                "            \"log\": \"118.534395\",\n" +
                "            \"radius\": \"\",\n" +
                "            \"remark\": \"\",\n" +
                "            \"siteCode\": \"\",\n" +
                "            \"siteName\": \"农机公司\",\n" +
                "            \"siteNum\": 4\n" +
                "        },\n" +
                "        {\n" +
                "            \"direction\": \"1\",\n" +
                "            \"distance\": \"\",\n" +
                "            \"lat\": \"30.293288\",\n" +
                "            \"lineCode\": \"12001\",\n" +
                "            \"log\": \"118.534395\",\n" +
                "            \"radius\": \"\",\n" +
                "            \"remark\": \"\",\n" +
                "            \"siteCode\": \"\",\n" +
                "            \"siteName\": \"农机公司\",\n" +
                "            \"siteNum\": 4\n" +
                "        },\n" +
                "        {\n" +
                "            \"direction\": \"1\",\n" +
                "            \"distance\": \"\",\n" +
                "            \"lat\": \"30.289203\",\n" +
                "            \"lineCode\": \"12001\",\n" +
                "            \"log\": \"118.534147\",\n" +
                "            \"radius\": \"\",\n" +
                "            \"remark\": \"\",\n" +
                "            \"siteCode\": \"\",\n" +
                "            \"siteName\": \"公路分局\",\n" +
                "            \"siteNum\": 5\n" +
                "        },\n" +
                "        {\n" +
                "            \"direction\": \"1\",\n" +
                "            \"distance\": \"\",\n" +
                "            \"lat\": \"30.289203\",\n" +
                "            \"lineCode\": \"12001\",\n" +
                "            \"log\": \"118.534147\",\n" +
                "            \"radius\": \"\",\n" +
                "            \"remark\": \"\",\n" +
                "            \"siteCode\": \"\",\n" +
                "            \"siteName\": \"公路分局\",\n" +
                "            \"siteNum\": 5\n" +
                "        },\n" +
                "        {\n" +
                "            \"direction\": \"1\",\n" +
                "            \"distance\": \"\",\n" +
                "            \"lat\": \"30.286487\",\n" +
                "            \"lineCode\": \"12001\",\n" +
                "            \"log\": \"118.53532\",\n" +
                "            \"radius\": \"\",\n" +
                "            \"remark\": \"\",\n" +
                "            \"siteCode\": \"\",\n" +
                "            \"siteName\": \"旌一楼宾馆\",\n" +
                "            \"siteNum\": 6\n" +
                "        },\n" +
                "        {\n" +
                "            \"direction\": \"1\",\n" +
                "            \"distance\": \"\",\n" +
                "            \"lat\": \"30.286487\",\n" +
                "            \"lineCode\": \"12001\",\n" +
                "            \"log\": \"118.53532\",\n" +
                "            \"radius\": \"\",\n" +
                "            \"remark\": \"\",\n" +
                "            \"siteCode\": \"\",\n" +
                "            \"siteName\": \"旌一楼宾馆\",\n" +
                "            \"siteNum\": 6\n" +
                "        },\n" +
                "        {\n" +
                "            \"direction\": \"1\",\n" +
                "            \"distance\": \"\",\n" +
                "            \"lat\": \"30.286645\",\n" +
                "            \"lineCode\": \"12001\",\n" +
                "            \"log\": \"118.537998\",\n" +
                "            \"radius\": \"\",\n" +
                "            \"remark\": \"\",\n" +
                "            \"siteCode\": \"\",\n" +
                "            \"siteName\": \"旌德一中\",\n" +
                "            \"siteNum\": 7\n" +
                "        },\n" +
                "        {\n" +
                "            \"direction\": \"1\",\n" +
                "            \"distance\": \"\",\n" +
                "            \"lat\": \"30.286645\",\n" +
                "            \"lineCode\": \"12001\",\n" +
                "            \"log\": \"118.537998\",\n" +
                "            \"radius\": \"\",\n" +
                "            \"remark\": \"\",\n" +
                "            \"siteCode\": \"\",\n" +
                "            \"siteName\": \"旌德一中\",\n" +
                "            \"siteNum\": 7\n" +
                "        },\n" +
                "        {\n" +
                "            \"direction\": \"1\",\n" +
                "            \"distance\": \"\",\n" +
                "            \"lat\": \"30.292073\",\n" +
                "            \"lineCode\": \"12001\",\n" +
                "            \"log\": \"118.540572\",\n" +
                "            \"radius\": \"\",\n" +
                "            \"remark\": \"\",\n" +
                "            \"siteCode\": \"\",\n" +
                "            \"siteName\": \"城管执法局\",\n" +
                "            \"siteNum\": 8\n" +
                "        },\n" +
                "        {\n" +
                "            \"direction\": \"1\",\n" +
                "            \"distance\": \"\",\n" +
                "            \"lat\": \"30.292073\",\n" +
                "            \"lineCode\": \"12001\",\n" +
                "            \"log\": \"118.540572\",\n" +
                "            \"radius\": \"\",\n" +
                "            \"remark\": \"\",\n" +
                "            \"siteCode\": \"\",\n" +
                "            \"siteName\": \"城管执法局\",\n" +
                "            \"siteNum\": 8\n" +
                "        },\n" +
                "        {\n" +
                "            \"direction\": \"1\",\n" +
                "            \"distance\": \"\",\n" +
                "            \"lat\": \"30.292702\",\n" +
                "            \"lineCode\": \"12001\",\n" +
                "            \"log\": \"118.542905\",\n" +
                "            \"radius\": \"\",\n" +
                "            \"remark\": \"\",\n" +
                "            \"siteCode\": \"\",\n" +
                "            \"siteName\": \"老党校\",\n" +
                "            \"siteNum\": 9\n" +
                "        },\n" +
                "        {\n" +
                "            \"direction\": \"1\",\n" +
                "            \"distance\": \"\",\n" +
                "            \"lat\": \"30.292702\",\n" +
                "            \"lineCode\": \"12001\",\n" +
                "            \"log\": \"118.542905\",\n" +
                "            \"radius\": \"\",\n" +
                "            \"remark\": \"\",\n" +
                "            \"siteCode\": \"\",\n" +
                "            \"siteName\": \"老党校\",\n" +
                "            \"siteNum\": 9\n" +
                "        },\n" +
                "        {\n" +
                "            \"direction\": \"1\",\n" +
                "            \"distance\": \"\",\n" +
                "            \"lat\": \"30.292562\",\n" +
                "            \"lineCode\": \"12001\",\n" +
                "            \"log\": \"118.54867\",\n" +
                "            \"radius\": \"\",\n" +
                "            \"remark\": \"\",\n" +
                "            \"siteCode\": \"\",\n" +
                "            \"siteName\": \"时代新城\",\n" +
                "            \"siteNum\": 10\n" +
                "        },\n" +
                "        {\n" +
                "            \"direction\": \"1\",\n" +
                "            \"distance\": \"\",\n" +
                "            \"lat\": \"30.292562\",\n" +
                "            \"lineCode\": \"12001\",\n" +
                "            \"log\": \"118.54867\",\n" +
                "            \"radius\": \"\",\n" +
                "            \"remark\": \"\",\n" +
                "            \"siteCode\": \"\",\n" +
                "            \"siteName\": \"时代新城\",\n" +
                "            \"siteNum\": 10\n" +
                "        },\n" +
                "        {\n" +
                "            \"direction\": \"1\",\n" +
                "            \"distance\": \"\",\n" +
                "            \"lat\": \"30.29661\",\n" +
                "            \"lineCode\": \"12001\",\n" +
                "            \"log\": \"118.55136\",\n" +
                "            \"radius\": \"\",\n" +
                "            \"remark\": \"\",\n" +
                "            \"siteCode\": \"\",\n" +
                "            \"siteName\": \"东方雅苑南门\",\n" +
                "            \"siteNum\": 11\n" +
                "        },\n" +
                "        {\n" +
                "            \"direction\": \"1\",\n" +
                "            \"distance\": \"\",\n" +
                "            \"lat\": \"30.29661\",\n" +
                "            \"lineCode\": \"12001\",\n" +
                "            \"log\": \"118.55136\",\n" +
                "            \"radius\": \"\",\n" +
                "            \"remark\": \"\",\n" +
                "            \"siteCode\": \"\",\n" +
                "            \"siteName\": \"东方雅苑南门\",\n" +
                "            \"siteNum\": 11\n" +
                "        },\n" +
                "        {\n" +
                "            \"direction\": \"1\",\n" +
                "            \"distance\": \"\",\n" +
                "            \"lat\": \"30.298537\",\n" +
                "            \"lineCode\": \"12001\",\n" +
                "            \"log\": \"118.552488\",\n" +
                "            \"radius\": \"\",\n" +
                "            \"remark\": \"\",\n" +
                "            \"siteCode\": \"\",\n" +
                "            \"siteName\": \"东方雅苑东门\",\n" +
                "            \"siteNum\": 12\n" +
                "        },\n" +
                "        {\n" +
                "            \"direction\": \"1\",\n" +
                "            \"distance\": \"\",\n" +
                "            \"lat\": \"30.298537\",\n" +
                "            \"lineCode\": \"12001\",\n" +
                "            \"log\": \"118.552488\",\n" +
                "            \"radius\": \"\",\n" +
                "            \"remark\": \"\",\n" +
                "            \"siteCode\": \"\",\n" +
                "            \"siteName\": \"东方雅苑东门\",\n" +
                "            \"siteNum\": 12\n" +
                "        },\n" +
                "        {\n" +
                "            \"direction\": \"1\",\n" +
                "            \"distance\": \"\",\n" +
                "            \"lat\": \"30.299672\",\n" +
                "            \"lineCode\": \"12001\",\n" +
                "            \"log\": \"118.554422\",\n" +
                "            \"radius\": \"\",\n" +
                "            \"remark\": \"\",\n" +
                "            \"siteCode\": \"\",\n" +
                "            \"siteName\": \"梓阳学校\",\n" +
                "            \"siteNum\": 13\n" +
                "        },\n" +
                "        {\n" +
                "            \"direction\": \"1\",\n" +
                "            \"distance\": \"\",\n" +
                "            \"lat\": \"30.299672\",\n" +
                "            \"lineCode\": \"12001\",\n" +
                "            \"log\": \"118.554422\",\n" +
                "            \"radius\": \"\",\n" +
                "            \"remark\": \"\",\n" +
                "            \"siteCode\": \"\",\n" +
                "            \"siteName\": \"梓阳学校\",\n" +
                "            \"siteNum\": 13\n" +
                "        },\n" +
                "        {\n" +
                "            \"direction\": \"1\",\n" +
                "            \"distance\": \"\",\n" +
                "            \"lat\": \"30.300858\",\n" +
                "            \"lineCode\": \"12001\",\n" +
                "            \"log\": \"118.55231\",\n" +
                "            \"radius\": \"\",\n" +
                "            \"remark\": \"\",\n" +
                "            \"siteCode\": \"\",\n" +
                "            \"siteName\": \"东方幼儿园\",\n" +
                "            \"siteNum\": 14\n" +
                "        },\n" +
                "        {\n" +
                "            \"direction\": \"1\",\n" +
                "            \"distance\": \"\",\n" +
                "            \"lat\": \"30.300858\",\n" +
                "            \"lineCode\": \"12001\",\n" +
                "            \"log\": \"118.55231\",\n" +
                "            \"radius\": \"\",\n" +
                "            \"remark\": \"\",\n" +
                "            \"siteCode\": \"\",\n" +
                "            \"siteName\": \"东方幼儿园\",\n" +
                "            \"siteNum\": 14\n" +
                "        },\n" +
                "        {\n" +
                "            \"direction\": \"1\",\n" +
                "            \"distance\": \"\",\n" +
                "            \"lat\": \"30.302643\",\n" +
                "            \"lineCode\": \"12001\",\n" +
                "            \"log\": \"118.550362\",\n" +
                "            \"radius\": \"\",\n" +
                "            \"remark\": \"\",\n" +
                "            \"siteCode\": \"\",\n" +
                "            \"siteName\": \"和顺家园南门\",\n" +
                "            \"siteNum\": 15\n" +
                "        },\n" +
                "        {\n" +
                "            \"direction\": \"1\",\n" +
                "            \"distance\": \"\",\n" +
                "            \"lat\": \"30.302643\",\n" +
                "            \"lineCode\": \"12001\",\n" +
                "            \"log\": \"118.550362\",\n" +
                "            \"radius\": \"\",\n" +
                "            \"remark\": \"\",\n" +
                "            \"siteCode\": \"\",\n" +
                "            \"siteName\": \"和顺家园南门\",\n" +
                "            \"siteNum\": 15\n" +
                "        },\n" +
                "        {\n" +
                "            \"direction\": \"1\",\n" +
                "            \"distance\": \"\",\n" +
                "            \"lat\": \"30.305908\",\n" +
                "            \"lineCode\": \"12001\",\n" +
                "            \"log\": \"118.548572\",\n" +
                "            \"radius\": \"\",\n" +
                "            \"remark\": \"\",\n" +
                "            \"siteCode\": \"\",\n" +
                "            \"siteName\": \"县医院东门\",\n" +
                "            \"siteNum\": 16\n" +
                "        },\n" +
                "        {\n" +
                "            \"direction\": \"1\",\n" +
                "            \"distance\": \"\",\n" +
                "            \"lat\": \"30.305908\",\n" +
                "            \"lineCode\": \"12001\",\n" +
                "            \"log\": \"118.548572\",\n" +
                "            \"radius\": \"\",\n" +
                "            \"remark\": \"\",\n" +
                "            \"siteCode\": \"\",\n" +
                "            \"siteName\": \"县医院东门\",\n" +
                "            \"siteNum\": 16\n" +
                "        },\n" +
                "        {\n" +
                "            \"direction\": \"1\",\n" +
                "            \"distance\": \"\",\n" +
                "            \"lat\": \"30.316978\",\n" +
                "            \"lineCode\": \"12001\",\n" +
                "            \"log\": \"118.556445\",\n" +
                "            \"radius\": \"\",\n" +
                "            \"remark\": \"\",\n" +
                "            \"siteCode\": \"\",\n" +
                "            \"siteName\": \"篁嘉桥村\",\n" +
                "            \"siteNum\": 17\n" +
                "        },\n" +
                "        {\n" +
                "            \"direction\": \"1\",\n" +
                "            \"distance\": \"\",\n" +
                "            \"lat\": \"30.316978\",\n" +
                "            \"lineCode\": \"12001\",\n" +
                "            \"log\": \"118.556445\",\n" +
                "            \"radius\": \"\",\n" +
                "            \"remark\": \"\",\n" +
                "            \"siteCode\": \"\",\n" +
                "            \"siteName\": \"篁嘉桥村\",\n" +
                "            \"siteNum\": 17\n" +
                "        },\n" +
                "        {\n" +
                "            \"direction\": \"1\",\n" +
                "            \"distance\": \"\",\n" +
                "            \"lat\": \"30.318282\",\n" +
                "            \"lineCode\": \"12001\",\n" +
                "            \"log\": \"118.563293\",\n" +
                "            \"radius\": \"\",\n" +
                "            \"remark\": \"\",\n" +
                "            \"siteCode\": \"\",\n" +
                "            \"siteName\": \"康尔曼酒店\",\n" +
                "            \"siteNum\": 18\n" +
                "        },\n" +
                "        {\n" +
                "            \"direction\": \"1\",\n" +
                "            \"distance\": \"\",\n" +
                "            \"lat\": \"30.318282\",\n" +
                "            \"lineCode\": \"12001\",\n" +
                "            \"log\": \"118.563293\",\n" +
                "            \"radius\": \"\",\n" +
                "            \"remark\": \"\",\n" +
                "            \"siteCode\": \"\",\n" +
                "            \"siteName\": \"康尔曼酒店\",\n" +
                "            \"siteNum\": 18\n" +
                "        },\n" +
                "        {\n" +
                "            \"direction\": \"1\",\n" +
                "            \"distance\": \"\",\n" +
                "            \"lat\": \"30.318008\",\n" +
                "            \"lineCode\": \"12001\",\n" +
                "            \"log\": \"118.56642\",\n" +
                "            \"radius\": \"\",\n" +
                "            \"remark\": \"\",\n" +
                "            \"siteCode\": \"\",\n" +
                "            \"siteName\": \"经开区管委会\",\n" +
                "            \"siteNum\": 19\n" +
                "        },\n" +
                "        {\n" +
                "            \"direction\": \"1\",\n" +
                "            \"distance\": \"\",\n" +
                "            \"lat\": \"30.318008\",\n" +
                "            \"lineCode\": \"12001\",\n" +
                "            \"log\": \"118.56642\",\n" +
                "            \"radius\": \"\",\n" +
                "            \"remark\": \"\",\n" +
                "            \"siteCode\": \"\",\n" +
                "            \"siteName\": \"经开区管委会\",\n" +
                "            \"siteNum\": 19\n" +
                "        },\n" +
                "        {\n" +
                "            \"direction\": \"1\",\n" +
                "            \"distance\": \"\",\n" +
                "            \"lat\": \"30.326123\",\n" +
                "            \"lineCode\": \"12001\",\n" +
                "            \"log\": \"118.569178\",\n" +
                "            \"radius\": \"\",\n" +
                "            \"remark\": \"\",\n" +
                "            \"siteCode\": \"\",\n" +
                "            \"siteName\": \"明成驾校\",\n" +
                "            \"siteNum\": 20\n" +
                "        },\n" +
                "        {\n" +
                "            \"direction\": \"1\",\n" +
                "            \"distance\": \"\",\n" +
                "            \"lat\": \"30.326123\",\n" +
                "            \"lineCode\": \"12001\",\n" +
                "            \"log\": \"118.569178\",\n" +
                "            \"radius\": \"\",\n" +
                "            \"remark\": \"\",\n" +
                "            \"siteCode\": \"\",\n" +
                "            \"siteName\": \"明成驾校\",\n" +
                "            \"siteNum\": 20\n" +
                "        }\n" +
                "    ]\n" +
                "}";
        //endregion
        JSONObject jsonObject = JSONUtil.parseObj(jsonStr);
        JSONArray siteQueryListVo = jsonObject.getJSONArray("siteQueryListVo");
        List<SiteQueryVo> siteQueryVos = siteQueryListVo.toList(SiteQueryVo.class);
        List<SiteQueryVo> siteQueryVos1 = removeDuplicateWithOrder(siteQueryVos);
        System.out.println();
    }


    /**
     * 删除ArrayList中重复元素，保持顺序
     *
     * @param list
     */
    public List<SiteQueryVo> removeDuplicateWithOrder(List<SiteQueryVo> list) {
        Set set = new HashSet();
        List<SiteQueryVo> newList = new ArrayList();
        for (SiteQueryVo element : list) {
            if (set.add(element)) {
                newList.add(element);
            }
        }
        list.clear();
        list.addAll(newList);
        return list;
    }


       @Test
    public void tcp() throws Exception {
        Socket socket = new Socket();
        SocketAddress socketAddress = new InetSocketAddress("120.55.62.88", 8001);
//        SocketAddress socketAddress = new InetSocketAddress("192.168.100.220", 8001);
        socket.connect(socketAddress, 30000);
        socket.setSoTimeout(30000);
        while (true) {
            InputStream in = socket.getInputStream();
            String respLen = "500";
            String response = "";
            byte[] len = new byte[Integer.parseInt(respLen)];
            in.read(len, 0, Integer.parseInt(respLen));
            response = new String(len, "gbk");
            response = response.trim();
            if (!Strings.isNullOrEmpty(response)) {
                System.out.println(response);
            }
        }


    }

    @Test
    public void tcp2() throws Exception {
        Socket socket = new Socket();
//        SocketAddress socketAddress = new InetSocketAddress("120.55.62.88", 8001);
        SocketAddress socketAddress = new InetSocketAddress("192.168.100.220", 8001);
        socket.connect(socketAddress, 30000);
        socket.setSoTimeout(30000);
        while (true) {
            InputStream in = socket.getInputStream();
            String respLen = "500";
            String response = "";
            byte[] len = new byte[Integer.parseInt(respLen)];
            in.read(len, 0, Integer.parseInt(respLen));
            response = new String(len, "gbk");
            response = response.trim();
            if (!Strings.isNullOrEmpty(response)) {
                System.out.println(response);
            }
        }


    }


}
