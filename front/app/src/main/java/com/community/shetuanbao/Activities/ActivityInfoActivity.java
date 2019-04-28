package com.community.shetuanbao.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.os.Looper;
import android.widget.TextView;
import android.widget.Toast;

import com.community.shetuanbao.R;
import com.community.shetuanbao.utils.RequestUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ActivityInfoActivity extends Activity {

    private TextView activityInfoTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        activityInfoTv = (TextView) findViewById(R.id.activity_info_tv);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String res = RequestUtils.get("/activities/list");
                    try {
                        JSONObject jsonObject = new JSONObject(res);
                        if (jsonObject.getInt("code") == 200) {
                            // 注意获取到的数据的数据类型，在后台是数组，则这里是JSONArray，在后台是类，则这里是JSONObject
                            JSONArray list = (JSONArray) jsonObject.get("data");
                            String listStr = "";
                            for (int i = 0; i < list.length(); i++) {
//                                listStr += list.get(i).get("activityId") + "\n";
                                listStr += list.getJSONObject(i).getInt("activityId") + "\n";
                            }
                            activityInfoTv.setText(listStr);
                        } else {
                            Looper.prepare();
                            Toast.makeText(ActivityInfoActivity.this, "获取活动信息失败", Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
