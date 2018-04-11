package com.yequan.o2o.dao;

import com.yequan.o2o.entity.ProductCategory;

import java.util.List;

public interface ProductCategoryDao {
    /**
     * 根据店铺id查询商品分类信息
     * @param shopId
     * @return
     */
    List<ProductCategory> queryProductCategoryList(Long shopId);
}
