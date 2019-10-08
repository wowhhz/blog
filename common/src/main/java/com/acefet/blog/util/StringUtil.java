package com.acefet.blog.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StringUtil {
	
	public static boolean isNullStr(String str){
		return str==null || str.length()==0;
	}
	
	public static String formatDate(Date date){
		return formatDate(date,"yyyy-MM-dd HH:mm:ss");
	}
	
	public static String formatDate(Date date,String pattern){
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date);
	}
	
	public static Date parse(String str) throws ParseException{
		return parse(str,"yyyy-MM-dd HH:mm:ss");
	}
	
	public static Date parse(String str ,String pattern) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.parse(str);
	}
	
	 /** 
     * 将一个字符串的首字母改为大写或者小写 
     * 
     * @param srcString 源字符串 
     * @param flag  大小写标识，ture小写，false大写
     * @return 改写后的新字符串 
     */ 
    public static String toLowerCaseInitial(String srcString, boolean flag) { 
            StringBuilder sb = new StringBuilder(); 
            if (flag) { 
                    sb.append(Character.toLowerCase(srcString.charAt(0))); 
            } else { 
                    sb.append(Character.toUpperCase(srcString.charAt(0))); 
            } 
            sb.append(srcString.substring(1)); 
            return sb.toString(); 
    }

	/**
	 * 针对首字母进行大写转换
	 * @param str
	 * @return
	 */
	public static String upperCase(String str) {
    	char[] ch = str.toCharArray();
    	if (ch[0] >= 'a' && ch[0] <= 'z') {
    		ch[0] = (char) (ch[0] - 32);
    	}
    	return new String(ch);
    }
    
    /**
     * 将字符串数组转换成sql语句in()中的内容
     * @param strs
     * @return
     */
    public static String getSqlInStr(String[] strs){
    	StringBuffer buffer = new StringBuffer();
    	for (String str : strs) {
    		if(buffer.toString().length()>0)buffer.append(",");
    		buffer.append("'"+str+"'");
		}
    	return buffer.toString();
    	
    }

}
