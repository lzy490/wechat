package com.wechat.utils;

import net.sf.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by luzhiyuan on 16/7/31.
 */
public class HttpUtil {

    public static String doGet(String url, String params) {
        BufferedReader reader = null;
        StringBuffer buffer = new StringBuffer();
        String str = null;
        try {
            URL realUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            while ((str = reader.readLine()) != null) {
                buffer.append(str);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }

    public static String doPost(String url, String data) {
        StringBuffer buffer = new StringBuffer();
        String str = "";
        try {
            URL realUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("charset", "");
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            out.write(data);
            out.flush();
            out.close();
            int responseCode = conn.getResponseCode();
            if (HttpURLConnection.HTTP_OK == responseCode) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                if ((str = reader.readLine()) != null) {
                    buffer.append(str);
                }
            }else {
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }
}
