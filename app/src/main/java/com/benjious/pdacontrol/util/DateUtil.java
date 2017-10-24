package com.benjious.pdacontrol.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Benjious on 2017/10/22.
 */

public class DateUtil {
    //java.util.date --> String
    public static String converToString(Date date) {
        String dateStr="";
//      DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss",Locale.US);
        DateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        try {
            dateStr = sdf2.format(date);
            System.out.println(dateStr);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateStr;

    }
    //String 转化为 java.util.Date

}
