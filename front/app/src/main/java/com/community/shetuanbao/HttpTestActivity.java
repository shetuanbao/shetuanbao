package com.community.shetuanbao;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.community.shetuanbao.Activities.ActivityInfoActivity;
import com.community.shetuanbao.utils.RequestUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HttpTestActivity extends Activity implements View.OnClickListener {

    private EditText userNameEt;
    private EditText passwordEt;
    private Button loginBtn;

    private String userName;
    private String password;
    Map<String, Object> params;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http_test);

        userNameEt = (EditText) findViewById(R.id.userNameTv);
        passwordEt = (EditText) findViewById(R.id.passwordTv);
        loginBtn = (Button) findViewById(R.id.loginBtn);

        loginBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.loginBtn:
                userName = userNameEt.getText().toString();
                password = passwordEt.getText().toString();
                params = new HashMap<>();
                params.put("userName", userName);
                params.put("password", password);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String res = RequestUtils.post("/users/login", params);
                            Log.d("response", res);

                            // dosomething with res
                            try {
                                JSONObject jsonObject = new JSONObject(res);
                                if (jsonObject.getInt("code") == 200) {
                                    // 后台返回成功结果
                                    //Toast里面启动了一个handler，所以要加Looper
                                    Looper.prepare();
                                    Toast.makeText(HttpTestActivity.this,"登陆成功", Toast.LENGTH_LONG).show();
//                                    Looper.loop();
                                    Intent intent = new Intent(HttpTestActivity.this, ActivityInfoActivity.class);
                                    startActivity(intent);
                                } else {
                                    // 后台返回失败结果
                                    Looper.prepare();
                                    Toast.makeText(HttpTestActivity.this,"登陆失败", Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                break;
        }
    }
}
