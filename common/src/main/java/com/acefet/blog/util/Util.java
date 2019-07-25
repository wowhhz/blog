package com.acefet.blog.util;

import org.springframework.util.DigestUtils;

import java.io.UnsupportedEncodingException;
import java.util.UUID;
import java.util.regex.Pattern;

public class Util {

    /**
     * 用于生成不重复的8位短字符串
     */
    public static String[] SHORT_CHARS = new String[] { "a", "b", "c", "d", "e", "f",
            "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
            "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z" };

    public static String getUUID(){
        return UUID.randomUUID().toString().replace("-","");
    }

    /**
     * 获取MD5加密字符串
     * @param str
     * @return
     */
    public static String getMD5Str(String str){
        return DigestUtils.md5DigestAsHex(str.getBytes());
    }

    /**
     * 生成不重复的8位短字符串
     * @return
     */
    public static String generateShortUuid() {
        StringBuffer shortBuffer = new StringBuffer();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        for (int i = 0; i < 8; i++) {
            String str = uuid.substring(i * 4, i * 4 + 4);
            int x = Integer.parseInt(str, 16);
            shortBuffer.append(SHORT_CHARS[x % 0x3E]);
        }
        return shortBuffer.toString();
    }

    /**
     * 判断8位短ID的有效性
     * @param id
     * @return
     */
    public static Boolean checkShortId(String id){
        if(id==null || id.length()!=8)return false;
        String checkStr = id;
        for (int i = 0; i < SHORT_CHARS.length; i++) {
            checkStr = checkStr.replace(SHORT_CHARS[i],"");
            if(checkStr.length()==0)return true;
        }
        return checkStr.length()==0;
    }

    /*
     * 判断是否为整数
     * @param str 传入的字符串
     * @return 是整数返回true,否则返回false
     */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

    /**
     * blob字段内容转码处理
     * @param blobContent
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String getBlobField(String blobContent) throws UnsupportedEncodingException {
        if (null==blobContent)return null;
        return new String(blobContent.getBytes("ISO-8859-1"),"UTF-8");
    }


}
