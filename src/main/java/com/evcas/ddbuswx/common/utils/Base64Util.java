package com.evcas.ddbuswx.common.utils;

import sun.misc.BASE64Decoder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Created by noxn on 2018/9/13.
 */
public class Base64Util {

    public static String encode(String str) {
        byte[] bytes = str.getBytes(StandardCharsets.UTF_8);
        return new sun.misc.BASE64Encoder().encode(bytes);
    }

    public static byte[] decoder(String str) {
        try {
            BASE64Decoder decoder = new BASE64Decoder();
            return decoder.decodeBuffer(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
