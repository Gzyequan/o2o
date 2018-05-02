package com.yequan.o2o.service;

import com.yequan.o2o.dto.WechatAuthExecution;
import com.yequan.o2o.entity.WechatAuth;
import com.yequan.o2o.exceptions.WechatAuthOperationException;

public interface WechatAuthService {

    WechatAuth getWechatAuthByOpenId(String openId);

    WechatAuthExecution register(WechatAuth wechatAuth) throws WechatAuthOperationException;

}
