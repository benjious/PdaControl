package com.benjious.pdacontrol.webService;

import android.util.Log;

import com.benjious.pdacontrol.util.OkHttpUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static android.R.string.ok;


/**
 * Created by Benjious on 2017/10/8.
 */

public class WLoginService   {
    private static String IP = "192.168.137.1:8080";
    public static final String TAG = "WLoginService xyz =";

    //通过GET方式获取HTTP服务器数据
    public static String executeHttpGet(String username, String password) {
        HttpURLConnection connection = null;
        InputStream inputStream = null;

        try {
            String path = "http://" + IP + "/liku/LogLet";
            username = charSetConvert(username);


            path = path + "?username=" + username + "&password=" + password;
            Log.d(TAG, "xyz  executeHttpGet: " + path);
//            connection = (HttpURLConnection) new URL(path).openConnection();
//            connection.setConnectTimeout(3000);
//            connection.setReadTimeout(3000);
//            connection.setDoInput(true);
//            connection.setRequestMethod("GET");
//            connection.setRequestProperty("Charset", "UTF-8");
//
//            Log.d(TAG, "返回的相应码 ： " + connectn.getResponseCode());


//            if (connection.getResponseCode() == 200) {
//                inputStream = connection.getInputStream();
//                return parseInfo(inputStream);
//            }


            OkHttpUtils.ResultCallback<String> resultCallback = new OkHttpUtils.ResultCallback<String>() {
                @Override
                public void onSuccess(String response) {
                    System.out.println("返回的response 是： " + response);
                }

                @Override
                public void onFailure(Exception e) {

                }
            };
            String path1 = "http://192.168.137.1:8080/liku/getJson?page=0";
            Log.d(TAG, "xyz  executeHttpGet: 执行了吗？？？？？？？？");
            OkHttpUtils.get(path1, resultCallback);

            return "";
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
//            //意外退出时进行连接关闭保护
//            if (connection != null) {
//                connection.disconnect();
//            }
//
//            if (inputStream != null) {
//                try {
//                    inputStream.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//            }
        }
        Log.d(TAG, "xyz  executeHttpGet: 上面没执行到");
        return null;
    }

    //将输入流转化成string 型
    private static String parseInfo(InputStream in) throws Exception {
        byte[] data = read(in);
        return new String(data, "UTF-8");

    }

    public static byte[] read(InputStream in) throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = in.read(buffer)) != -1) {
            out.write(buffer, 0, len);
        }
        in.close();
        return out.toByteArray();
    }


    public static String charSetConvert(String request) {
        String charSet = geteEncoding(request);
        System.out.println("传过来的编码是   ： " + charSet);
        try {
            byte[] b = request.getBytes(charSet);
            request = new String(b, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return request;

    }

    public static String geteEncoding(String str) {
        String[] charSet = {"GBK", "GB2312", "UTF-8", "ISO-8859-1"};
        try {
            for (int i = 0; i < charSet.length; i++) {
                if (str.equals(new String(str.getBytes(charSet[i]), charSet[i]))) {
                    String s1 = charSet[i];
                    return s1;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


}
