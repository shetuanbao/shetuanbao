package com.community.shetuanbao.utils;

import android.content.Context;
import android.os.Looper;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Loginstate implements Userstate{
    Map<String, Object> params;
    @Override
    public void addfriend(Context context, int id){
        new Thread()
        {
            @Override
            public void run()
            {
                try {
                    params = new HashMap<>();
                    User test=User.getInstance().clone();
                    params.put("userId",  test.getUserId());
//                    params.put("userId",  LoginActivity.sp.getInt("SNO",0));
                    params.put("friendId",id);
                    String res = RequestUtils.post("/users/pancheckfriendByuserId", params);
                    try {
                        JSONObject jsonObject = new JSONObject(res);
                        if (jsonObject.getString("message").equals("SUCCESS")) {
                            Looper.prepare();
                            Toast.makeText(context,"您已添加过对方",Toast.LENGTH_SHORT).show();
                            Looper.loop();
                        }
                        else if(jsonObject.getInt("status")==500){
                            String res1 = RequestUtils.post("/users/panaddfriendByuserId", params);
                            try {
                                JSONObject jsonObject1 = new JSONObject(res1);
                                if (jsonObject1.getInt("code") == 200) {
                                    Looper.prepare();
                                    Toast.makeText(context,"添加成功", Toast.LENGTH_SHORT).show();
                                    Looper.loop();
                                }
                                else {
                                    Looper.prepare();
                                    Toast.makeText(context, "获取信息失败", Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        else {
                            Looper.prepare();
                            Toast.makeText(context, "获取信息失败", Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}