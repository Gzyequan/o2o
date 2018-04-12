package com.yequan.o2o.service.impl;

import com.yequan.o2o.dao.ProductCategoryDao;
import com.yequan.o2o.dao.ProductDao;
import com.yequan.o2o.dto.ProductCategoryExecution;
import com.yequan.o2o.entity.ProductCategory;
import com.yequan.o2o.enums.ProductCategoryStateEnum;
import com.yequan.o2o.exceptions.ProductCategoryOperationException;
import com.yequan.o2o.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

    @Autowired
    private ProductCategoryDao productCategoryDao;

    @Autowired
    private ProductDao productDao;

    /**
     * 根据店铺id获取商品分类信息
     *
     * @param shopId
     * @return
     */
    public List<ProductCategory> getProductCategoryList(Long shopId) {
        return productCategoryDao.queryProductCategoryList(shopId);
    }

    /**
     * 批量添加商品分类
     *
     * @param productCategoryList
     * @return
     * @throws ProductCategoryOperationException
     */
    public ProductCategoryExecution batchAddProductCategory(List<ProductCategory> productCategoryList)
            throws ProductCategoryOperationException {
        if (null != productCategoryList && productCategoryList.size() > 0) {
            try {
                int effectNum = productCategoryDao.batchInsertProductCategory(productCategoryList);
                if (effectNum <= 0) {
                    throw new ProductCategoryOperationException("店铺类别创建失败");
                } else {
                    return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS, productCategoryList);
                }
            } catch (Exception e) {
                throw new ProductCategoryOperationException("batchAddProductCategory error : " + e.getMessage());
            }
        } else {
            return new ProductCategoryExecution(ProductCategoryStateEnum.EMPTY_LIST);
        }
    }

    /**
     * 根据商品分类id和店铺id删除商品分类
     *
     * @param productCategoryId
     * @param shopId
     * @return
     */
    public ProductCategoryExecution deleteProductCategory(Long productCategoryId, Long shopId)
            throws ProductCategoryOperationException {
        if (null != productCategoryId && productCategoryId > 0) {
            //删除tb_product表中商品与该商品分类productCategoryId的关联
            try {
                int effectNum = productDao.updateProductCategoryToNull(productCategoryId);
                if (effectNum < 0) {
                    throw new ProductCategoryOperationException("更新商品类别失败");
                }
            } catch (Exception e) {
                throw new ProductCategoryOperationException("deleteProductCategory error : " + e.getMessage());
            }
            try {
                int effectNum = productCategoryDao.deleteProductCategory(productCategoryId, shopId);
                if (effectNum <= 0) {
                    throw new ProductCategoryOperationException("删除商品类别失败");
                } else {
                    return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS);
                }
            } catch (Exception e) {
                throw new ProductCategoryOperationException("deleteProductCategory error : " + e.getMessage());
            }
        } else {
            return new ProductCategoryExecution(ProductCategoryStateEnum.EMPTY_ID);
        }
    }
}
