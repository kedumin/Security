package com.xiaobin.security.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by kedumin on 14-2-9.
 */
public class MD5Encoder {

    public static String encode(String pwd){
        try{
            MessageDigest messageDigest=MessageDigest.getInstance("MD5");//拿到md5加密的对象
            byte[] bytes=messageDigest.digest(pwd.getBytes());//返回一个加密后的字节数组
            StringBuffer stringBuffer=new StringBuffer();
            String tmp;
            for(int i=0;i<bytes.length;i++){
                tmp=Integer.toHexString(0xff & bytes[i]);//把字节转换为16进制的字符串
                if(tmp.length()==1){
                    stringBuffer.append("0"+tmp);
                }else{
                    stringBuffer.append(tmp);
                }
            }
            return stringBuffer.toString();
        }catch(NoSuchAlgorithmException e){
            throw new RuntimeException("没有这个加密算法"+e);
        }
    }
}
