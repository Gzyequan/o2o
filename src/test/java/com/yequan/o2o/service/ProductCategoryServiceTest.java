package com.yequan.o2o.service;

import com.yequan.o2o.BaseTest;
import com.yequan.o2o.entity.ProductCategory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ProductCategoryServiceTest extends BaseTest {
    @Autowired
    private ProductCategoryService productCategoryService;

    @Test
    public void testGetProductCategoryList(){
        List<ProductCategory> productCategoryList = productCategoryService.getProductCategoryList(28L);
        System.out.println(productCategoryList.size());
        System.out.println(productCategoryList.get(0).getProductCategoryName());
    }

}
