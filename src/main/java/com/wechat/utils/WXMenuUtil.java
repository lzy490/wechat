package com.wechat.utils;


import net.sf.json.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by luzhiyuan on 16/7/31.
 */
public class WXMenuUtil {

    private static Logger logger = LogManager.getLogger(WXMenuUtil.class);

    public static String getToken() {
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + Constants.APPID + "&secret=" + Constants.SECRET;
        BufferedReader reader = null;
        String token = "";
        try {
            URL realUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuffer buffer = new StringBuffer();
            String str = null;
            while ((str = reader.readLine()) != null) {
                buffer.append(str);
            }
            JSONObject jsonObject = JSONObject.fromObject(buffer.toString());
            token = jsonObject.getString("access_token");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return token;
    }

    public static boolean createMenu(String token) {
        String url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=" + token;
        String menuData = "{" +
                "    \"button\": [\n" +
                "        {\n" +
                "            \"type\": \"view\", \n" +
                "            \"name\": \"主页\", \n" +
                "            \"url\": \"http://120.27.123.7:8081/wechat/home.do\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";
        try {
            URL realUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("charset", "");
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            out.write(menuData);
            out.flush();
            out.close();
            int responseCode = conn.getResponseCode();
            if (HttpURLConnection.HTTP_OK == responseCode) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuffer buffer = new StringBuffer();
                String str = "";
                if ((str = reader.readLine()) != null) {
                    buffer.append(str);
                }
                JSONObject jsonObject = JSONObject.fromObject(buffer.toString());
                String errmsg = jsonObject.getString("errmsg");
                logger.info("errorMsg: " + errmsg);
                if (errmsg.equals("ok")) {
                    return true;
                }
            }else {
                logger.info("");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
        //JSONObject jsonObject = JSONObject.fromObject(menuData);
    }
}
