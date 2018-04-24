package com.yequan.o2o.dao;

import com.yequan.o2o.BaseTest;
import com.yequan.o2o.entity.ShopCategory;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class ShopCategoryDaoTest extends BaseTest {
    @Autowired
    private ShopCategoryDao shopCategoryDao;

    @Test
    @Ignore
    public void testQueryShopCategory() {
        ShopCategory rootShopCategory = new ShopCategory();
        rootShopCategory.setShopCategoryId(10L);
        ShopCategory parent = new ShopCategory();
        parent.setShopCategoryId(10L);
        rootShopCategory.setParent(parent);
        List<ShopCategory> rootShopCategoryList = shopCategoryDao.queryShopCategory(rootShopCategory);
        System.out.println(rootShopCategoryList.size());
        for (ShopCategory category : rootShopCategoryList) {
            System.out.println(category.getShopCategoryName());
        }
    }
}
