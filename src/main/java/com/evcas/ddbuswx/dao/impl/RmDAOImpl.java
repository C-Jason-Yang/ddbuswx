package com.evcas.ddbuswx.dao.impl;

import com.evcas.ddbuswx.common.SystemParameter;
import com.evcas.ddbuswx.common.rmsys.BusSystemTool;
import com.evcas.ddbuswx.common.rmsys.ServiceService;
import com.evcas.ddbuswx.common.utils.Base64Util;
import com.evcas.ddbuswx.common.utils.JsonTools;
import com.evcas.ddbuswx.common.utils.StringUtil;
import com.evcas.ddbuswx.dao.IRmDAO;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by noxn on 2018/1/10.
 */
@Repository
public class RmDAOImpl implements IRmDAO {

    @Override
    public String getBusLineAndStation(String regKey) {
        try {
            Map<String, Object> requestLineSiteInfoParamMap = new HashMap<String, Object>();
            requestLineSiteInfoParamMap.put("PAGEINDEX", 1);
            String lineSiteResultStr = ServiceService.client.getBaseData(0xB301, 123, regKey, Base64Util.encode(JsonTools.gson.toJson(requestLineSiteInfoParamMap)));
            return BusSystemTool.uncompress(Base64Util.decoder(lineSiteResultStr));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String rmLogin(String loginName, String password) {
        try {
            Map<String, Object> loginParamMap = new HashMap<String, Object>();
            loginParamMap.put("FORCELOGIN", 1);
            loginParamMap.put("USERNAME", loginName);
            loginParamMap.put("PASSWORD", password);
            loginParamMap.put("CLIENTTYPE", 0x02);
            loginParamMap.put("LOGINIP", "");
            loginParamMap.put("LANG", "zh-CN");
            loginParamMap.put("VERSION", "V2.0");
            String loginResultStr = ServiceService.client.getBaseData(0xB001, 123, "", Base64Util.encode(JsonTools.gson.toJson(loginParamMap)));
            loginResultStr = BusSystemTool.uncompress(Base64Util.decoder(loginResultStr));
            Map<String, Object> loginResultMap = JsonTools.gson.fromJson(loginResultStr, Map.class);
            Map<String, Object> loginResultParamMap = JsonTools.gson.fromJson(loginResultMap.get("PARAM").toString(), Map.class);
//            SystemParameter.BUS_SYSTEM_REG_KEY_MAP.put(loginName, loginResultParamMap.get("REGKEY").toString());
            System.out.println(loginResultParamMap.get("REGKEY").toString());
            if (!StringUtil.isEmpty(String.valueOf(loginResultParamMap.get("REGKEY")))) {
                return String.valueOf(loginResultParamMap.get("REGKEY"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public List<Map<String, Object>> getBusInfoList(String regKey) {
        try {
            Map<String, Object> queryBusInfoParamMap = new HashMap<String, Object>();
            queryBusInfoParamMap.put("PAGESIZE", 500);
            queryBusInfoParamMap.put("PAGEINDEX", 1);
            String busListResultStr = ServiceService.client.getBaseData(0xB003, 123, regKey, Base64Util.encode(JsonTools.gson.toJson(queryBusInfoParamMap)));
            busListResultStr = BusSystemTool.uncompress(Base64Util.decoder(busListResultStr));
            Map<String, Object> busListResultMap = JsonTools.gson.fromJson(busListResultStr, Map.class);
            String busListParamStr = busListResultMap.get("PARAM").toString();
            Map<String, Object> busListParamMap = JsonTools.gson.fromJson(busListParamStr, Map.class);
            List<Map<String, Object>> busList = (List) busListParamMap.get("CARINFOS");
            if (busList != null && busList.size() > 0) {
                Integer carinfocount = Math.round(Float.valueOf(String.valueOf(busListParamMap.get("CARINFOCOUNT"))));
                if (busList.size() < carinfocount) {
                    queryBusInfoParamMap.put("PAGESIZE", carinfocount);
                    busListResultStr = ServiceService.client.getBaseData(0xB003, 123, regKey, Base64Util.encode(JsonTools.gson.toJson(queryBusInfoParamMap)));
                    busListResultStr = BusSystemTool.uncompress(Base64Util.decoder(busListResultStr));
                    busListResultMap = JsonTools.gson.fromJson(busListResultStr, Map.class);
                    busListParamStr = busListResultMap.get("PARAM").toString();
                    busListParamMap = JsonTools.gson.fromJson(busListParamStr, Map.class);
                    return  (List) busListParamMap.get("CARINFOS");
                }
                return (List) busListParamMap.get("CARINFOS");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getBusShiftList(String regKey, Integer lineCode) {
        try {
            Map<String, Object> reqBusShiftInfoParamMap = new HashMap<String, Object>();
            reqBusShiftInfoParamMap.put("LINENO", lineCode);
            String busShiftInfoResultStr = ServiceService.client.getBusinessData(0xA503, 123, regKey, Base64Util.encode(JsonTools.gson.toJson(reqBusShiftInfoParamMap)));
            return BusSystemTool.uncompress(Base64Util.decoder(busShiftInfoResultStr));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
