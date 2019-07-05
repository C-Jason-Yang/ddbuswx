package com.evcas.ddbuswx.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则工具
 * Created by noxn on 2018/9/30.
 */
public class RegularUtil {

    public static boolean validate(String regx, CharSequence validateValue) {
        Pattern pattern = Pattern.compile(regx);
        Matcher matcher = pattern.matcher(validateValue);
        return matcher.matches();
    }
}
