package com.yequan.o2o.util;

import com.yequan.o2o.dto.ImageHolder;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;


public class ImageUtil {

    private static String basePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
    private static final SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    private static final Random random = new Random();
    private static Logger logger = LoggerFactory.getLogger(ImageUtil.class);

    public static File transferCommonsMultipartFileToFile(CommonsMultipartFile cFile) {
        File newFile = new File(cFile.getOriginalFilename());
        try {
            cFile.transferTo(newFile);
        } catch (IOException e) {
            logger.error(e.toString());
            e.printStackTrace();
        }
        return newFile;
    }

    /**
     * 处理缩略图，并返回缩略图的相对路径
     *
     * @param thumbnail  图片File对象
     * @param targetAddr 相对路径中的文件夹，例如 /data/shop/images/a.jpg  targetAddr就是/data/shop/images/
     * @return
     */
    public static String generateThumbnail(ImageHolder thumbnail, String targetAddr) {
        String realFileName = getRandomFileName();
        String extension = getThumbnailExtension(thumbnail.getImageName());
        makeDirPath(targetAddr);
        String relativePath = targetAddr + realFileName + extension;
        logger.debug("current relative address is : " + relativePath);
        File dest = new File(PathUtil.getImageBasePth() + relativePath);
        logger.debug("current complete address is : " + PathUtil.getImageBasePth() + relativePath);
        logger.debug("base path is : " + basePath);
        try {
            Thumbnails.of(thumbnail.getImage()).size(200, 200)
                    .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath + "watermark.jpg")), 0.25f)
                    .outputQuality(0.8f).toFile(dest);
        } catch (IOException e) {
            logger.error(e.toString());
            e.printStackTrace();
        }
        return relativePath;
    }

    /**
     * 生成缩略图所在的文件夹 例如 /o2o/images/xxx.jpg
     * o2o images都需要生成
     *
     * @param targetAddr
     */
    private static void makeDirPath(String targetAddr) {
        String realFileParentPath = PathUtil.getImageBasePth() + targetAddr;
        File dirPath = new File(realFileParentPath);
        if (!dirPath.exists()) {
            dirPath.mkdirs();
        }
    }

    /**
     * 获取文件扩展名
     *
     * @return
     */
    private static String getThumbnailExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    /**
     * 生成随机唯一的文件名：当前时间+随机五位数
     *
     * @return
     */
    public static String getRandomFileName() {
        String nowTimeStr = sDateFormat.format(new Date());
        int rannum = random.nextInt(89999) + 10000;
        return nowTimeStr + rannum;
    }

    public static void main(String[] args) throws IOException {
        Thumbnails.of(new File("F:/o2o/image/mogu.jpg")).size(200, 200)
                .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath + "/watermark.png")), 0.25f)
                .outputQuality(0.8f).toFile("F:/o2o/image/mogunew.jpg");
    }

    /**
     * storeFile是文件路径或文件所在目录，如果是文件路径则删除文件，如果是文件所在的目录，则删除该目录下的所有文件
     *
     * @param storeFile
     */
    public static void deleteFileOrPath(String storeFile) {
        File fileOrPath = new File(PathUtil.getImageBasePth() + storeFile);
        if (fileOrPath.isDirectory()) {
            File[] files = fileOrPath.listFiles();
            if (null != files && files.length > 0) {
                for (File file : files) {
                    file.delete();
                }
            }
        }
        fileOrPath.delete();
    }

}
