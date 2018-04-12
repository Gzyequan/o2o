package com.yequan.o2o.service;

import com.yequan.o2o.BaseTest;
import com.yequan.o2o.dto.ProductCategoryExecution;
import com.yequan.o2o.entity.ProductCategory;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProductCategoryServiceTest extends BaseTest {
    @Autowired
    private ProductCategoryService productCategoryService;

    @Test
    @Ignore
    public void testGetProductCategoryList() {
        List<ProductCategory> productCategoryList = productCategoryService.getProductCategoryList(28L);
        System.out.println(productCategoryList.size());
        System.out.println(productCategoryList.get(0).getProductCategoryName());
    }

    @Test
    @Ignore
    public void testBatchAddProductCategory() {
        List<ProductCategory> productCategoryList = new ArrayList<ProductCategory>();
        ProductCategory productCategory1 = new ProductCategory();
        productCategory1.setProductCategoryName("饮料");
        productCategory1.setPriority(5);
        productCategory1.setCreateTime(new Date());
        productCategory1.setShopId(77L);

        ProductCategory productCategory2 = new ProductCategory();
        productCategory2.setProductCategoryName("酒水");
        productCategory2.setPriority(5);
        productCategory2.setCreateTime(new Date());
        productCategory2.setShopId(77L);

        ProductCategory productCategory3 = new ProductCategory();
        productCategory3.setProductCategoryName("主食");
        productCategory3.setPriority(4);
        productCategory3.setCreateTime(new Date());
        productCategory3.setShopId(77L);

        productCategoryList.add(productCategory1);
        productCategoryList.add(productCategory2);
        productCategoryList.add(productCategory3);

        ProductCategoryExecution pe = productCategoryService.batchAddProductCategory(productCategoryList);
        System.out.println(pe.getState() + " : " + pe.getStateInfo());
    }

    @Test
    public void testDeleteProductCategory() {
        ProductCategoryExecution pe = productCategoryService.deleteProductCategory(9L, 28L);
        System.out.println(pe.getStateInfo());
    }

}
