package com.bzgwl.mybatis_plus.utils;

public class LowAndUpConvert {

    public static String lowFirst(String string){

        char[] chars = string.toCharArray();
        //判断第一个字符是否为小写
        boolean lowerCase = Character.isLowerCase(string.charAt(0));
        if(lowerCase){
            return string;
        }else {
            chars[0] +=32;
            return String.valueOf(chars);
        }

    }

    public static String upFirst(String string){

        char[] chars = string.toCharArray();
        //判断第一个字符是否为大写
        boolean upperCase = Character.isUpperCase(string.charAt(0));
        if(upperCase){
            return string;
        }
        chars[0] -=32;
        return String.valueOf(chars);
    }

}
