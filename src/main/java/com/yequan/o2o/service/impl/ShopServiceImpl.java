package com.yequan.o2o.service.impl;

import com.yequan.o2o.dao.ShopDao;
import com.yequan.o2o.dto.ShopExecution;
import com.yequan.o2o.entity.Shop;
import com.yequan.o2o.enums.ShopStateEnum;
import com.yequan.o2o.exceptions.ShopOperationException;
import com.yequan.o2o.service.ShopService;
import com.yequan.o2o.util.ImageUtil;
import com.yequan.o2o.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.InputStream;
import java.util.Date;

@Service
public class ShopServiceImpl implements ShopService {

    @Autowired
    private ShopDao shopDao;

    @Transactional
    public ShopExecution addShop(Shop shop, InputStream shopImgInputStream, String fileName) {
        if (null == shop) {
            return new ShopExecution(ShopStateEnum.NULL_SHOP);
        }
        try {
            shop.setEnableStatus(0);
            shop.setCreateTime(new Date());
            shop.setLastEditTime(new Date());
            int effectNum = shopDao.insertShop(shop);
            if (effectNum <= 0) {
                throw new ShopOperationException("店铺创建失败");
            } else {
                if (null != shopImgInputStream) {
                    try {
                        addShopImg(shop, shopImgInputStream,fileName);
                    } catch (Exception e) {
                        throw new ShopOperationException("add shop image error:" + e.getMessage());
                    }
                    effectNum = shopDao.updateShop(shop);
                    if (effectNum <= 0) {
                        throw new ShopOperationException("图片地址更新失败！");
                    }
                }
            }
        } catch (Exception e) {
            throw new ShopOperationException(e.getMessage());
        }
        return new ShopExecution(ShopStateEnum.CHECK, shop);
    }

    private void addShopImg(Shop shop, InputStream shopImgInputStream, String fileName) {
        String dest = PathUtil.getShopImagePath(shop.getShopId());
        String imgAddr = ImageUtil.generateThumbnail(shopImgInputStream, fileName, dest);
        shop.setShopImg(imgAddr);
    }
}
