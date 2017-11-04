package com.benjious.pdacontrol.util;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by Benjious on 2017/11/3.
 */

public class NetworkUtil {


    public static boolean checkNetworkState(Context mContext) {
        boolean flag = false;
        //得到网络连接信息
        ConnectivityManager manager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        //去进行判断网络是否连接
        if (manager.getActiveNetworkInfo() != null) {
            flag = manager.getActiveNetworkInfo().isAvailable();
        }
        return flag;
    }
}
