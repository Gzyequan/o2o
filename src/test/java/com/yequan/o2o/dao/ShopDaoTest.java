package com.yequan.o2o.dao;

import com.yequan.o2o.BaseTest;
import com.yequan.o2o.entity.Area;
import com.yequan.o2o.entity.PersonInfo;
import com.yequan.o2o.entity.Shop;
import com.yequan.o2o.entity.ShopCategory;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ShopDaoTest extends BaseTest {
    @Autowired
    private ShopDao shopDao;

    @Test
    @Ignore
    public void testQueryByShopId() {
        Shop shop = shopDao.queryByShopId(77L);
        System.out.println(shop.getArea().getAreaName());
        System.out.println(shop.getShopName());
    }

    @Test
    @Ignore
    public void testQueryShopList() {
        Shop shop = new Shop();
        PersonInfo owner = new PersonInfo();
        owner.setUserId(1L);
        ShopCategory shopCategory = new ShopCategory();
        shopCategory.setShopCategoryId(20L);
        Area area = new Area();
        area.setAreaId(2);

        shop.setShopCategory(shopCategory);
        shop.setArea(area);
        shop.setOwner(owner);
        shop.setEnableStatus(1);
        shop.setShopName("奶茶");

        List<Shop> shops = shopDao.queryShopList(shop, 0, 5);
        System.out.println(shops.size());
        for (Shop shopItem : shops) {
            System.out.println(shopItem.getShopName());
        }
    }

    @Test
    @Ignore
    public void testQueryShopCount() {
        Shop shop = new Shop();
        shop.setShopName("奶茶");
        int shopCount = shopDao.queryShopCount(shop);
        System.out.println(shopCount);
    }

    @Test
    @Ignore
    public void testInsertShop() {
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
        shop.setShopName("测试店铺名");
        shop.setEnableStatus(1);
        shop.setAddress("test");
        shop.setAdvice("审核中");
        shop.setCreateTime(new Date());
        shop.setLastEditTime(new Date());
        shop.setPhone("test");
        shop.setPriority(1);
        shop.setShopDesc("test");
        shop.setShopImg("test");
        int insertShop = shopDao.insertShop(shop);
        assertEquals(1, insertShop);
    }

    @Test
    @Ignore
    public void testUpdateShop() {
        Shop shop = new Shop();
        shop.setShopId(37L);
        shop.setLastEditTime(new Date());
        shop.setAddress("更新测试地址");
        shopDao.updateShop(shop);
    }

}
