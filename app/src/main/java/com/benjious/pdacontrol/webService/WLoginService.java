package com.benjious.pdacontrol.webService;

import android.util.Log;

import com.benjious.pdacontrol.url.Url;
import com.benjious.pdacontrol.been.UsersALL;
import com.benjious.pdacontrol.util.OkHttpUtils;
import com.benjious.pdacontrol.view.CommonView;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;



/**
 * Created by Benjious on 2017/10/8.
 */

public class WLoginService {
    public static final String TAG = "WLoginService xyz =";
    public static final int LOGIN =3 ;


    //通过GET方式获取HTTP服务器数据
    public static String executeHttpGet(String username, String password, final CommonView view) {
        final String[] holdMsg = {""};

        try {
            String path = Url.PATH+"/LogLet";
            username = charSetConvert(username);
            path = path + "?username=" + username + "&password=" + password;
            Log.d(TAG, "xyz  executeHttpGet: " + path);


            OkHttpUtils.ResultCallback<String> resultCallback = new OkHttpUtils.ResultCallback<String>() {
                @Override
                public void onSuccess(String response) {
                    if (response == null) {
                        return;
                    }
                    Gson gson = new Gson();
                    UsersALL usersALL = gson.fromJson(response, UsersALL.class);
                    if (usersALL==null) {
                        view.showLoadFail(OkHttpUtils.SERVER_OFFLINE);
                    }else {
                        if (usersALL.getUsers().size() != 0) {
                            Log.d(TAG, "xyz  onSuccess: ----" + usersALL.getUsers().get(0).getUsername());
                            view.addData(usersALL.getUsers().get(0).getUsername(),LOGIN);
                        } else {
                            view.showLoadFail(OkHttpUtils.NO_REAL_DATA);
                        }
                    }
                }


                @Override
                public void onFailure(Exception e) {
                    view.loadExecption(e);
                }
            };
            OkHttpUtils.get(path, resultCallback);

        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d(TAG, "xyz  executeHttpGet: " + holdMsg[0]);
        return holdMsg[0];
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
