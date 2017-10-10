package com.benjious.pdacontrol.webService;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.*;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;

/**
 * Created by Benjious on 2017/10/8.
 */

//这是function分支
public class ClientPost {

    private static String IP = "192.168.137.1:8080";


    //通过GET方式获取HTTP服务器数据
    public static String executeHttpPost(String username, String password) {


        try {
            String path = "http://" + IP + "/liku/servlet/MyServlet";
            path = path + "?username =" + username + "&password =" + password;

            //发送指令和信息
            Map<String, String> params = new HashMap<>();
            params.put("username", username);
            params.put("password", password);

            return sendPostRequest(path, params, "UTF-8");


        } catch (Exception e) {
            e.printStackTrace();
        }
        return  null;
    }



    //将输入流转化成string 型
    private static String parseInfo(InputStream in) throws Exception {
        byte[] data = read(in);
        return new String(data, "UTF-8");

    }

    public  static byte[] read(InputStream in) throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = in.read(buffer)) != -1) {
            out.write(buffer, 0, len);
        }
        in.close();
        return out.toByteArray();
    }

    private static String sendPostRequest(String path, Map<String, String> params, String encoding) throws Exception {
        List<NameValuePair> pairs = new ArrayList();
        if (params != null && !params.isEmpty()) {
            for (Map.Entry<String, String> entry : params.entrySet())
                pairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(pairs, encoding);
        HttpPost post = new HttpPost(path);
        post.setEntity(entity);
        DefaultHttpClient client = new DefaultHttpClient();


        //请求超时
        client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 500);

        //读取超时
        client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 5000);
        HttpResponse response = client.execute(post);

        //判断是否成功收取信息
        if (response.getStatusLine().getStatusCode() == 200) {
            return getInfo(response);
        }

        return null;

    }

    // 收取数据
    private static String getInfo(HttpResponse response) throws Exception {

        HttpEntity entity = response.getEntity();
        InputStream is = entity.getContent();
        // 将输入流转化为byte型
        byte[] data = WLoginService.read(is);
        // 转化为字符串
        return new String(data, "UTF-8");
    }

}
