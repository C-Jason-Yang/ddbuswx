package com.evcas.ddbuswx.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by noxn on 2018/3/28.
 */
public class DateTimeUtil {
    
    public static String getCurrentTime(boolean isNeedMs) {
        Date date = new Date();
        String dateStr = "";
        SimpleDateFormat sdf;
        if (isNeedMs) {
            sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        } else {
            sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
        try {
            dateStr = sdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateStr;
    }
}
