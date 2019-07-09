package com.evcas.ddbuswx.common.utils;

import lombok.Cleanup;
import lombok.extern.log4j.Log4j2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

/**
 * Created by noxn on 2018/9/24.
 */
@Log4j2
public class HttpUtil {


    public static String post(String urlStr, String xmlInfo) {
        try {
            URL url = new URL(urlStr);
            URLConnection con = url.openConnection();
            con.setDoOutput(true);
            con.setRequestProperty("Cache-Control", "no-cache");
            con.setRequestProperty("Content-Type", "text/xml");

           @Cleanup OutputStreamWriter out = new OutputStreamWriter(con.getOutputStream(), StandardCharsets.UTF_8);
            out.write(new String(xmlInfo.getBytes(StandardCharsets.UTF_8)));
            out.flush();
            //out.close();
            @Cleanup BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8));
            String line;
            StringBuilder sb = new StringBuilder();
            for (line = br.readLine(); line != null; line = br.readLine()) {
                log.info(line);
                sb.append(line);
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
