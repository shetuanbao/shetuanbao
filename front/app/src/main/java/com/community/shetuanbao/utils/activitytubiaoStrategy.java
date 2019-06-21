package com.community.shetuanbao.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class activitytubiaoStrategy implements ImageStrategy {
    Map<String, Object> params;
    @Override
    public byte[] getImagebyte(String image){
        try {
            params = new HashMap<>();
            params.put("picture", image);
            String res1 = RequestUtils.post("/activities/panfindpicture", params);
            try {
                JSONObject jsonObject1 = new JSONObject(res1);
                if (jsonObject1.getInt("code") == 200) {
                    // 注意获取到的数据的数据类型，在后台是数组，则这里是JSONArray，在后台是类，则这里是JSONObject
                    JSONArray list1 = (JSONArray) jsonObject1.get("data");
                    byte[] all_image = new byte[list1.length()];
                    for (int j = 0; j < list1.length(); j++) {
                        all_image[j] = (byte) list1.getInt(j);
                    }
                    return all_image;
                } else {
                    return null;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}