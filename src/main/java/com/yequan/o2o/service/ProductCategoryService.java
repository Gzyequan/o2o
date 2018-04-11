package com.yequan.o2o.service;

import com.yequan.o2o.entity.ProductCategory;

import java.util.List;

public interface ProductCategoryService {

    /**
     * 根据店铺id获取商品类别信息
     *
     * @param shopId
     * @return
     */
    List<ProductCategory> getProductCategoryList(Long shopId);
}
