package com.yequan.o2o.util.wechat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yequan.o2o.dto.UserAccessToken;
import com.yequan.o2o.dto.WechatUser;
import com.yequan.o2o.entity.PersonInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.io.*;
import java.net.URL;
import java.security.SecureRandom;

public class WechatUtil {

    private static Logger logger = LoggerFactory.getLogger(WechatUtil.class);

    /**
     * 获取用户AccessToken
     *
     * @param code 微信公众号传输过来的code
     * @return
     */
    public static UserAccessToken getUserAccessToken(String code) {
        String appId = "wxf2a25864445d7fb4";
        logger.debug("appId is :" + appId);
        String appsecret = "26c5234529e9d23e4f167c0cc5f1a5f3";
        logger.debug("appsecret is :" + appsecret);
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + appId + "&secret=" + appsecret
                + "&code=" + code + "&grant_type=authorization_code";
        String tokenStr = httpsRequest(url, "GET", null);
        logger.debug("userAccessToken :" + tokenStr);
        ObjectMapper objectMapper = new ObjectMapper();
        UserAccessToken token = null;
        try {
            token = objectMapper.readValue(tokenStr, UserAccessToken.class);
        } catch (IOException e) {
            logger.debug("获取用户accessToken失败：" + e.getMessage());
        }
        if (null == token) {
            logger.debug("获取用户accessToken失败");
            return null;
        }
        return token;
    }

    public static WechatUser getUserInfo(String accessToken, String openId) {
        // 根据传入的accessToken以及openId拼接出访问微信定义的端口并获取用户信息的URL
        String url = "https://api.weixin.qq.com/sns/userinfo?access_token=" + accessToken + "&openid=" + openId
                + "&lang=zh_CN";
        String userStr = httpsRequest(url, "GET", null);
        logger.debug("userStr :" + userStr);
        WechatUser user = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            user = objectMapper.readValue(userStr, WechatUser.class);
        } catch (IOException e) {
            logger.debug("获取用户信息失败：" + e.getMessage());
        }
        if (null == user) {
            logger.debug("获取用户信息失败");
            return null;
        }
        return user;
    }

    /**
     * https请求，并返回结果
     *
     * @param requestUrl    请求的url
     * @param requestMethod 请求方式
     * @param outputStr     向服务端传递的数据
     * @return
     */
    public static String httpsRequest(String requestUrl, String requestMethod, String outputStr) {

        StringBuffer buffer = new StringBuffer();
        try {
            //生成SSL证书
            TrustManager[] trustManagers = {new MyX509TrustManager()};
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, trustManagers, new SecureRandom());
            SSLSocketFactory socketFactory = sslContext.getSocketFactory();

            //网络连接
            URL url = new URL(requestUrl);
            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
            httpsURLConnection.setSSLSocketFactory(socketFactory);
            httpsURLConnection.setDoOutput(true);
            httpsURLConnection.setDoInput(true);
            httpsURLConnection.setUseCaches(false);
            httpsURLConnection.setRequestMethod(requestMethod);
            if ("GET".equalsIgnoreCase(requestMethod)) {
                httpsURLConnection.connect();
            }
            //如果有数据向服务器端提交
            if (null != outputStr) {
                OutputStream outputStream = httpsURLConnection.getOutputStream();
                outputStream.write(outputStr.getBytes("utf-8"));
                outputStream.close();
            }
            //将返回的输入流转换成字符串
            InputStream inputStream = httpsURLConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            inputStream = null;
            httpsURLConnection.disconnect();
            logger.debug("https buffer : " + buffer.toString());
        } catch (Exception e) {
            logger.debug("https request error:{}", e);
        }
        return buffer.toString();
    }

    /**
     * 将WechatUser里的信息转换成PersonInfo的信息并返回PersonInfo实体类
     *
     * @param user
     * @return
     */
    public static PersonInfo getPersonInfoFromRequest(WechatUser user) {
        PersonInfo personInfo = new PersonInfo();
        personInfo.setName(user.getNickName());
        personInfo.setGender(user.getSex() + "");
        personInfo.setProfileImg(user.getHeadImgUrl());
        personInfo.setEnableStatus(1);
        return personInfo;
    }

}
