package com.yequan.o2o.dao;

import com.yequan.o2o.BaseTest;
import com.yequan.o2o.entity.PersonInfo;
import com.yequan.o2o.entity.WechatAuth;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public class PersonInfoDaoTest extends BaseTest {

    @Autowired
    private PersonInfoDao personInfoDao;

    @Test
    @Ignore
    public void testQueryPersonInfoById() {
        PersonInfo personInfo = personInfoDao.queryPersonInfoById(8L);
        System.out.println(personInfo.getName());
    }

    @Test
    public void testInsertPersonInfo() {
        PersonInfo personInfo = new PersonInfo();
        personInfo.setName("yequan");
        personInfo.setEmail("673541964@qq.com");
        personInfo.setEnableStatus(1);
        personInfo.setCreateTime(new Date());
        personInfo.setLastEditTime(new Date());
        personInfo.setUserType(3);
        int effectNum = personInfoDao.insertPersonInfo(personInfo);
        System.out.println(effectNum);
    }

}
