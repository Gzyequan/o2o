package com.yequan.o2o.enums;

public enum ProductCategoryStateEnum {
    SUCCESS(1, "操作成功"), INNER_ERROR(-1001, "操作失败"), EMPTY_LIST(-1002, "商品分类集合为空"),
    EMPTY_ID(-1003, "商品分类id为空");
    private int state;
    private String stateInfo;

    private ProductCategoryStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public static ProductCategoryStateEnum stateOf(int state) {
        for (ProductCategoryStateEnum productCategoryStateEnum : values()) {
            if (productCategoryStateEnum.state == state) {
                return productCategoryStateEnum;
            }
        }
        return null;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }
}
