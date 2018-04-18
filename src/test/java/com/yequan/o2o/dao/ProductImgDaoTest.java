package com.yequan.o2o.dao;

import com.yequan.o2o.BaseTest;
import com.yequan.o2o.entity.ProductImg;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProductImgDaoTest extends BaseTest {

    @Autowired
    private ProductImgDao productImgDao;

    @Test
    @Ignore
    public void testBatchInsertProductImg() {
        List<ProductImg> productImgList = new ArrayList<ProductImg>();
        ProductImg productImg1 = new ProductImg();
        productImg1.setCreateTime(new Date());
        productImg1.setImgAddr("/////");
        productImg1.setImgDesc("miaos");
        productImg1.setPriority(1);
        productImg1.setProductId(15L);

        ProductImg productImg2 = new ProductImg();
        productImg2.setCreateTime(new Date());
        productImg2.setImgAddr("/////ffff");
        productImg2.setImgDesc("miaosddd");
        productImg2.setPriority(2);
        productImg2.setProductId(15L);

        productImgList.add(productImg1);
        productImgList.add(productImg2);
        int effectNum = productImgDao.batchInsertProductImg(productImgList);
        System.out.println(effectNum);
    }

    @Test
    @Ignore
    public void testQueryProductImgList() {
        List<ProductImg> productImgList = productImgDao.queryProductImgList(45L);
        System.out.println(productImgList.size());
    }

    @Test
    public void testDeleteProductImgByProductId(){
        int effectNum = productImgDao.deleteProductImgByProductId(58L);
        System.out.println(effectNum);
    }

}
