package com.yequan.o2o.dao;

import com.yequan.o2o.entity.Area;

import java.util.List;

public interface AreaDao {
    /**
     * 查询Area数据
     *
     * @return areaList
     */
    List<Area> queryArea();
}
