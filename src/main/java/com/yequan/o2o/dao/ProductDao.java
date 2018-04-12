package com.yequan.o2o.dao;

import com.yequan.o2o.entity.Product;

public interface ProductDao {

    /**
     * 新增一个商品
     * @param product
     * @return
     */
    int insertProduct(Product product);

    /**
     * 删除商品分类时清楚与之对应的商品分类
     *
     * @param ProductCategoryId
     * @return
     */
    int updateProductCategoryToNull(Long ProductCategoryId);

}
