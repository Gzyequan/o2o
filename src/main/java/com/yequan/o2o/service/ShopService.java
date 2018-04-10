package com.yequan.o2o.service;

import com.yequan.o2o.dto.ShopExecution;
import com.yequan.o2o.entity.Shop;
import com.yequan.o2o.exceptions.ShopOperationException;

import java.io.File;
import java.io.InputStream;

public interface ShopService {

    Shop getByShopId(Long shopId);

    ShopExecution addShop(Shop shop, InputStream shopImgInputStream, String fileName) throws ShopOperationException;

    ShopExecution modifyShop(Shop shop, InputStream shopImgInputStream, String fileName) throws ShopOperationException;
}
