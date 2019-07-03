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
        Iterator iterator = set.iterator();
        while (iterator.hasNext()) {
            String tempKey = (String) iterator.next();
            beforeSign.append(tempKey + "=" + signTreeMap.get(tempKey) + "&");
        }
        beforeSign.append("key=" + SystemParameter.WC_MERCHANTS_API_SECRET_KEY);
        return beforeSign.toString();
    }
}
