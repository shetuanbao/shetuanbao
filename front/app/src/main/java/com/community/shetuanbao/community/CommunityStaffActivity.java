package com.community.shetuanbao.community;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.community.shetuanbao.R;
import com.community.shetuanbao.utils.F_GetBitmap;
import com.community.shetuanbao.utils.FontManager;
import com.community.shetuanbao.utils.LocalLoader;
import com.community.shetuanbao.utils.NetLoader;
import com.community.shetuanbao.utils.RequestUtils;
import com.community.shetuanbao.utils.User;
import com.community.shetuanbao.utils.usertubiaoStrategy;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class CommunityStaffActivity extends Activity {
    private int userId;
    private String name="";
    private String sex="";
    private String phone="";
    private String email="";
    private String[] total=null;
    private Bitmap touxiang;
    private ImageView pic=null;
    private TextView sno_text=null;
    private EditText name_text=null;
    private EditText sex_text=null;
    private EditText email_text=null;
    private EditText phone_text=null;
    private TextView back=null;
    private Button save=null;
    private String count="";
    private String userpen="";
    private String photo;
    Map<String, Object> params;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.communitystaffactivity);
        Bundle myBundleForGet=this.getIntent().getExtras();
        userId=myBundleForGet.getInt("sno");
        pic=(ImageView)findViewById(R.id.shetuan_user_photo);
        sno_text=(TextView)findViewById(R.id.shetuan_user_sno_edit);
        name_text=(EditText)findViewById(R.id.shetuan_user_name_edit);
        sex_text=(EditText)findViewById(R.id.shetuan_user_sex_edit);
        email_text=(EditText)findViewById(R.id.shetuan_user_e_mail_edit);
        phone_text=(EditText)findViewById(R.id.shetuan_user_phone_edit);
        back=(TextView)findViewById(R.id.go_back);
        save=(Button)findViewById(R.id.save_person);
        FontManager.changeFonts(FontManager.getContentView(this), this);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User.getInstance().addfriend(CommunityStaffActivity.this,userId);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                finish();
            }
        });
        init();
    }
    //好友请求
    public void d()
    {
        new Thread()
        {
            @Override
            public void run()
            {
                try {
                    params = new HashMap<>();
                    User test=User.getInstance().clone();
                    params.put("userId",  test.getUserId());
                    params.put("friendId",userId);
                    String res = RequestUtils.post("/users/pancheckfriendByuserId", params);
                    try {
                        JSONObject jsonObject = new JSONObject(res);
                        if (jsonObject.getString("message").equals("SUCCESS")) {
                            myHandler.sendEmptyMessage(1);

                        }
                        else if(jsonObject.getInt("status")==500){
                            String res1 = RequestUtils.post("/users/panaddfriendByuserId", params);
                            try {
                                JSONObject jsonObject1 = new JSONObject(res1);
                                if (jsonObject1.getInt("code") == 200) {
                                    myHandler.sendEmptyMessage(2);
                                }
                                else {
                                    Looper.prepare();
                                    Toast.makeText(CommunityStaffActivity.this, "获取信息失败", Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        else {
                            Looper.prepare();
                            Toast.makeText(CommunityStaffActivity.this, "获取信息失败", Toast.LENGTH_LONG).show();
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
    //加载用户数据
    public void init()
    {
        new Thread()
        {
            @Override
            public void run()
            {
                try {
                    params = new HashMap<>();
                    params.put("userId", userId);
                    String res = RequestUtils.post("/users/pangetuserByuserId", params);
                    try {
                        JSONObject jsonObject = new JSONObject(res);
                        if (jsonObject.getInt("code") == 200) {
                            // 注意获取到的数据的数据类型，在后台是数组，则这里是JSONArray，在后台是类，则这里是JSONObject
                            JSONObject users=jsonObject.getJSONObject("data");
                            name=users.getString("userName");
                            sex=users.getString("sex");
                            email=users.getString("useremail");
                            phone=users.getString("userphone");
                            photo=users.getString("userphoto");
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
                            Toast.makeText(CommunityStaffActivity.this, "获取信息失败", Toast.LENGTH_LONG).show();
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
            //            //策略模式加模板模式
            if (F_GetBitmap.isEmpty(photo)){
                touxiang= new NetLoader().loadImage(photo,new usertubiaoStrategy());
            }
            else{
                touxiang= new LocalLoader().loadImage(photo,new usertubiaoStrategy());
            }
        }
    }

    public void initAdp()
    {
        Message msg=new Message();
        msg.what=0;
        myHandler.sendMessage(msg);
    }
    Handler myHandler=new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            switch(msg.what)
            {
                case 0:initView();
                    break;
                case 1:
                    Toast.makeText(CommunityStaffActivity.this,"您已添加过对方",Toast.LENGTH_SHORT).show();
                    break;
                case 2:Toast.makeText(CommunityStaffActivity.this,"添加成功", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
    public void initView()
    {
        sno_text.setText(String.valueOf(userId));
        name_text.setText(name);
        if(sex.equals("0")) {
            sex_text.setText("男");
        }
        else{
            sex_text.setText("女");
        }
        email_text.setText(email);
        phone_text.setText(phone);
        pic.setImageBitmap(touxiang);
    }
}