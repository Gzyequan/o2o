package com.yequan.o2o.service;

import com.yequan.o2o.BaseTest;
import com.yequan.o2o.dto.ImageHolder;
import com.yequan.o2o.dto.ProductExecution;
import com.yequan.o2o.entity.Product;
import com.yequan.o2o.entity.ProductCategory;
import com.yequan.o2o.entity.Shop;
import com.yequan.o2o.enums.ProductStateEnum;
import com.yequan.o2o.exceptions.ShopOperationException;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ProductServiceTest extends BaseTest {

    @Autowired
    private ProductService productService;

    @Test
    @Ignore
    public void testAddProduct() throws ShopOperationException, FileNotFoundException {
        // 创建shopId为1且productCategoryId为1的商品实例并给其成员变量赋值
        Product product = new Product();
        Shop shop = new Shop();
        shop.setShopId(77L);
        ProductCategory pc = new ProductCategory();
        pc.setProductCategoryId(13L);
        product.setShop(shop);
        product.setProductCategory(pc);
        product.setProductName("测试商品1");
        product.setProductDesc("测试商品1");
        product.setPriority(20);
        product.setCreateTime(new Date());
        product.setEnableStatus(ProductStateEnum.SUCCESS.getState());
        // 创建缩略图文件流
        File thumbnailFile = new File("F:/servicefile/upload/mogu.jpg");
        InputStream is = new FileInputStream(thumbnailFile);
        ImageHolder thumbnail = new ImageHolder(thumbnailFile.getName(), is);

        // 创建两个商品详情图文件流并将他们添加到详情图列表中
        File productImg1 = new File("F:/servicefile/upload/lufei.jpg");
        InputStream is1 = new FileInputStream(productImg1);
        File productImg2 = new File("F:/servicefile/upload/minren.jpg");
        InputStream is2 = new FileInputStream(productImg2);

        List<ImageHolder> productImgList = new ArrayList<ImageHolder>();
        productImgList.add(new ImageHolder(productImg1.getName(), is1));
        productImgList.add(new ImageHolder(productImg2.getName(), is2));
        // 添加商品并验证
        ProductExecution pe = productService.addProduct(product, thumbnail, productImgList);
        assertEquals(ProductStateEnum.SUCCESS.getState(), pe.getState());
    }

    @Test
    public void testGetProductList() {
        Product product = new Product();
        Shop shop = new Shop();
        shop.setShopId(77L);
        product.setShop(shop);
        ProductExecution pe = productService.getProductList(product, 1, 10);
        System.out.println(ProductStateEnum.stateOf(pe.getState()).getStateInfo());
        for (Product product1 : pe.getProductList()) {
            System.out.println(product1.getProductName());
        }
    }

}
