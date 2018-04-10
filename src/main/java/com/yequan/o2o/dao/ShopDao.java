package com.yequan.o2o.dao;

import com.yequan.o2o.entity.Shop;

public interface ShopDao {

    /**
     * 根据shopId查询店铺信息
     *
     * @param shopId
     * @return
     */
    Shop queryByShopId(Long shopId);

    /**
     * 新增店铺
     *
     * @param shop
     * @return
     */
    int insertShop(Shop shop);

    /**
     * 更新店铺信息
     *
     * @param shop
     * @return
     */
    int updateShop(Shop shop);
}
