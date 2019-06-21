package com.community.shetuanbao.Login;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.community.shetuanbao.R;
import com.community.shetuanbao.chat.Friend;
import com.community.shetuanbao.utils.Exit;
import com.community.shetuanbao.utils.RequestUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends Activity {

  //定义页面上的控件
  Map<String, Object> params1;//第一次请求的参数
  Map<String, Object> params2;//第二次请求的参数
  public static String registerusername;//注册名
  public static String registerpassword;//注册密码
  public String userId;//用户Id
  private String thisusername;
  private int datauserId;
  private String thispassword;
  private String thispassword1;
  private String thisname;
  private String thise_mail;
  private String thisphone;
  private String thispen;
  private String thissex="男";
  Button ok = null;
  TextView fanhui = null;//放回
  LinearLayout usernamel = null;//用户名layout
  EditText yonghuming = null;//用户名输入框
  LinearLayout passwordl = null;//密码layout
  EditText mima = null;//密码输入框
  LinearLayout passwordl1 = null;//密码layout1
  EditText mima1 = null;//密码输入框1
  LinearLayout namel = null;//名字layout
  EditText mingzi = null;//名字输入框
  LinearLayout e_mail = null;
  EditText youxiang = null;
  LinearLayout phone = null;
  EditText dianhua = null;
  private Spinner sex=null;//选择性别的
  private ArrayAdapter<String> sexAdapter = null;
  private String[]  sexArray={"男","女"};
  private  EditText  sexEd=null;
  private EditText pen=null;
  private EditText xueyuan=null;
  private EditText major=null;
  private String thisxueyuan="";
  private String thismajor="";
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.activity_register);
    Exit.getInstance().addActivities(this);
    pen=(EditText)findViewById(R.id.enrollmain_pen_edit);//签名
    usernamel = (LinearLayout) findViewById(R.id.enrollmain_username);
    yonghuming = (EditText) findViewById(R.id.enrollmain_username_edit);//用户名
    xueyuan=(EditText)findViewById(R.id.enrollmain_xueyuan_edit);//学院
    major=(EditText)findViewById(R.id.enrollmain_major_edit);//专业
    sex=(Spinner)findViewById(R.id.sex);//性别
    sex.setPrompt("请选择性别" );
    setSpinner();
    yonghuming.setOnFocusChangeListener(new View.OnFocusChangeListener() {
      @Override
      public void onFocusChange(View arg0, boolean hasFocus) {
        if (hasFocus) {
          usernamel.setBackgroundResource(R.drawable.roundshapepress);
        } else {
          usernamel.setBackgroundResource(R.drawable.roundshape);
        }
      }
    });
    passwordl =  findViewById(R.id.enrollmain_password);
    mima =  findViewById(R.id.enrollmain_password_edit);//密码1
    mima.setOnFocusChangeListener(new View.OnFocusChangeListener() {
      @Override
      public void onFocusChange(View arg0, boolean hasFocus) {
        if (hasFocus) {
          passwordl.setBackgroundResource(R.drawable.roundshapepress);
        } else {
          passwordl.setBackgroundResource(R.drawable.roundshape);
        }
      }
    });
    passwordl1 = findViewById(R.id.enrollmain_password_2);
    mima1 =  findViewById(R.id.enrollmain_password_2_edit);//密码2
    mima1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
      @Override
      public void onFocusChange(View arg0, boolean hasFocus) {
        if (hasFocus) {
          passwordl1
                  .setBackgroundResource(R.drawable.roundshapepress);
        } else {
          passwordl1.setBackgroundResource(R.drawable.roundshape);
        }
      }
    });
    namel = (LinearLayout) findViewById(R.id.enrollmain_name);
    mingzi = (EditText) findViewById(R.id.enrollmain_name_edit);//昵称
    mingzi.setOnFocusChangeListener(new View.OnFocusChangeListener() {
      @Override
      public void onFocusChange(View arg0, boolean hasFocus) {
        if (hasFocus) {
          namel.setBackgroundResource(R.drawable.roundshapepress);
        } else {
          namel.setBackgroundResource(R.drawable.roundshape);
        }
      }
    });
    e_mail = (LinearLayout) findViewById(R.id.enrollmain_e_mail);
    youxiang = (EditText) findViewById(R.id.enrollmain_e_mail_edit);//邮箱
    youxiang.setOnFocusChangeListener(new View.OnFocusChangeListener() {
      @Override
      public void onFocusChange(View arg0, boolean hasFocus) {
        if (hasFocus) {
          e_mail.setBackgroundResource(R.drawable.roundshapepress);
        } else {
          e_mail.setBackgroundResource(R.drawable.roundshape);
        }
      }
    });
    phone = (LinearLayout) findViewById(R.id.enrollmain_phone);
    dianhua = (EditText) findViewById(R.id.enrollmain_phone_edit);//电话
    dianhua.setOnFocusChangeListener(new View.OnFocusChangeListener() {
      @Override
      public void onFocusChange(View arg0, boolean hasFocus) {
        if (hasFocus) {
          phone.setBackgroundResource(R.drawable.roundshapepress);
        } else {
          phone.setBackgroundResource(R.drawable.roundshape);
        }
      }
    });
    //ok指的是注册按钮，按了号注册后先检查用户名是否重复
    fanhui = findViewById(R.id.enrolltool_text1);
    ok = findViewById(R.id.enrollmain_Button_1);
    ok.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        judgeusernamerepetitionThread();
      }
    });
    //返回按钮的响应
    fanhui.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }
    });
  }
  // 判断用户名重复线程
  public void judgeusernamerepetitionThread() {
    new Thread() {
      @Override
      public void run() {
        userId = yonghuming.getText().toString();//用户账号
        thispassword = mima.getText().toString();//用户密码
        thispassword1 = mima1.getText().toString();//用户密码1
        thisname = mingzi.getText().toString();//用户昵称
        thise_mail = youxiang.getText().toString();//用户邮箱
        thisphone = dianhua.getText().toString();//用户电话
        thispen=pen.getText().toString();//用户签名
        thisxueyuan=xueyuan.getText().toString();//用户学院
        thismajor=major.getText().toString();//用户专业
        //所有信息完整才能响应
        if (!userId.isEmpty() && !thispassword.isEmpty()
                && !thispassword1.isEmpty() && !thisname.isEmpty()
                && !thise_mail.isEmpty() && !thisphone.isEmpty()&&!thispen.isEmpty()&&!thisxueyuan.isEmpty()&&!thismajor.isEmpty()) {
          thread_getCount th=new thread_getCount();
          th.start();
          try{
            th.join();
          }catch(Exception e){
            e.printStackTrace();
          }
          //这里检查用户名个数
          if (datauserId==0)
          {
            if (thispassword.equals(thispassword1)) {
              initMoreInfo(0);
              params2=new HashMap<>();
              params2.put("userId",userId);
              params2.put("userpassword",thispassword);
              params2.put("userName",thisname);
              params2.put("e_mail",thise_mail);
              params2.put("phone",thisphone);
              params2.put("sex",thissex);
              params2.put("pen",thispen);
              params2.put("xueyuan",thisxueyuan);
              params2.put("major",thismajor);
              String res = null;
              try {
                res = RequestUtils.post("/users/register",params2);
              } catch (IOException e) {
                e.printStackTrace();
              }
              Log.d("response:",res);
              try {
                JSONObject jsonObject = new JSONObject(res);
                if (jsonObject.getInt("code") == 200) {
                  Looper.prepare();
                  Toast.makeText(RegisterActivity.this,"注册成功" ,Toast.LENGTH_LONG).show();
                  Intent a=new Intent(RegisterActivity.this,LoginActivity.class);
                }
              } catch (JSONException e) {
                e.printStackTrace();
              }
            } else
            {
              initMoreInfo(3);
            }
          }
          else
          {
            initMoreInfo(1);
          }

        } else
        {
          initMoreInfo(2);
        }

      }
    }.start();
  }
  @SuppressLint("HandlerLeak")
  Handler hd = new Handler() {
    @SuppressWarnings("deprecation")
    @SuppressLint("HandlerLeak")
    @Override
    public void handleMessage(Message msg) {
      switch (msg.what) {
        case 0:
          showDialog(0);
          break;
        case 1:
          showDialog(1);
          break;
        case 2:
          showDialog(2);
          break;
        case 3:
          showDialog(3);
          break;
      }
    }
  };
  public void initMoreInfo(int i) {
    Message msg = new Message();
    msg.what = i;
    hd.sendMessage(msg);
  }
  // 创建对话框
  @Override
  public Dialog onCreateDialog(int id) {
    Dialog dialog = null;
    switch (id) {
      case 0:// 如果id为0，提示注册成功
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setMessage("注册成功");// 显示需要显示的信息
        b.setPositiveButton
                ("取消", new DialogInterface.OnClickListener() {
                  @Override
                  public void onClick(DialogInterface dialog, int which) {
                    finish();
                  }
                });
        dialog = b.create();
        break;
      case 1:// 显示情况是1显示的对话框
        AlertDialog.Builder b1 = new AlertDialog.Builder(this);
        b1.setMessage("用户名重复");
        b1.setPositiveButton
                ("取消", new DialogInterface.OnClickListener() {
                  @Override
                  public void onClick(DialogInterface dialog, int which) {

                  }
                });
        dialog = b1.create();
        break;
      case 2:// 显示情况为2的信息对话框
        AlertDialog.Builder b2 = new AlertDialog.Builder(this);
        b2.setMessage("信息未填写完成");// 锟斤拷锟斤拷锟斤拷息
        b2.setPositiveButton// 为锟皆伙拷锟斤拷锟斤拷锟矫帮拷钮
                ("取消", new DialogInterface.OnClickListener() {
                  @Override
                  public void onClick(DialogInterface dialog, int which) {

                  }
                });
        dialog = b2.create();
        break;
      case 3:// 情况为3的对话框
        AlertDialog.Builder b3 = new AlertDialog.Builder(this);
        b3.setMessage("前后密码不一致");
        b3.setPositiveButton
                ("取消", new DialogInterface.OnClickListener() {
                  @Override
                  public void onClick(DialogInterface dialog, int which) {

                  }
                });
        dialog = b3.create();
        break;
    }
    return dialog;
  }
  @Override
  public boolean onKeyDown(int keyCode, KeyEvent event) {
    if (keyCode == KeyEvent.KEYCODE_BACK) {
      finish();
    }
    return false;
  }
  public void setSpinner()
  {
    sexAdapter = new ArrayAdapter<String>(this,
            android.R.layout.simple_spinner_item, sexArray);
    sexAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    sex.setAdapter(sexAdapter);
    sex.setSelection(0,true);
    sex.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
    {

      @Override
      public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3)
      {
        thissex=sexArray[position];
        sexEd=(EditText)findViewById(R.id.myselfmain_sex_edit);
        sexEd.setText(sexArray[position]);
      }
      @Override
      public void onNothingSelected(AdapterView<?> arg0)
      {
      }
    });
  }
  //用于获取用户状态码来确定用户是否被封禁
  private class thread_getCount extends Thread{
    @Override
    public void run(){
      try{
        params1=new HashMap<>();
        params1.put("userId",Integer.valueOf(userId));
        String res = RequestUtils.post("/users/yangGetIdCount",params1);
        Log.d("response:",res);
        try {
          JSONObject jsonObject = new JSONObject(res);
          if (jsonObject.getInt("code") == 200) {
            // 注意获取到的数据的数据类型，在后台是数组，则这里是JSONArray，在后台是类，则这里是JSONObject
            datauserId=jsonObject.getInt("data");
          } else {
            Looper.prepare();
            Toast.makeText(RegisterActivity.this, "获取信息失败", Toast.LENGTH_LONG).show();
          }
        } catch (JSONException e) {
          e.printStackTrace();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

  }
}