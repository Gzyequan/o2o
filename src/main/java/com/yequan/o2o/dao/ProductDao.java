package com.yequan.o2o.dao;

import com.yequan.o2o.entity.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductDao {

    /**
     * 分页查询商品列表，可输入的查询条件：商品名，店铺id，商品类别，商品状态
     *
     * @param productCondition
     * @param rowIndex
     * @param pageSize
     * @return
     */
    List<Product> queryProductList(@Param("productCondition") Product productCondition, @Param("rowIndex") int rowIndex,
                                   @Param("pageSize") int pageSize);

    /**
     * 新增一个商品
     *
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


    /**
     * 返回某查询条件下的商品总数
     *
     * @param productCondition
     * @return
     */
    int queryProductCount(@Param("productCondition") Product productCondition);

    /**
     * 根据商品id查询某商品
     *
     * @param productId
     * @return
     */
    Product queryByProductId(Long productId);

    /**
     * 更新商品信息
     *
     * @param product
     * @return
     */
    int updateProduct(Product product);

}
