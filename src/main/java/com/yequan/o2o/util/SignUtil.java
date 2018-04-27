package com.yequan.o2o.util;

import java.security.MessageDigest;
import java.util.Arrays;

public class SignUtil {

    private static String token = "myo2o";

    public static boolean checkSignature(String signature, String timestamp, String nonce) {
        String[] arr = {token, timestamp, nonce};
        Arrays.sort(arr);
        StringBuilder content = new StringBuilder();
        for (String s : arr) {
            content.append(s);
        }
        MessageDigest md = null;
        String tmpStr = null;

        try {
            md = MessageDigest.getInstance("SHA-1");
            byte[] digest = md.digest(content.toString().getBytes());
            tmpStr = byteToStr(digest);
        } catch (Exception e) {
            e.printStackTrace();
        }
        content=null;
        return tmpStr != null && tmpStr.equals(signature.toUpperCase());
    }

    /**
     * 将字节组转换成十六进制字符串
     *
     * @param byteArray
     * @return
     */
    private static String byteToStr(byte[] byteArray) {
        String strDigest = "";
        for (byte b : byteArray) {
            strDigest += byteToHexStr(b);
        }
        return strDigest;
    }

    /**
     * 将字节转换成16进制字符串
     *
     * @param myByte
     * @return
     */
    private static String byteToHexStr(byte myByte) {
        String s = null;
        char[] digit = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        char[] tmpArr = new char[2];
        tmpArr[0] = digit[myByte >>> 4 & 0x0F];
        tmpArr[1] = digit[myByte & 0x0F];
        s = new String(tmpArr);
        return s;
    }

}
