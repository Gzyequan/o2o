package com.yequan.o2o.dao;

import com.yequan.o2o.BaseTest;
import com.yequan.o2o.entity.ProductCategory;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProductCategoryDaoTest extends BaseTest {

    @Autowired
    private ProductCategoryDao productCategoryDao;

    @Test
    @Ignore
    public void testQueryProductCategoryList() {
        List<ProductCategory> productCategoryList = productCategoryDao.queryProductCategoryList(29L);
        System.out.println(productCategoryList.size());
        System.out.println(productCategoryList.get(0).getProductCategoryName());
    }

    @Test
    @Ignore
    public void testBatchInsertProductCategory() {
        List<ProductCategory> productCategoryList = new ArrayList<ProductCategory>();
        ProductCategory productCategory1 = new ProductCategory();
        productCategory1.setProductCategoryName("肉类");
        productCategory1.setPriority(7);
        productCategory1.setCreateTime(new Date());
        productCategory1.setShopId(77L);

        ProductCategory productCategory2 = new ProductCategory();
        productCategory2.setProductCategoryName("蔬菜");
        productCategory2.setPriority(6);
        productCategory2.setCreateTime(new Date());
        productCategory2.setShopId(77L);

        ProductCategory productCategory3 = new ProductCategory();
        productCategory3.setProductCategoryName("水果");
        productCategory3.setPriority(8);
        productCategory3.setCreateTime(new Date());
        productCategory3.setShopId(77L);

        productCategoryList.add(productCategory1);
        productCategoryList.add(productCategory2);
        productCategoryList.add(productCategory3);

        int effectNum = productCategoryDao.batchInsertProductCategory(productCategoryList);
        System.out.println(effectNum);
    }

    @Test
    @Ignore
    public void testDeleteProductCategory() {
        int effectNum = productCategoryDao.deleteProductCategory(7L, 28L);
        System.out.println(effectNum);
    }

}
