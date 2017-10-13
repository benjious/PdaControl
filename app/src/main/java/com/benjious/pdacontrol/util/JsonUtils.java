package com.benjious.pdacontrol.util;


import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;

import java.lang.reflect.Type;

/**JSON 转换工具类
 *
 * Created by Benjious on 2017/1/13.
 */
public class JsonUtils {
    private static Gson sGson = new Gson();

    /**
     * 将对象转换为json字符串
     */
    public static <T> String serialize(T object)throws JSONException{
        return sGson.toJson(object);
    }

    /**
     * 将json转换为对象
     * @param json
     * @param tClass
     * @param <T>
     * @return
     * @throws JSONException
     */
    public static <T> T deserialize(String json,Class<T> tClass)throws JSONException{
        return sGson.fromJson(json,tClass);
    }

    /**
     * 将json转换为实体对象
     * @param json
     * @param tClass
     * @param <T>
     * @return
     * @throws JSONException
     */
    public static <T> T deserialize(JsonObject json, Class<T> tClass)throws JSONException{
        return sGson.fromJson(json,tClass);
    }

    public static <T> T deserialize(String object, Type type)throws JSONException{
        return sGson.fromJson(object, type);
    }
}
