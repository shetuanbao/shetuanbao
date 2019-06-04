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
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;

public class GetActivityInfo {
    public static String[] activityName;//活动名
    public static String[] activityIntroduce;//活动介绍
    public static String[] activityPicture;//活动照片名
    public static Integer[] activityId;//活动ID
    public static  Integer[] activityLei;//活动类型
    public static  String[] time;//活动时间
    public static String[] palce;//活动地点
    public static String[] album;
    public static int a;
//    public static byte[][] all_image;

    private static OkHttpClient client = new OkHttpClient();
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void GetActivities() throws IOException {
        JSONArray list = null;
      String res=RequestUtils.get("/activities");//得到所有的活动
        Log.d("response", res);
        try {
            JSONObject jsonObject = new JSONObject(res);
            if (jsonObject.getInt("code") == 200) {
                // 如何code==200,说明连接数据库成功并成功取得数据
                list = (JSONArray) jsonObject.get("data");
                a = list.length();
                activityIntroduce = new String[a];
                activityName = new String[a];
                activityPicture = new String[a];
                activityLei = new Integer[a];
                activityId = new Integer[a];
                time=new String[a];
                palce=new String[a];
                for (int i = 0; i < a; i++) {
                    activityPicture[i] = (list.getJSONObject(i).getString("activityPicture"));
                    activityName[i] = (list.getJSONObject(i).getString("activityTitle"));
                    activityIntroduce[i] = (list.getJSONObject(i).getString("activityIntroduce"));
                    activityId[i] = (list.getJSONObject(i).getInt("activityId"));
                    activityLei[i]=(list.getJSONObject(i).getInt("leixing"));
                    time[i]=(list.getJSONObject(i).getString("activityTime"));
                    palce[i]=(list.getJSONObject(i).getString("activityPlace"));
                }


            } else {
                Looper.prepare();
                Log.d("消息", "连接失败");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    public static void shanchuhaoyou(String id) {

    }

    public static byte[] getImage2(String name) {
        byte[] image=null;
        Map<String, Object> params = new HashMap<>();
        params = new HashMap<>();
        params.put("picture", name);
        String res1 = null;
        try {
            res1 = RequestUtils.post("/activities/panfindpicture", params);
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

    public static String[] getAlbum(int id) {
        byte[] image=null;
        Map<String, Object> params = new HashMap<>();
        params = new HashMap<>();
        params.put("activityId", id);
        String res1 = null;
        try {
            res1 = RequestUtils.post("/activities/yangGetAlbum", params);
            JSONObject jsonObject1 = new JSONObject(res1);
            Log.d("byte",res1);
            if (jsonObject1.getInt("code") == 200) {
                // 注意获取到的数据的数据类型，在后台是数组，则这里是JSONArray，在后台是类，则这里是JSONObject
                JSONArray list1 = (JSONArray) jsonObject1.get("data");
                album = new String[list1.length()];
                for (int j = 0; j < list1.length(); j++) {
                    album[j] = list1.getJSONObject(j).toString();
                }
                return album;
            }
        } catch (Exception e) {
            Log.d("error", String.valueOf(e));
        }
        return album;
    }
}
