package com.yequan.o2o.dao;

import com.yequan.o2o.BaseTest;
import com.yequan.o2o.entity.PersonInfo;
import com.yequan.o2o.entity.WechatAuth;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public class WechatLoginDaoTest extends BaseTest {

    @Autowired
    private WechatAuthDao wechatAuthDao;

    @Test
    @Ignore
    public void testQueryWechatAuthByOpenId() {
        WechatAuth wechatAuth = wechatAuthDao.queryWechatAuthByOpenId("ovLbns-gxJHqC-UTPQKvgEuENl-E");
        System.out.println(wechatAuth.getPersonInfo().getName());
    }

    @Test
    public void testInsertWechatAuth() {
        WechatAuth wechatAuth = new WechatAuth();
        wechatAuth.setOpenId("test");
        PersonInfo personInfo = new PersonInfo();
        personInfo.setUserId(9L);
        wechatAuth.setPersonInfo(personInfo);
        wechatAuth.setCreateTime(new Date());
        int effectNum = wechatAuthDao.insertWechatAuth(wechatAuth);
        System.out.println(effectNum);
    }

}
