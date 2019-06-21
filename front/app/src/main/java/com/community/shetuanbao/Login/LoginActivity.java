package com.community.shetuanbao.Login;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.community.shetuanbao.R;
import com.community.shetuanbao.utils.Exit;
import com.community.shetuanbao.utils.RequestUtils;
import com.community.shetuanbao.utils.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends Activity implements View.OnClickListener {
  public static SharedPreferences sp;
  private Button sign=null;
  private TextView register=null;
  private EditText username=null;
  private EditText password=null;
  private String zh_pw[]=null;
  private  String count="";
  private String name="";
  private String userpassword="";
  private  int SUCCESS = 0;
  private  int FAIL =1 ;
  public static ProgressDialog pd;
  String zhuangtai=null;
  String zhuangtai2=null;
  String userNameValue=null;
  String passwordValue=null;
  String ss=null;
  Map<String, Object> params;
  @Override
  protected void onCreate(Bundle savedInstanceState)
  {

    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.loginactivity);
    Exit.getInstance().addActivities(this);
    username=(EditText)findViewById(R.id.app_username_et);
    password=(EditText)findViewById(R.id.app_password_et);
    register=(TextView)findViewById(R.id.de_login_register);
    sign=(Button)findViewById(R.id.app_sign_in_bt);
    sp=this.getSharedPreferences("userInfo",MODE_PRIVATE);
    sign.setOnClickListener(this);
    register.setOnClickListener(this);
    pd = new ProgressDialog(this);
    pd.setMax(100);
    pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    pd.setCancelable(false);
    pd.setMessage("加载中，请稍后........");
    //从手机内存中取出前一次登录的用户数据
    if(sp.getBoolean("CHECK", false))
    {
      username.setText(String.valueOf(sp.getInt("SNO", 0)));
      password.setText(sp.getString("PASSWORD", ""));
      //开启新线程来检测用户状态
      thread_get2 th=new thread_get2();
      th.start();
      try{
        //join()等待子线程结束主线程才开始运行
        th.join();
      }catch(Exception e){
        e.printStackTrace();
      }
      if(zhuangtai2.equals("1")){
        User.getInstance().init(sp.getString("USER_NAME",""),sp.getString("PASSWORD",""),sp.getInt("SNO", 0),sp.getString("userphone",""),sp.getString("sex",""),sp.getString("userpen",""),sp.getString("userphoto",""),sp.getString("status",""),sp.getString("major",""),sp.getString("xueyuan",""));
        Intent in=new Intent(LoginActivity.this,MainFrame.class);
        startActivity(in);
      }
      else if(zhuangtai2.equals("0")){
        Toast.makeText(LoginActivity.this, "账号被封，不能登录!!", Toast.LENGTH_SHORT).show();
      }

    }

  }
  Handler mHandler=new Handler()
  {
    @Override
    public void handleMessage(Message msg)
    {
      switch(msg.what)
      {
        case 0:
          Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
          break;
        case 1:
          Toast.makeText(LoginActivity.this, "账号或密码错误", Toast.LENGTH_SHORT).show();
          break;
        case 3:
          pd.show();
          break;
      }
    }
  };
  @Override
  public void onClick(View v)
  {
    // TODO Auto-generated method stub
    switch(v.getId())
    {
      case R.id.de_login_register:
        Intent ii=new Intent(LoginActivity.this,RegisterActivity.class);
        startActivity(ii);
        break;
      case R.id.app_sign_in_bt:
        userNameValue=username.getText().toString();
        passwordValue=password.getText().toString();
        if(userNameValue.equals("")||passwordValue.equals(""))
        {
          Toast.makeText(LoginActivity.this,"账号或密码不能为空", Toast.LENGTH_SHORT).show();
        }
        else
        {
          //用户登录
          thread_get th=new thread_get();
          th.start();
          try{
            th.join();
          }catch(Exception e){
            e.printStackTrace();
          }
          if(zhuangtai.equals("1")){
            login(Integer.valueOf(userNameValue),passwordValue);
          }
          else {
            Toast.makeText(LoginActivity.this, "账号被封，不能登录!!", Toast.LENGTH_SHORT).show();
          }
        }
        break;
    }
  }
  void login(final int userNameValue, final String passwordValue)
  {
    mHandler.sendEmptyMessage(3);
    pd.show();
    new Thread(new Runnable()
    {
      @Override
      public void run()
      {
        try{
          params=new HashMap<>();
          params.put("userId",userNameValue);
          params.put("password",passwordValue);
          Log.d("Login123123",passwordValue);
          String res = RequestUtils.post("/users/login",params);
          try {
            JSONObject jsonObject = new JSONObject(res);
            if (jsonObject.getInt("code") == 200) {
              // 注意获取到的数据的数据类型，在后台是数组，则这里是JSONArray，在后台是类，则这里是JSONObject
              JSONObject user=jsonObject.getJSONObject("data");
              SharedPreferences.Editor editor = sp.edit();
              editor.putString("USER_NAME", user.getString("userName"));
              editor.putString("PASSWORD",passwordValue);
              editor.putInt("SNO",user.getInt("userId"));
              editor.putString("userphone",user.getString("userphone"));
              editor.putString("sex",user.getString("sex"));
              editor.putString("userpen",user.getString("userpen"));
              editor.putString("userphoto",user.getString("userphoto"));
              editor.putString("status",user.getString("status"));
              editor.putString("major",user.getString("major"));
              editor.putString("xueyuan",user.getString("xueyuan"));
              editor.putString("Token", name+"#"+String.valueOf(user.getInt("userId"))+"#"+"file:///"+ Environment.getExternalStorageDirectory().toString()+"/download_test/"+user.getString("userphoto"));
              editor.putBoolean("CHECK", true);
              editor.putBoolean("zhuangtai", true);
              editor.commit();
              User.getInstance().init(user.getString("userName"),passwordValue,user.getInt("userId"),user.getString("userphone"),user.getString("sex"),user.getString("userpen"),user.getString("userphoto"),user.getString("status"),user.getString("major"),user.getString("xueyuan"));
              mHandler.sendEmptyMessage(SUCCESS);
              Intent ii=new Intent(LoginActivity.this,MainFrame.class);
              startActivity(ii);
              try {
                Thread.sleep(1000);
              } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
              }
              pd.dismiss();
            }
            else if(jsonObject.getInt("code") == 400){
              mHandler.sendEmptyMessage(FAIL);
              pd.dismiss();
            }
            else {
              Looper.prepare();
              Toast.makeText(LoginActivity.this, "获取信息失败", Toast.LENGTH_LONG).show();
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
  //用于获取用户状态码来确定用户是否被封禁
  private class thread_get extends Thread{
    @Override
    public void run(){
      try{
        params=new HashMap<>();
        params.put("userId",Integer.valueOf(userNameValue));
        String res = RequestUtils.post("/users/pangetstatusByuserId",params);
        try {
          JSONObject jsonObject = new JSONObject(res);
          if (jsonObject.getInt("code") == 200) {
            // 注意获取到的数据的数据类型，在后台是数组，则这里是JSONArray，在后台是类，则这里是JSONObject
            zhuangtai=jsonObject.getString("data");
          } else {
            Looper.prepare();
            Toast.makeText(LoginActivity.this, "获取信息失败", Toast.LENGTH_LONG).show();
          }
        } catch (JSONException e) {
          e.printStackTrace();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

  }
  //用于获取用户状态码来确定用户是否被封禁
  private class thread_get2 extends Thread{
    @Override
    public void run(){
      try{
        params=new HashMap<>();
        params.put("userId",Integer.valueOf(username.getText().toString()));
        String res = RequestUtils.post("/users/pangetstatusByuserId",params);
        try {
          JSONObject jsonObject = new JSONObject(res);
          if (jsonObject.getInt("code") == 200) {
            // 注意获取到的数据的数据类型，在后台是数组，则这里是JSONArray，在后台是类，则这里是JSONObject
            zhuangtai2=jsonObject.getString("data");
          } else {
            Looper.prepare();
            Toast.makeText(LoginActivity.this, "获取信息失败", Toast.LENGTH_LONG).show();
          }
        } catch (JSONException e) {
          e.printStackTrace();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

  }
  //用于监控手机上按键操作
  @Override
  public boolean onKeyDown(int keyCode, KeyEvent event)      //��д���ؼ�
  {
    if(keyCode == KeyEvent.KEYCODE_BACK)
    {
      Exit.exitActivity();
    }
    return true;
  }
}