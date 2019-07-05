package com.evcas.ddbuswx.common.utils;

import lombok.Cleanup;
import sun.misc.BASE64Decoder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * Created by noxn on 2018/9/24.
 */
public class FileOperation {

    /**
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
                if (out != null) {
                    out.flush();
                    out.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 二进制数据转换文件存储
     *
     * @param filebyteArray
     * @param fileName
     */
    public static void saveBtyeArrayToFile(byte[] filebyteArray, String fileName) {
        try {
            File file;
            file = new File(fileName);
            if (!file.exists()) {
                boolean newFile = file.createNewFile();// 如果文件不存在，则创建
                if (!newFile) {
                    throw new FileNotFoundException("create new file error");
                }
            }
            @Cleanup FileOutputStream fos = new FileOutputStream(file);
            if (filebyteArray.length > 0) {
                fos.write(filebyteArray, 0, filebyteArray.length);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
