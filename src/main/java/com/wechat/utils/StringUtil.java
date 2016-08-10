package com.wechat.utils;

/**
 * Created by luzhiyuan on 16/7/29.
 */
public class StringUtil {
    public static boolean isEmpty(String str) {
        if (str != null && str.trim() != null) {
            return false;
        }
        return true;
    }
}
