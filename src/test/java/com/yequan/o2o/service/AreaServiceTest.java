package com.yequan.o2o.service;

import com.yequan.o2o.BaseTest;
import com.yequan.o2o.entity.Area;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class AreaServiceTest extends BaseTest {
    @Autowired
    private AreaService areaService;

    @Test
//    @Ignore
    public void testQueryArea(){
        List<Area> areas = areaService.queryArea();
        assertEquals("西苑",areas.get(0).getAreaName());
        System.out.println(areas.get(0).getAreaName());
    }
}
