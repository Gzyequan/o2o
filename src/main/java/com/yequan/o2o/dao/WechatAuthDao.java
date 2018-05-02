package com.yequan.o2o.dao;

import com.yequan.o2o.entity.WechatAuth;

public interface WechatAuthDao {

    WechatAuth queryWechatAuthByOpenId(String openId);

    int insertWechatAuth(WechatAuth wechatAuth);

}
