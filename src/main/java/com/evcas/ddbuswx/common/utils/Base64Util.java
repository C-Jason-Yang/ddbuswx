package com.evcas.ddbuswx.common.utils;

import sun.misc.BASE64Decoder;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by noxn on 2018/9/13.
 */
public class Base64Util {

    public static String encode(String str) {
        try {
            byte[] bytes = str.getBytes("utf-8");
            return new sun.misc.BASE64Encoder().encode(bytes);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[]  decoder(String str) {
        try {
            BASE64Decoder decoder = new BASE64Decoder();
            return decoder.decodeBuffer(str);
//            byte[] b = str.getBytes("utf-8");
//            return new String(b,"utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
