package com.yequan.o2o.util;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

public class EncryptPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {

    private String[] encryptPropertyNames = {"jdbc.username", "jdbc.password"};

    /**
     * 对加密属性进行转换
     *
     * @param propertyName
     * @param propertyValue
     * @return
     */
    protected String convertProperty(String propertyName, String propertyValue) {
        if (isEncryptProp(propertyName)) {
            return DESUtil.getDecryptString(propertyValue);
        } else {
            return propertyValue;
        }
    }

    private boolean isEncryptProp(String propertyName) {
        for (String encryptPropertyName : encryptPropertyNames) {
            if (encryptPropertyName.equals(propertyName)) {
                return true;
            }
        }
        return false;
    }

}
