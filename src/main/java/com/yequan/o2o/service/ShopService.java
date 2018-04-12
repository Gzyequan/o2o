package com.yequan.o2o.service;

import com.yequan.o2o.dto.ImageHolder;
import com.yequan.o2o.dto.ShopExecution;
import com.yequan.o2o.entity.Shop;
import com.yequan.o2o.exceptions.ShopOperationException;

import java.io.File;
import java.io.InputStream;

public interface ShopService {

    /**
     * 根据查询条件分页获取店铺列表
     *
     * @param shopCondition
     * @param pageIndex
     * @param pageSize
     * @return
     */
    ShopExecution queryShopList(Shop shopCondition, int pageIndex, int pageSize);

    /**
     * 根据id获取店铺信息
     *
     * @param shopId
     * @return
     */
    Shop getByShopId(Long shopId);

    /**
     * 添加新店铺信息
     *
     * @param shop
     * @param imageHolder
     * @return
     * @throws ShopOperationException
     */
    ShopExecution addShop(Shop shop, ImageHolder imageHolder) throws ShopOperationException;

    /**
     * 修改店铺信息
     *
     * @param shop
     * @param imageHolder
     * @return
     * @throws ShopOperationException
     */
    ShopExecution modifyShop(Shop shop, ImageHolder imageHolder) throws ShopOperationException;
}
