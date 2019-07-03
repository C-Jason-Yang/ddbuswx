package com.evcas.ddbuswx.common.utils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by noxn on 2018/1/11.
 */
public class BaiDuMapUtil {


    private static String ak="Yw6v2WPTd0ZjtYOQR5dNW6vqoBNqXw6W";
    //返回结果格式
    private static String outputType="json";

    /**
     * 批量转换经纬度
     * @param coordList
     * @param from 源坐标类型 取值为如下：
    1：GPS设备获取的角度坐标，wgs84坐标;
    2：GPS获取的米制坐标、sogou地图所用坐标;
    3：google地图、soso地图、aliyun地图、mapabc地图和amap地图所用坐标，国测局坐标;
    4：3中列表地图坐标对应的米制坐标;
    5：百度地图采用的经纬度坐标;
    6：百度地图采用的米制坐标;
    7：mapbar地图坐标;
    8：51地图坐标
     * @param to 目的坐标类型
    有两种可供选择：5、6。
    5：bd09ll(百度经纬度坐标),
    6：bd09mc(百度米制经纬度坐标);
     * @return
     */
    public static Map<String, JSONArray> geoConv(List<String> coordList, String from, String to) throws Exception {
        if(coordList.size()>100){
            throw new Exception("一次转换最大可转换100组");
        }
        StringBuilder coords = new StringBuilder();
        for (int i = 0; i < coordList.size(); i++) {
            if (i == 0)
                coords.append(coordList.get(i));
            else
                coords.append(";" + coordList.get(i));
        }
        String url="http://api.map.baidu.com/geoconv/v1/?coords="+coords+"&output="+outputType+"&ak="+ak+"&from="+from+"&to="+to;
        Map<String,JSONArray> map=new HashMap<String, JSONArray>();
        String json = loadJSON(url);
        JSONObject obj = JSONObject.fromObject(json);
        if(obj.get("status").toString().equals("0")){
            JSONArray result= obj.getJSONArray("result");
            map.put("coords",result);
        }else{
        }
        return map;
    }

    public static String loadJSON (String url) {
        StringBuilder json = new StringBuilder();
        try {
            URL oracle = new URL(url);
            URLConnection yc = oracle.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    yc.getInputStream()));
            String inputLine = null;
            while ( (inputLine = in.readLine()) != null) {
                json.append(inputLine);
            }
            in.close();
        } catch (MalformedURLException e) {

        } catch (IOException e) {

        }
        return json.toString();
    }
}
