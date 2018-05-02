package com.yequan.o2o.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserAccessToken {

    //获取到的凭证
    @JsonProperty("access_token")
    private String accessToken;
    //凭证的有效时间
    @JsonProperty("expires_in")
    private String expiresIn;
    //表示更新令牌，用来下一次的访问令牌，这里没有什么用
    @JsonProperty("refresh_token")
    private String refreshToken;
    //该用户在此微信公众号的唯一标识，对于此微信号具有唯一性
    @JsonProperty("openid")
    //表示权限范围，这里可以省略
    private String openId;
    @JsonProperty("scope")
    private String scope;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(String expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}
