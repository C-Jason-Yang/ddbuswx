package com.evcas.ddbuswx.common.utils;

import com.evcas.ddbuswx.common.SystemParameter;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;

/**
 * Created by noxn on 2018/9/24.
 */
public class WeChatUtil {

    public static String getSignBeForeEncryption(TreeMap signTreeMap) {
        StringBuilder beforeSign = new StringBuilder();
        Set set = signTreeMap.keySet();
        for (Object o : set) {
            String tempKey = (String) o;
            beforeSign.append(tempKey).append("=").append(signTreeMap.get(tempKey)).append("&");
        }
        beforeSign.append("key=").append(SystemParameter.WC_MERCHANTS_API_SECRET_KEY);
        return beforeSign.toString();
    }
}
