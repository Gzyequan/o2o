package com.yequan.o2o.util;

import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.Properties;

public class ConfigFileUtil {

    public static String getProperty(String key) {
        String property = "";
        try {
            Properties properties = PropertiesLoaderUtils.loadAllProperties("classpath:config.properties");
            System.out.println(properties.size());
            property = properties.getProperty(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return property;
    }

}
