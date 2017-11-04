package com.benjious.pdacontrol.util;

/**
 * Created by Benjious on 2017/10/31.
 */

public class CheckboxUtil {
    public static String convert(boolean checkNot){
        String checkNotString = null;
        if (checkNot) {
            checkNotString = "1";
        } else {
            checkNotString = "0";
        }
        return checkNotString;
    }
}
