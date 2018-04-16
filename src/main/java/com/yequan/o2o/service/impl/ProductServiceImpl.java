package com.yequan.o2o.service.impl;

import com.yequan.o2o.dao.ProductDao;
import com.yequan.o2o.dao.ProductImgDao;
import com.yequan.o2o.dto.ImageHolder;
import com.yequan.o2o.dto.ProductExecution;
import com.yequan.o2o.entity.Product;
import com.yequan.o2o.entity.ProductImg;
import com.yequan.o2o.enums.ProductStateEnum;
import com.yequan.o2o.exceptions.ProductOperationException;
import com.yequan.o2o.service.ProductService;
import com.yequan.o2o.util.ImageUtil;
import com.yequan.o2o.util.PageCalculateUtil;
import com.yequan.o2o.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;
    @Autowired
    private ProductImgDao productImgDao;

    @Transactional
    public ProductExecution addProduct(Product product, ImageHolder thumbnail, List<ImageHolder> imageHolderList)
            throws ProductOperationException {
        if (null != product && null != product.getShop() && null != product.getShop().getShopId()) {
            product.setCreateTime(new Date());
            product.setLastEditTime(new Date());
            product.setEnableStatus(1);
            //1、添加商品缩略图
            if (null != thumbnail) {
                addThumbnail(product, thumbnail);
            }
            //2、插入商品信息
            try {
                int effectNum = productDao.insertProduct(product);
                if (effectNum <= 0) {
                    throw new ProductOperationException("创建商品失败");
                }
            } catch (Exception e) {
                throw new ProductOperationException("创建商品失败 : " + e.getMessage());
            }
            //3、插入商品详情图
            if (null != imageHolderList && imageHolderList.size() > 0) {
                addProductImageList(product, imageHolderList);
            }
            return new ProductExecution(ProductStateEnum.SUCCESS, product);
        } else {
            return new ProductExecution(ProductStateEnum.EMPTY);
        }
    }

    public ProductExecution getProductList(Product productCondition, int pageIndex, int pageSize) {
        int rowIndex = PageCalculateUtil.rowIndexCalculate(pageIndex, pageSize);
        List<Product> productList = productDao.queryProductList(productCondition, rowIndex, pageSize);
        ProductExecution pe = new ProductExecution();
        if (null != productList) {
            pe.setCount(productList.size());
            pe.setProductList(productList);
            pe.setState(ProductStateEnum.SUCCESS.getState());
        } else {
            pe.setState(ProductStateEnum.INNER_ERROR.getState());
        }
        return pe;
    }

    private void addProductImageList(Product product, List<ImageHolder> imageHolderList) {
        String dest = PathUtil.getShopImagePath(product.getShop().getShopId());
        List<ProductImg> productImgList = new ArrayList<ProductImg>();
        for (ImageHolder productImageHolder : imageHolderList) {
            String imgAddr = ImageUtil.generateNormalImg(productImageHolder, dest);
            ProductImg productImg = new ProductImg();
            productImg.setImgAddr(imgAddr);
            productImg.setProductId(product.getProductId());
            productImg.setCreateTime(new Date());
            productImgList.add(productImg);
        }
        if (productImgList.size() > 0) {
            try {
                int effectNum = productImgDao.batchInsertProductImg(productImgList);
                if (effectNum <= 0) {
                    throw new ProductOperationException("创建商品详情图片失败");
                }
            } catch (Exception e) {
                throw new ProductOperationException("创建商品详情图片失败 ：" + e.getMessage());
            }
        }
    }

    /**
     * 为商品添加缩略图
     *
     * @param product
     * @param thumbnail
     */
    private void addThumbnail(Product product, ImageHolder thumbnail) {
        String dest = PathUtil.getShopImagePath(product.getShop().getShopId());
        String thumbnailAddr = ImageUtil.generateThumbnail(thumbnail, dest);
        product.setImgAddr(thumbnailAddr);
    }
}
