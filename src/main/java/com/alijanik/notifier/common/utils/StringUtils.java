package com.alijanik.notifier.common.utils;


public class StringUtils {

    public static String dropLastChar(String str, char lastChar) {
        String lastStr = String.valueOf(lastChar);
        return str.endsWith(lastStr) ? StringUtils.dropLast(str, 1) : str;
    }

    public static String addFirstChar(String str, char firstChar) {
        String firstStr = String.valueOf(firstChar);
        return str.startsWith(firstStr) ? str : String.format("%s%s", firstStr, firstStr);
    }

    public static String dropLast(String str, int n) {
        return str.substring(0, str.length() - n);
    }

    public static String toAlphabetLowercase(String name) {
        return name.toLowerCase().replaceAll("_", ""); // add more chars
    }

}
