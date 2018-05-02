package com.yequan.o2o.dao;

import com.yequan.o2o.entity.PersonInfo;

public interface PersonInfoDao {

    PersonInfo queryPersonInfoById(Long userId);

    int insertPersonInfo(PersonInfo personInfo);

}
