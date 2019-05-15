package com.community.shetuanbao.chat;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.community.shetuanbao.Login.MainFrame;
import com.community.shetuanbao.R;
import com.community.shetuanbao.utils.FontManager;
//import com.community.shetuanbao.utils.RequestUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import io.rong.imkit.RongIM;

public class lianxiren_message extends Activity implements View.OnClickListener{

    String ss="";
    private ImageView photo=null;
    private TextView zhanghao=null;//账号
    private TextView xingming=null;//姓名
    private TextView sex=null;//性别
    private TextView phone=null;//电话
    private Button send=null;
    private String userId="";
    private String[] total=null;
    private String xingming2="";
    private String sex2="";
    private String phone2="";
    private String thisxueyuan=" ";
    private String thismajor=" ";
    private String thisemail=" ";
    private String thispen=" ";
    private Bitmap touxiang;
    private Intent intent;
    private byte[] bitmapByte;
    private TextView back=null;
    public TextView shanchu=null;
    private EditText xueyuan=null;
    private EditText major=null;
    private EditText email=null;
    private EditText pen=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_lianxiren_message);
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
        back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                finish();
            }
        });
        shanchu=findViewById(R.id.faxiaosssxi);
        shanchu.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String userName="";
                // TODO Auto-generated method stub
                ss=userName+"#"+userId;
                shanchu(ss);
                Intent it=new Intent(lianxiren_message.this,MainFrame.class);
                shejiao_lianxiren.list.remove(shejiao_lianxiren.x);
                shejiao_lianxiren.ba.notifyDataSetChanged();
                shejiao_lianxiren.xuehao.remove(shejiao_lianxiren.x);
                startActivity(it);
            }
        });
        send=findViewById(R.id.faxiaoxi);//发消息
        Bundle myBundleForGet=this.getIntent().getExtras();
        userId=myBundleForGet.getString("sno");
//        send.setOnClickListener(this);
        init();

    }
    @Override
    public void onClick(View v)
    {
        switch(v.getId())
        {
            case R.id.faxiaoxi:if (RongIM.getInstance() != null)
               this.finish();
                RongIM.getInstance().startPrivateChat(this,userId,"");
               break;
            case R.id.myselftool_text2:
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
                case 0:initView();
                    break;
            }
        }
    };
    public void initView()
    {
        xingming.setText(xingming2);
        sex.setText(sex2);
        phone.setText(phone2);
        zhanghao.setText(userId);
        photo.setImageBitmap(touxiang);
        xueyuan.setText(thisxueyuan);
        major.setText(thismajor);
        email.setText(thisemail);
        pen.setText(thispen);
    }
    public void shanchu(final String s)
    {
        new Thread()
        {
            @Override
            public void run()
            {
                //根据好友的id来删除好友
//                GetFriendInfo.shanchuhaoyou(s);
            }
        }.start();
    }
    public void init()
    {
        new Thread()
        {
            @Override
            public void run()
            {
                String res = null;
//                try {
////                    res = RequestUtils.get("/friends/list");//捕捉是否成功连接上后端
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
                try {
                    JSONObject jsonObject = new JSONObject(res);
                    if (jsonObject.getInt("code") == 200) {
                        // 如果响应码是200说明连接成功
                        JSONArray list = (JSONArray) jsonObject.get("data");
                        String listStr = "";
                        String photo="";
                        String xingming="";
                        String pen="";
                        String sex="";
                        String phone="";
                        String emial="";
                        String xueyuan="";
                        String major="";

                        for (int i = 0; i < list.length(); i++) {
//                                listStr += list.get(i).get("activityId") + "\n";
//                            listStr += list.getJSONObject(i).getInt("activityId") + "\n";
                            photo+=list.getJSONObject(i).getString("phonto")+"\n";

                        }
//                        activityInfoTv.setText(listStr);
                    } else {
                        Looper.prepare();
                        Toast.makeText(lianxiren_message.this, "访问失败", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


//                total=NetInfoUtil.getuserfriend(userId);//得到朋友的信息
//                System.out.println(total.length+"nasnasd");
//
//                String photo=NetInfoUtil.getuseronephoto(userId);//得到朋友的头像
//                if(F_GetBitmap.isEmpty(photo))
//                {
//                    byte[] b=NetInfoUtil.getPicture(photo);
//                    F_GetBitmap.setInSDBitmap(b, photo);
//                    InputStream input = null;
//                    BitmapFactory.Options options = new BitmapFactory.Options();
//                    options.inSampleSize = 1;
//                    input = new ByteArrayInputStream(b);
//                    @SuppressWarnings({ "rawtypes", "unchecked" })
//                    SoftReference softRef = new SoftReference(BitmapFactory.decodeStream(
//                            input, null, options));
//                    touxiang =(Bitmap) softRef.get();
//                }
//                else
//                {

//                    touxiang=F_GetBitmap.getSDBitmap(photo);
//                    if(F_GetBitmap.bitmap!=null && !F_GetBitmap.bitmap.isRecycled())
//                    {
//                        F_GetBitmap.bitmap = null;
//                    }
//                }
//                xingming2=total[0];
//                sex2=total[1];
//                phone2=total[2];
//                thisxueyuan=total[3];
//                thismajor=total[4];
//                thisemail=total[5];
//                thispen=total[6];
                initAdp();
            }
        }.start();
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
