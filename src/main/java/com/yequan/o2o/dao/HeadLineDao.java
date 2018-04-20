package com.yequan.o2o.dao;

import com.yequan.o2o.entity.HeadLine;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HeadLineDao {

    List<HeadLine> queryHeadLine(@Param("headLineCondition") HeadLine headLineCondition);

}
