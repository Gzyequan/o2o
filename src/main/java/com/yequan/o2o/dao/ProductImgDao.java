package com.yequan.o2o.dao;

import com.yequan.o2o.entity.ProductImg;

import java.util.List;

public interface ProductImgDao {

    /**
     * 批量添加商品图片
     *
     * @param productImgList
     * @return
     */
    int batchInsertProductImg(List<ProductImg> productImgList);

    /**
     * 根据商品Id查询商品详情图
     *
     * @param productId
     * @return
     */
    List<ProductImg> queryProductImgList(Long productId);

    /**
     * 根据商品id删除商品详情图片
     *
     * @param productId
     * @return
     */
    int deleteProductImgByProductId(Long productId);


}
