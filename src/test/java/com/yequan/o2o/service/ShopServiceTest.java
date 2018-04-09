package com.yequan.o2o.service;

import com.yequan.o2o.BaseTest;
import com.yequan.o2o.dto.ShopExecution;
import com.yequan.o2o.entity.Area;
import com.yequan.o2o.entity.PersonInfo;
import com.yequan.o2o.entity.Shop;
import com.yequan.o2o.entity.ShopCategory;
import com.yequan.o2o.enums.ShopStateEnum;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class ShopServiceTest extends BaseTest {

    @Autowired
    private ShopService shopService;

    @Test
    @Ignore
    public void testAddShop() throws FileNotFoundException {
        Shop shop = new Shop();
        PersonInfo owner = new PersonInfo();
        Area area = new Area();
        ShopCategory shopCategory = new ShopCategory();
        owner.setUserId(1L);
        area.setAreaId(2);
        shopCategory.setShopCategoryId(10L);
        shop.setOwner(owner);
        shop.setArea(area);
        shop.setShopCategory(shopCategory);
        shop.setShopName("测试店铺名3");
        shop.setEnableStatus(ShopStateEnum.CHECK.getState());
        shop.setAddress("test3");
        shop.setAdvice("审核中");
        shop.setCreateTime(new Date());
        shop.setLastEditTime(new Date());
        shop.setPhone("test3");
        shop.setPriority(1);
        shop.setShopDesc("test3");

        File shopImg = new File("F:/servicefile/upload/mogu.jpg");
        InputStream inputStream = new FileInputStream(shopImg);
        ShopExecution shopExecution = shopService.addShop(shop, inputStream, shopImg.getName());
        assertEquals(ShopStateEnum.CHECK.getState(), shopExecution.getState());
    }

}
