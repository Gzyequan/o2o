package com.yequan.o2o.service;

import com.yequan.o2o.dto.ShopExecution;
import com.yequan.o2o.entity.Shop;

import java.io.File;

public interface ShopService {
    ShopExecution addShop(Shop shop, File shopImg);
}
