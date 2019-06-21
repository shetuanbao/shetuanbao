package com.community.shetuanbao.utils;

import android.os.Build;
import android.os.Looper;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.community.shetuanbao.Login.LoginActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;

public class GetFriendInfo {
    Integer CurrentUser = 0;
    public static String[] name;
    public static String[] pen;
    public static String[] photo;
    public static Integer[] id;
    public static int a;
    public static byte[][] all_image;

    private static OkHttpClient client = new OkHttpClient();
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static String getConn() throws IOException {
        int user_id = LoginActivity.sp.getInt("SNO",0);
        Map<String, Object> params = new HashMap<>();
        params.put("userId", user_id);
        String res = RequestUtils.post("/friends/findFriends", params);
        Log.d("response", res);
        return res;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void getfriends() throws IOException {
//        List<User>  user=null;
        JSONArray list = null;
        String res = getConn();
        Log.d("response", res);
        try {
            JSONObject jsonObject = new JSONObject(res);
            if (jsonObject.getInt("code") == 200) {
                // 如何code==200,说明连接数据库成功并成功取得数据
                list = (JSONArray) jsonObject.get("data");
                a = list.length();
                name = new String[a];
                pen = new String[a];
                photo = new String[a];
                id = new Integer[a];
                for (int i = 0; i < a; i++) {
                    name[i] = (list.getJSONObject(i).getString("userName"));
                    pen[i] = (list.getJSONObject(i).getString("userpen"));
                    photo[i] = (list.getJSONObject(i).getString("userphoto"));
                    id[i] = (list.getJSONObject(i).getInt("userId"));
                }


            } else {
                Looper.prepare();
                Log.d("消息", "连接失败");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public static Integer[] getUserIds() throws IOException {
        Integer[] ids = null;
        // dosomething with res
        try {
            JSONArray list = new JSONArray();
            String res = RequestUtils.get("/users/YangGetIds");
            Log.d("response", res);

            JSONObject jsonObject = new JSONObject(res);
            if (jsonObject.getInt("code") == 200) {
                list = (JSONArray) jsonObject.get("data");
                ids = new Integer[list.length()];
                for (int i = 0; i < list.length(); i++) {
                    ids[i] = (list.getJSONObject(i).getInt("userId"));
                }
            } else {
                Log.d("信息", "连接服务器失败");
                Looper.prepare();

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ids;
    }

    public static void shanchuhaoyou(String id) {

    }

    public static void getImage() {

        for (int i = 0; i < a; i++) {
            Map<String, Object> params = new HashMap<>();
            if (F_GetBitmap.isEmpty(photo[i])) {
                params = new HashMap<>();
                params.put("userphoto", photo[i]);
                String res1 = null;
                try {
                    res1 = RequestUtils.post("/users/yangGetPhoto", params);

                    JSONObject jsonObject1 = new JSONObject(res1);
                    Log.d("byte",res1);
                    if (jsonObject1.getInt("code") == 200) {
                        // 注意获取到的数据的数据类型，在后台是数组，则这里是JSONArray，在后台是类，则这里是JSONObject
                        JSONArray list1 = (JSONArray) jsonObject1.get("data");
                        all_image[i] = new byte[list1.length()];
                        for (int j = 0; j < list1.length(); j++) {
                            all_image[i][j] = (byte) list1.getInt(j);
                        }
                    }
                } catch (Exception e) {
                    Log.d("error", String.valueOf(e));
                }
            }
        }
    }
    public static byte[] getImage2(String name) {
         byte[] image=null;
            Map<String, Object> params = new HashMap<>();
                params = new HashMap<>();
                params.put("userphoto", name);
                String res1 = null;
                try {
                    res1 = RequestUtils.post("/users/yangGetPhoto", params);
                    JSONObject jsonObject1 = new JSONObject(res1);
                    Log.d("byte",res1);
                    if (jsonObject1.getInt("code") == 200) {
                        // 注意获取到的数据的数据类型，在后台是数组，则这里是JSONArray，在后台是类，则这里是JSONObject
                        JSONArray list1 = (JSONArray) jsonObject1.get("data");
                        image = new byte[list1.length()];
                        for (int j = 0; j < list1.length(); j++) {
                            image[j] = (byte) list1.getInt(j);
                        }
                        return image;
                    }
                } catch (Exception e) {
                    Log.d("error", String.valueOf(e));
                }
           return image;
    }
}
