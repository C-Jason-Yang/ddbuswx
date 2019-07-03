package com.evcas.ddbuswx.common.rmsys;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;

/**
 * Created by noxn on 2018/1/10.
 */
public class BusSystemTool {

    /**
     * GZIP解压
     * @param str
     * @return
     * @throws IOException
     */
    public static String uncompress(byte[] data) throws IOException {
        if (data == null || data.length == 0) {
            return "";
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        GZIPInputStream gunzip = new GZIPInputStream(in);
        byte[] buffer = new byte[256];
        int n;
        while ((n = gunzip.read(buffer)) >= 0) {
            out.write(buffer, 0, n);
        }
        return out.toString("utf-8");
    }
}
