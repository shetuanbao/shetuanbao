package com.community.shetuanbao.chat;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import io.rong.imkit.RongIM;

import com.community.shetuanbao.Login.LoginActivity;
import com.community.shetuanbao.Login.MainFrame;
import com.community.shetuanbao.R;
import com.community.shetuanbao.utils.F_GetBitmap;
import com.community.shetuanbao.utils.FontManager;
import com.community.shetuanbao.utils.GetFriendInfo;
import com.community.shetuanbao.utils.RequestUtils;
//import com.community.shetuanbao.utils.RequestUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

import io.rong.imkit.RongIM;

public class lianxiren_message extends Activity implements View.OnClickListener{

  private ImageView photo=null;//头像
  private TextView zhanghao=null;//账号
  private TextView xingming=null;//姓名
  private TextView sex=null;//性别
  private TextView phone=null;//电话
  private Button send=null;//发送按键
  private Integer userId;//用户id
  private Integer[] total=null;
  private String xingming2="";
  private String sex2="";
  private String phone2="";
  private String thisxueyuan=" ";
  private String thismajor=" ";
  private String thisemail=" ";
  private String thispen=" ";
  private String touxiang;
  private Bitmap touxiang2;
  private Intent intent;
  private byte[] bitmapByte;
  private TextView back=null;
  public TextView shanchu=null;
  private EditText xueyuan=null;
  private EditText major=null;
  private EditText email=null;
  private EditText pen=null;
  private Integer currentId;
  Map<String, Object> params;
  Integer id;
  int a;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.activity_lianxiren_message);
    currentId=LoginActivity.sp.getInt("SNO",0);
    Bundle myBundleForGet=this.getIntent().getExtras();
    userId=myBundleForGet.getInt("sno");
    FontManager.changeFonts(FontManager.getContentView(this), this);
    photo=(ImageView)findViewById(R.id.userfriend_photo_EditView);
    zhanghao=(TextView)findViewById(R.id.userfriend_zhanghao_EditView);
    xingming=(TextView)findViewById(R.id.userfriend_name_EditView);
    sex=(TextView)findViewById(R.id.userfriend_sex_EditView);
    phone=(TextView)findViewById(R.id.userfriend_phone_EditView);
    xueyuan=(EditText)findViewById(R.id.myselfmain_xueyuan_edit);
    major=(EditText)findViewById(R.id.myselfmain_major_edit);
    email=(EditText)findViewById(R.id.myselfmain_e_mail_edit);
    pen=(EditText)findViewById(R.id.myselfmain_pen_edit);
    back=(TextView)findViewById(R.id.myselftoffol_text1);
    //back.setTypeface(FontManager.tf);
    back.setOnClickListener(new View.OnClickListener() {

      @Override
      public void onClick(View v) {
        // TODO Auto-generated method stub
        finish();
      }
    });
    shanchu=(TextView)findViewById(R.id.faxiaosssxi);
    shanchu.setOnClickListener(new View.OnClickListener() {

      @Override
      public void onClick(View v) {
        // TODO Auto-generated method stub
        shanchu(currentId,userId);
        Intent it=new Intent(lianxiren_message.this,MainFrame.class);
        shejiao_lianxiren.list.remove(shejiao_lianxiren.x);
        shejiao_lianxiren.xuehao.remove(shejiao_lianxiren.x);
        startActivity(it);
      }
    });
    send=(Button)findViewById(R.id.faxiaoxi);
    send.setOnClickListener(this);
    init();

  }
  @Override
  public void onClick(View v)
  {
    switch(v.getId())
    {
      case R.id.faxiaoxi:
        if (RongIM.getInstance() != null)
          RongIM.getInstance().startPrivateChat(this, userId.toString(),"标题");
        break;
      case R.id.myselftool_text1:
        this.finish();
        break;
    }
  }
  Handler myHandler=new Handler()
  {
    @Override
    public void handleMessage(Message msg)
    {
      switch(msg.what)
      {
        case 0:
          initView();
          break;
//                case 1:
//                    Toast.makeText(lianxiren_message.this,"删除成功", Toast.LENGTH_SHORT).show();
//                    break;
      }
    }
  };
  public void initView()
  {
    photo.setImageBitmap(touxiang2);
    zhanghao.setText(id.toString());
    xingming.setText(xingming2);
    sex.setText(sex2);
    phone.setText(phone2);
    xueyuan.setText(thisxueyuan);
    major.setText(thismajor);
    email.setText(thisemail);
    pen.setText(thispen);

  }
  public void shanchu(Integer u, Integer s)
  {
    new Thread()
    {
      @Override
      public void run()
      {
        try {
          params = new HashMap<>();
          params.put("userId", u);
          params.put("friendId",s);
          String res = RequestUtils.post("/users/yangDeleteUserById", params);
          JSONObject jsonObject = new JSONObject(res);
          if (jsonObject.getInt("code") == 200) {
            Looper.prepare();
            Toast.makeText(lianxiren_message.this,"删除成功", Toast.LENGTH_SHORT).show();
          }
          else {
            Looper.prepare();
            Toast.makeText(lianxiren_message.this, "删除失败", Toast.LENGTH_LONG).show();
          }
        } catch (IOException e) {
          e.printStackTrace();
        } catch (JSONException e) {
          e.printStackTrace();
        }
      }
    }.start();
  }
  public void init()
  {
    new Thread()
    {
      @TargetApi(Build.VERSION_CODES.KITKAT)
      @RequiresApi(api = Build.VERSION_CODES.KITKAT)
      @Override
      public void run()
      {
        try{
          params = new HashMap<>();
          params.put("userId", userId);
          String res = RequestUtils.post("/users/pangetuserByuserId", params);
          try {
            JSONObject jsonObject = new JSONObject(res);
            if (jsonObject.getInt("code") == 200) {
              // 注意获取到的数据的数据类型，在后台是数组，则这里是JSONArray，在后台是类，则这里是JSONObject
              JSONObject users=jsonObject.getJSONObject("data");
              id=users.getInt("userId");
              xingming2=users.getString("userName");
              sex2=users.getString("sex");
              thisemail=users.getString("useremail");
              phone2=users.getString("userphone");
              touxiang=users.getString("userphoto");
              thismajor=users.getString("major");
              thispen=users.getString("userpen");
              thisxueyuan= users.getString("xueyuan");
              //加载头像的线程
              Thread_pic thread_pic=new Thread_pic();
              thread_pic.start();
              try {
                thread_pic.join();
              } catch (Exception e) {
                e.printStackTrace();
              }
            } else {
              Looper.prepare();
              Toast.makeText(lianxiren_message.this, "获取信息失败", Toast.LENGTH_LONG).show();
            }
          } catch (JSONException e) {
            e.printStackTrace();
          }
        }
        catch (IOException e) {
          e.printStackTrace();
        }
        initAdp();
      }
    }.start();
  }
  public class Thread_pic extends Thread{
    @Override
    public void run(){
      try {
        if(F_GetBitmap.isEmpty(touxiang))
        {
          params = new HashMap<>();
          params.put("photo", touxiang);
          String res1 = RequestUtils.post("/users/panfindphoto", params);
          try {
            JSONObject jsonObject1 = new JSONObject(res1);
            if (jsonObject1.getInt("code") == 200) {
              // 注意获取到的数据的数据类型，在后台是数组，则这里是JSONArray，在后台是类，则这里是JSONObject
              JSONArray list1 = (JSONArray) jsonObject1.get("data");
              byte[] b = new byte[list1.length()];
              for (int j = 0; j < list1.length(); j++) {
                b[j] = (byte) list1.getInt(j);
              }
              F_GetBitmap.setInSDBitmap(b, touxiang);
              InputStream input = null;
              BitmapFactory.Options options = new BitmapFactory.Options();
              options.inSampleSize = 1;
              input = new ByteArrayInputStream(b);
              @SuppressWarnings({ "rawtypes", "unchecked" })
              SoftReference softRef = new SoftReference(BitmapFactory.decodeStream(input, null, options));
              touxiang2 = (Bitmap) softRef.get();
            } else {
              Looper.prepare();
              Toast.makeText(lianxiren_message.this, "获取信息失败", Toast.LENGTH_LONG).show();
            }
          } catch (JSONException e) {
            e.printStackTrace();
          }
        }
        else
        {
          touxiang2=F_GetBitmap.getSDBitmap(touxiang);
          if(F_GetBitmap.bitmap!=null && !F_GetBitmap.bitmap.isRecycled())
          {
            F_GetBitmap.bitmap = null;
          }
        }

      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
  public void initAdp()
  {
    Message msg=new Message();
    msg.what=0;
    myHandler.sendMessage(msg);
  }

  @Override
  public boolean onKeyDown(int keyCode, KeyEvent event)
  {
    if(keyCode == KeyEvent.KEYCODE_BACK)
    {
      this.finish();
    }
    return false;
  }

}
