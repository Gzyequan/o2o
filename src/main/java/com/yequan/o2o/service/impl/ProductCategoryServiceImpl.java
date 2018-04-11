package com.yequan.o2o.service.impl;

import com.yequan.o2o.dao.ProductCategoryDao;
import com.yequan.o2o.entity.ProductCategory;
import com.yequan.o2o.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

    @Autowired
    private ProductCategoryDao productCategoryDao;

    /**
     * 根据店铺id获取商品分类信息
     *
     * @param shopId
     * @return
     */
    public List<ProductCategory> getProductCategoryList(Long shopId) {
        return productCategoryDao.queryProductCategoryList(shopId);
    }
}
