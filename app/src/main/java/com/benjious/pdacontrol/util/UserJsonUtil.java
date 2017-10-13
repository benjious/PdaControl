package com.benjious.pdacontrol.util;

import com.benjious.pdacontrol.been.User;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Benjious on 2017/10/12.
 */

public class UserJsonUtil {

    /**
     * 将获取到的json转换为电影列表对象
     *
     * @param res
     * @param value
     * @return
     */
    public static List<User> readUserJsonBeans(String res, String value) {
        List<User> beans = new ArrayList<>();
        try{
        JsonParser parser = new JsonParser();
        JsonObject jsonObj = parser.parse(res).getAsJsonObject();
        JsonElement jsonElement = jsonObj.get(value);
        if (jsonElement == null) {
            return null;
        }
        JsonArray jsonArray = jsonElement.getAsJsonArray();
        for (int i = 1; i < jsonArray.size(); i++) {
            JsonObject jo = jsonArray.get(i).getAsJsonObject();
            if (jo.has("Users")) {
                User user = JsonUtils.deserialize(jo, User.class);
                beans.add(user);
            }
        }
    }catch(Exception e){

        }
        return beans;
    }
}