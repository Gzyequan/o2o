package com.yequan.o2o.dao;

import com.yequan.o2o.BaseTest;
import com.yequan.o2o.entity.Product;
import com.yequan.o2o.entity.ProductCategory;
import com.yequan.o2o.entity.Shop;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public class ProductDaoTest extends BaseTest {

    @Autowired
    private ProductDao productDao;

    @Test
    @Ignore
    public void testUpdateProductCategoryToNull() {
        int effectNum = productDao.updateProductCategoryToNull(2L);
        System.out.println(effectNum);
    }

    @Test
    public void testInsertProduct() {
        Product product = new Product();
        product.setCreateTime(new Date());
        product.setLastEditTime(new Date());
        product.setProductName("撒尿牛丸");
        product.setProductDesc("美味滋滋的撒尿牛丸");
        product.setPriority(15);
        product.setNormalPrice("5元/个");
        product.setPromotionPrice("4元/个");
        product.setEnableStatus(1);

        ProductCategory productCategory = new ProductCategory();
        productCategory.setProductCategoryId(13L);
        product.setProductCategory(productCategory);

        Shop shop = new Shop();
        shop.setShopId(77L);
        product.setShop(shop);

        int effectNum = productDao.insertProduct(product);
        System.out.println(effectNum);
    }

}
