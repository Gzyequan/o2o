package com.yequan.o2o.service;

import com.yequan.o2o.dto.ProductCategoryExecution;
import com.yequan.o2o.entity.ProductCategory;
import com.yequan.o2o.exceptions.ProductCategoryOperationException;

import java.util.List;

public interface ProductCategoryService {

    /**
     * 根据店铺id获取商品类别信息
     *
     * @param shopId
     * @return
     */
    List<ProductCategory> getProductCategoryList(Long shopId);

    /**
     * 批量添加商品分类
     *
     * @param productCategoryList
     * @return
     * @throws ProductCategoryOperationException
     */
    ProductCategoryExecution batchAddProductCategory(List<ProductCategory> productCategoryList)
            throws ProductCategoryOperationException;

    /**
     * 根据商品分类id和店铺id删除商品分类
     *
     * @param productCategoryId
     * @param shopId
     * @return
     */
    ProductCategoryExecution deleteProductCategory(Long productCategoryId, Long shopId)
            throws ProductCategoryOperationException;

}
