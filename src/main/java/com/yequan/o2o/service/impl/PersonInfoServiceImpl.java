package com.yequan.o2o.service.impl;

import com.yequan.o2o.dao.PersonInfoDao;
import com.yequan.o2o.entity.PersonInfo;
import com.yequan.o2o.service.PersonInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonInfoServiceImpl implements PersonInfoService {

    @Autowired
    private PersonInfoDao personInfoDao;

    public PersonInfo getPersonInfoById(Long userId) {
        return personInfoDao.queryPersonInfoById(userId);
    }
}
