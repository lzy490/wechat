package com.wechat.controller;

import com.wechat.entity.UserInfo;
import com.wechat.utils.*;
import net.sf.json.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * Created by luzhiyuan on 16/7/29.
 */
@Controller
public class WeChatController {
    Logger logger = LogManager.getLogger(WeChatController.class);
    private String refresh_token;
    private String openid;
    private String access_token;
    private static String wxCode;
    @RequestMapping("authWeChat")
    public void authWeChat(HttpServletRequest request, HttpServletResponse response) {
        String signature = request.getParameter("signature");
        logger.info("signature from wx server: " + signature);
        String timestamp = request.getParameter("timestamp");
        logger.info("timestamp from wx server: " + timestamp);
        String nonce = request.getParameter("nonce");
        logger.info("nonce from wx server: " + nonce);
        String echostr = request.getParameter("echostr");
        logger.info("echostr from wx server: " + echostr);
        String result = "";
        PrintWriter out = null;
        String wxXml = "";
        String readStr = "";
        StringBuffer buffer = new StringBuffer();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
            while ((readStr = reader.readLine()) != null) {
                buffer.append(readStr);
            }
            wxXml = buffer.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            out = response.getWriter();
            if (!StringUtil.isEmpty(echostr)) {
                if (SignUtil.checkSignature(signature, timestamp, nonce)) {
                    result = echostr;

                }
            } else {

            }
            out.write(result);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            out.close();
        }
    }

    @RequestMapping("test")
    public void test() {
        logger.info("test");
    }

    @RequestMapping("/")
    public ModelAndView index() {

        ModelAndView model = new ModelAndView();
        model.setViewName("index");
        return model;
    }

    @RequestMapping("home")
    public ModelAndView home(@RequestParam String code) {
        logger.info("home");
        logger.info(code);
        wxCode = code;
        String getAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="
                + Constants.APPID + "&secret=" + Constants.SECRET + "&code=" + code + "&grant_type=authorization_code";
        logger.info("getAccessTokenUrl is : " + getAccessTokenUrl);
        String accessTokenResult = HttpUtil.doGet(getAccessTokenUrl, null);
        if (!StringUtil.isEmpty(accessTokenResult)) {
            JSONObject tokenJson = JSONObject.fromObject(accessTokenResult);
            //refresh_token = tokenJson.getString("refresh_token");
            access_token = tokenJson.getString("access_token");
            openid = tokenJson.getString("openid");
            logger.info("refresh_token: " + refresh_token);
            logger.info("access_token: " + access_token);
            logger.info("openid: " + openid);
        }
//        String refreshTokenUrl = "https://api.weixin.qq.com/sns/oauth2/refresh_token?+appid="
//                + Constants.APPID + "&grant_type=refresh_token&refresh_token=" + refresh_token;
//        logger.info("refreshTokenUrl is : " + refreshTokenUrl);
//        String refreshTokenResult = HttpUtil.doGet(refreshTokenUrl, null);
//        if (!StringUtil.isEmpty(refreshTokenResult)) {
//            JSONObject refreshTokenJson = JSONObject.fromObject(refreshTokenResult);
//            access_token = refreshTokenJson.getString("access_token");
//            openid = refreshTokenJson.getString("openid");
//        }
//        String getUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo?access_token="
//                + access_token + "&openid=" + openid + "&lang=zh_CN";
//        logger.info("getUserInfoUrl is : " + getUserInfoUrl);
//        String userInfoResult = HttpUtil.doGet(getUserInfoUrl, null);
//        if (!StringUtil.isEmpty(userInfoResult)) {
//            JSONObject userInfoJson = JSONObject.fromObject(userInfoResult);
//            logger.info("nickname: " + userInfoJson.getString("nickname"));
//            logger.info("headimgurl: " + userInfoJson.getString("headimgurl"));
//        }
        ModelAndView model = new ModelAndView();
        model.setViewName("index");
        return model;
    }

    @RequestMapping("getToken")
    public void getToken() {
        logger.info("getToken");
        String token = WXMenuUtil.getToken();
        logger.info(token);
    }

    @RequestMapping("createMenu")
    public void createMenu() {
        logger.info("create menu");
        String token = WXMenuUtil.getToken();
        WXMenuUtil.createMenu(token);
    }

    @RequestMapping("getNickNameAndHeadImgUrl")
    public @ResponseBody UserInfo getNickNameAndHeadImgUrl() {
        System.out.println("getNickNameAndHeadImgUrl");
        String getAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + Constants.APPID + "&secret=" + Constants.SECRET + "&code=" + wxCode + "&grant_type=authorization_code";
        logger.info("getAccessTokenUrl is : " + getAccessTokenUrl);
        String accessTokenResult = HttpUtil.doGet(getAccessTokenUrl, null);
        if (!StringUtil.isEmpty(accessTokenResult)) {
            JSONObject tokenJson = JSONObject.fromObject(accessTokenResult);
            access_token = tokenJson.getString("access_token");
            openid = tokenJson.getString("openid");
            logger.info("refresh_token: " + refresh_token);
            logger.info("access_token: " + access_token);
            logger.info("openid: " + openid);
        }
        String getUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo?access_token="
                + access_token + "&openid=" + openid + "&lang=zh_CN";
        logger.info("getUserInfoUrl is : " + getUserInfoUrl);
        String userInfoResult = HttpUtil.doGet(getUserInfoUrl, null);
        UserInfo userInfo = new UserInfo();
        if (!StringUtil.isEmpty(userInfoResult)) {
            JSONObject userInfoJson = JSONObject.fromObject(userInfoResult);
            userInfo.setNickName(userInfoJson.getString("nickname"));
            userInfo.setHeadImgUrl(userInfoJson.getString("headimgurl"));
            logger.info("nickname: " + userInfoJson.getString("nickname"));
            logger.info("headimgurl: " + userInfoJson.getString("headimgurl"));
        }
        return userInfo;
    }
}
