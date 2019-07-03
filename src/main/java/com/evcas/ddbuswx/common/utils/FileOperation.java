package com.evcas.ddbuswx.common.utils;

import sun.misc.BASE64Decoder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * Created by noxn on 2018/9/24.
 */
public class FileOperation {

    /**
     *
     * @param fileCode
     * @param fileName
     */
    public static void saveBase64ToFile(String fileCode, String fileName) {
        BASE64Decoder decoder = new BASE64Decoder();
        OutputStream out = null;
        try {
            byte[] b = decoder.decodeBuffer(fileCode);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }
            out = new FileOutputStream(fileName);
            out.write(b);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.flush();
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 二进制数据转换文件存储
     * @param filebyteArray
     * @param fileName
     */
    public static void saveBtyeArrayToFile(byte[] filebyteArray, String fileName) {
        try {
            File file =null ;
            FileOutputStream fos = null;
            file = new File(fileName);
            if (!file.exists()) {
                file.createNewFile(); // 如果文件不存在，则创建
            }
            fos = new FileOutputStream(file);
            if (filebyteArray.length > 0) {
                fos.write(filebyteArray, 0, filebyteArray.length);
            }
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
