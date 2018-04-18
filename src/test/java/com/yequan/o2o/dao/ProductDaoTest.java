package com.yequan.o2o.dao;

import com.yequan.o2o.BaseTest;
import com.yequan.o2o.entity.Product;
import com.yequan.o2o.entity.ProductCategory;
import com.yequan.o2o.entity.Shop;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

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
    @Ignore
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

    @Test
    @Ignore
    public void testQueryProductCount() {
        Product product = new Product();
        Shop shop = new Shop();
        shop.setShopId(77L);
        product.setShop(shop);
        int productCount = productDao.queryProductCount(product);
        System.out.println(productCount);
    }

    @Test
    @Ignore
    public void testQueryProductList() {
        Product product = new Product();
        Shop shop = new Shop();
        shop.setShopId(77L);
        product.setShop(shop);
        List<Product> products = productDao.queryProductList(product, 0, 10);
        System.out.println(products.size());
        for (Product product1 : products) {
            System.out.println(product1.getProductName());
        }
    }

    @Test
    @Ignore
    public void testQueryByProductId() {
        Product product = productDao.queryByProductId(1L);
        System.out.println(product.getProductImgList().size());
    }

    @Test
    public void testUpdateProduct() {
        Product product = new Product();
        product.setProductId(15L);
        Shop shop = new Shop();
        shop.setShopId(77L);
        product.setShop(shop);
        product.setCreateTime(new Date());
        product.setLastEditTime(new Date());
        product.setProductName("撒尿牛丸");
        product.setProductDesc("美味滋滋的撒尿牛丸");
        product.setPriority(20);
        product.setNormalPrice("5元/个");
        product.setPromotionPrice("5元/个");
        product.setEnableStatus(1);

        ProductCategory productCategory = new ProductCategory();
        productCategory.setProductCategoryId(13L);
        int effectNum = productDao.updateProduct(product);
        System.out.println(effectNum);
    }

}
