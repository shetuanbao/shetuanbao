package com.community.shetuanbao.utils;

import android.os.Build;
import android.os.Looper;
import android.support.annotation.RequiresApi;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.community.shetuanbao.utils.RequestUtils.HOSTPORT;

public class GetFriendInfo {
    Integer CurrentUser = 0;
    private static OkHttpClient client = new OkHttpClient();
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static String getConn() throws IOException {
        int user_id = 2;
        String url = HOSTPORT + "/friends/getFriends?userId=" + user_id;

        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }


    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static JSONArray getfriends() throws IOException {
//        List<User>  user=null;
        JSONArray list = null;
        String res = getConn();
        Log.d("response",res);
        try {
            JSONObject jsonObject = new JSONObject(res);
            if (jsonObject.getInt("code") == 200) {
                // 如何code==200,说明连接数据库成功并成功取得数据
                list = (JSONArray) jsonObject.get("data");
                return list;
            } else {
                Looper.prepare();
                Log.d("消息", "连接失败");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static int getFriendNum() throws IOException {
        int i = getfriends().length();
        return i;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static List<String> getFriendNames() throws IOException, JSONException {
        List<String> names = null;
        for (int i = 0; i < getfriends().length(); i++) {
            names.add(getfriends().getJSONObject(i).getString("userName"));
        }
        return names;

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static List<String> getFriendPens() throws IOException, JSONException {
        List<String> pens = null;
        for (int i = 0; i < getfriends().length(); i++) {
            pens.add(getfriends().getJSONObject(i).getString("userpen"));
        }
        return pens;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static List<String> getFriendPhotos() throws IOException, JSONException {
        List<String> photos = null;
        for (int i = 0; i < getfriends().length(); i++) {
            photos.add(getfriends().getJSONObject(i).getString("userphoto"));
        }
        return photos;
    }

    public static List<String> getUserIds() throws IOException {
        List<String> ids=null;
        JSONArray list=new JSONArray();
        String res = RequestUtils.get("/users/YangGetIds");
        Log.d("response", res);

        // dosomething with res
        try {

            JSONObject jsonObject = new JSONObject(res);
            if (jsonObject.getInt("code") == 200) {
                list = (JSONArray) jsonObject.get("data");
                for(int i=0;i<list.length();i++){
                    ids.add(list.getJSONObject(i).getString("user_id"));
                }
            } else {
                Log.d("信息","连接服务器失败");
                Looper.prepare();

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ids;
    }
    public static void shanchuhaoyou(String id){

    }
    public static byte[] getImage(String name){
        byte[] a=null;
        return a;
    }
}
