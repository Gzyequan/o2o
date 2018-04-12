package com.yequan.o2o.service.impl;

import com.yequan.o2o.dao.ShopDao;
import com.yequan.o2o.dto.ImageHolder;
import com.yequan.o2o.dto.ShopExecution;
import com.yequan.o2o.entity.Shop;
import com.yequan.o2o.enums.ShopStateEnum;
import com.yequan.o2o.exceptions.ShopOperationException;
import com.yequan.o2o.service.ShopService;
import com.yequan.o2o.util.ImageUtil;
import com.yequan.o2o.util.PageCalculateUtil;
import com.yequan.o2o.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

@Service
public class ShopServiceImpl implements ShopService {

    @Autowired
    private ShopDao shopDao;

    public ShopExecution queryShopList(Shop shopCondition, int pageIndex, int pageSize) {
        int rowIndex = PageCalculateUtil.rowIndexCalculate(pageIndex, pageSize);
        List<Shop> shopList = shopDao.queryShopList(shopCondition, rowIndex, pageSize);
        int count = shopDao.queryShopCount(shopCondition);
        ShopExecution se = new ShopExecution();
        if (null != shopList && shopList.size() > 0) {
            se.setShopList(shopList);
            se.setCount(count);
        } else {
            se.setState(ShopStateEnum.INNER_ERROR.getState());
        }
        return se;
    }

    public Shop getByShopId(Long shopId) {
        return shopDao.queryByShopId(shopId);
    }

    @Transactional
    public ShopExecution addShop(Shop shop, ImageHolder thumbnail) throws ShopOperationException {
        if (null == shop) {
            return new ShopExecution(ShopStateEnum.NULL_SHOP);
        }
        try {
            shop.setEnableStatus(0);
            shop.setCreateTime(new Date());
            shop.setLastEditTime(new Date());
            int effectNum = shopDao.insertShop(shop);
            if (effectNum <= 0) {
                throw new ShopOperationException("店铺创建失败");
            } else {
                if (null != thumbnail.getImage()) {
                    try {
                        addShopImg(shop, thumbnail);
                    } catch (Exception e) {
                        throw new ShopOperationException("add shop image error:" + e.getMessage());
                    }
                    effectNum = shopDao.updateShop(shop);
                    if (effectNum <= 0) {
                        throw new ShopOperationException("图片地址更新失败！");
                    }
                }
            }
        } catch (Exception e) {
            throw new ShopOperationException(e.getMessage());
        }
        return new ShopExecution(ShopStateEnum.CHECK, shop);
    }

    public ShopExecution modifyShop(Shop shop, ImageHolder thumbnail) throws ShopOperationException {
        if (null == shop) {
            return new ShopExecution(ShopStateEnum.NULL_SHOP);
        } else {
            try {
                //更新图片信息
                if (null != thumbnail.getImage() && null != thumbnail.getImageName() && !"".equals(thumbnail.getImageName())) {
                    Shop tempShop = shopDao.queryByShopId(shop.getShopId());
                    if (null != tempShop.getShopImg() && !"".equals(tempShop.getShopImg())) {
                        ImageUtil.deleteFileOrPath(tempShop.getShopImg());
                    }
                    addShopImg(shop, thumbnail);
                }
                //跟新店铺信息
                shop.setLastEditTime(new Date());
                int effectNum = shopDao.updateShop(shop);
                if (effectNum <= 0) {
                    return new ShopExecution(ShopStateEnum.INNER_ERROR);
                } else {
                    shop = shopDao.queryByShopId(shop.getShopId());
                    return new ShopExecution(ShopStateEnum.SUCCESS, shop);
                }
            } catch (Exception e) {
                throw new ShopOperationException(e.getMessage());
            }
        }
    }

    /**
     * 将图片信息写入到数据库，并存储图片
     *
     * @param shop
     * @param Thumbnail
     */
    private void addShopImg(Shop shop, ImageHolder Thumbnail) {
        String dest = PathUtil.getShopImagePath(shop.getShopId());
        String imgAddr = ImageUtil.generateThumbnail(Thumbnail, dest);
        shop.setShopImg(imgAddr);
    }
}
