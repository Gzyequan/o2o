package com.yequan.o2o.service.impl;

import com.yequan.o2o.dao.ShopDao;
import com.yequan.o2o.dto.ShopExecution;
import com.yequan.o2o.entity.Shop;
import com.yequan.o2o.enums.ShopStateEnum;
import com.yequan.o2o.exceptions.ShopOperationException;
import com.yequan.o2o.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.Date;

public class ShopServiceImpl implements ShopService {

    @Autowired
    private ShopDao shopDao;

    @Transactional
    public ShopExecution addShop(Shop shop, File shopImg) {
        if (null == shop) {
            return new ShopExecution(ShopStateEnum.NULL_SHOP);
        }
        try {
            shop.setEnableStatus(0);
            shop.setCreateTime(new Date());
            shop.setLastEditTime(new Date());
            int insertShop = shopDao.insertShop(shop);
            if (insertShop <= 0) {
                throw new ShopOperationException("店铺创建失败");
            } else {

            }
        } catch (Exception e) {
            throw new ShopOperationException(e.getMessage());
        }
        return null;
    }
}
