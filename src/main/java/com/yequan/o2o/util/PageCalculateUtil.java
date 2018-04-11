package com.yequan.o2o.util;

public class PageCalculateUtil {
    public static int rowIndexCalculate(int pageIndex, int pageSize) {
        return (pageIndex > 0) ? (pageIndex - 1) * pageSize : 0;
    }
}
