package com.yequan.o2o.exceptions;

//当且仅当抛出RuntimeException及其子类才能支持事务的回滚和终止
public class ShopOperationException extends RuntimeException {
    public ShopOperationException(String msg) {
        super(msg);
    }
}
