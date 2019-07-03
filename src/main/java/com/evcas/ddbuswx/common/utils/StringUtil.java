package com.evcas.ddbuswx.common.utils;

/**
 * Created by noxn on 2018/1/10.
 */
public class StringUtil {

    public static boolean isEmpty(String str) {
        if ((str == null) || (str.trim().isEmpty())) {
            return true;
        }
        return false;
    }

    public static String changeFirstCharToUpperCase(String str) {
        String firstChar = String.valueOf(Character.toUpperCase(str.charAt(0)));
        return firstChar + str.substring(1);
    }
}
