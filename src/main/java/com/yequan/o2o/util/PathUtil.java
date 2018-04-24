package com.yequan.o2o.util;


public class PathUtil {

    private static String separator = System.getProperty("file.separator");

    public static String getImageBasePath() {
        String os = System.getProperty("os.name");
        String basePath = "";
        if (os.toLowerCase().startsWith("win")) {
//            basePath = ConfigFileUtil.getProperty("win_path");
            basePath = "F:/o2o/image";
        } else {
//            basePath = ConfigFileUtil.getProperty("linux_path");
            basePath = "/home/image";
        }
//        basePath.replace("/", separator);
        return basePath;
    }

    public static String getShopImagePath(Long shopId) {
        String imagePath = "/upload/images/item/shop/" + shopId + "/";
//        return imagePath.replace("/", separator);
        return imagePath;
    }
}
