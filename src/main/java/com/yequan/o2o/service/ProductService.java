package com.yequan.o2o.service;

import com.yequan.o2o.dto.ImageHolder;
import com.yequan.o2o.dto.ProductExecution;
import com.yequan.o2o.entity.Product;
import com.yequan.o2o.exceptions.ProductOperationException;

import java.util.List;

public interface ProductService {

    /**
     * 添加商品信息
     *
     * @param product
     * @param thumbnail
     * @param imageHolderList
     * @return
     * @throws ProductOperationException
     */
    ProductExecution addProduct(Product product, ImageHolder thumbnail, List<ImageHolder> imageHolderList)
            throws ProductOperationException;

    /**
     * 分页查询商品列表，可输入的查询条件：商品名，店铺id，商品类别，商品状态
     * @param productCondition
     * @param pageIndex
     * @param pageSize
     * @return
     */
    ProductExecution getProductList(Product productCondition,int pageIndex,int pageSize);

}
