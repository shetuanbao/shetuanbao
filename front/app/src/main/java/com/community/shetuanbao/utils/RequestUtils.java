package com.community.shetuanbao.utils;
import android.annotation.SuppressLint;
import android.util.Log;

import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@SuppressLint("NewApi")
/*
 * 请求工具类，封装了post和get请求
 */
public class RequestUtils {
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    public static final String HOSTPORT = "http://192.168.62.1:8010"; //后端端口号已设置为8010无需修改，ip地址改成当前的ipv4地址

    private static OkHttpClient client = new OkHttpClient();

    // POST传输数据
    public static String post(String url, Map<String, Object> params) throws IOException {

        // 拼接url
        url = HOSTPORT + url;

        // 创建json
        JSONObject json = new JSONObject(params);
        String jsonString = json.toString();

        Log.d("request", "url: " + url + ", params: " + jsonString);

        // 创建连接
        RequestBody body = RequestBody.create(JSON, jsonString);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        // 得到相应内容体
        try ( Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    public static String get(String url) throws IOException {
        // 拼接url
        url = HOSTPORT + url;

        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }
}
