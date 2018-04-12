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

}
