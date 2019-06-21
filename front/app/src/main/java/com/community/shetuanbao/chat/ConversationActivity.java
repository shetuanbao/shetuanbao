package com.community.shetuanbao.chat;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.community.shetuanbao.R;
import com.community.shetuanbao.utils.Exit;
import com.community.shetuanbao.utils.FontManager;
import com.community.shetuanbao.utils.GetFriendInfo;
import com.community.shetuanbao.utils.RequestUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.Conversation;

public class ConversationActivity extends FragmentActivity {
  private TextView title;//对话活动题目
  private String sName;
  private String sId;
  private TextView back;
  int count=0;
  TextView baocun;
  Bitmap touxiang=null;//头像
  private  String pen="";
  Map<String, Object> params=new HashMap<>();
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.activity_conversation);

    title=findViewById(R.id.liaotian_name);
    back=findViewById(R.id.back_conversition);
    baocun=findViewById(R.id.baocun);
    back.setTypeface(FontManager.tf);
    Exit.getInstance().addActivities(this);
    sId=getIntent().getData().getQueryParameter("targetId");
    back.setOnClickListener(new View.OnClickListener() {

      @Override
      public void onClick(View v) {
        // TODO Auto-generated method stub
        finish();
      }
    });
    init();
    baocun.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        // TODO Auto-generated method stub
        init2();
      }
    });
    initUnreadCountListener();
  }
  public void init()
  {//从数据库中根据用户id获取用户的名字
    //然后得到用户的朋友个数
    //如果朋友为0则发用朋友为空的对话框
    new Thread()
    {
      @RequiresApi(api = Build.VERSION_CODES.KITKAT)
      @Override
      public void run()
      {
        String userName="许";
        count=GetFriendInfo.a;

        if(count==0){
          mHandler.sendEmptyMessage(1);
        }
        initTitle();
      }

    }.start();
  }
  public void init2()
  {//点击保存之后，用户插入新的朋友
    String userId="111";
    String friendId="1111";
    params.put("userName", userId);
    params.put("password", friendId);
    new Thread(new Runnable() {
      @Override
      public void run() {
        try {
          String res = RequestUtils.post("/friends/Add", params);
          Log.d("response", res);
          try {
            JSONObject jsonObject = new JSONObject(res);
            if (jsonObject.getInt("code") == 200) {
              Looper.prepare();
            } else {
              // 后台返回失败结果
              Looper.prepare();
              Toast.makeText(ConversationActivity.this,"连接服务器失败", Toast.LENGTH_LONG).show();
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
  public void initTitle()
  {
    Message msg=new Message();
    msg.what=0;
    mHandler.sendMessage(msg);
  }
  Handler mHandler=new Handler()
  {
    @Override
    public void handleMessage(Message msg)
    {
      switch(msg.what)
      {
        case 0:title.setText(sName);
          title.setTypeface(FontManager.tf);
          break;
        case 1:baocun.setVisibility(View.VISIBLE);
          break;

        case 2:
          baocun.setVisibility(View.GONE);
          Map<String,Object> map=new HashMap<String,Object>();
          map.put("name", sName);
          map.put("pen", pen);
          map.put("photo", touxiang);
          shejiao_lianxiren.list.add(map);
          break;
      }
    }
  };
  /**
   *
   * 融云未读消息监听
   */
  private void initUnreadCountListener() {
    final Conversation.ConversationType[] conversationTypes = {Conversation.ConversationType.PRIVATE, Conversation.ConversationType.DISCUSSION,
            Conversation.ConversationType.GROUP, Conversation.ConversationType.SYSTEM,
            Conversation.ConversationType.PUBLIC_SERVICE};

    Handler handler = new Handler();
    handler.postDelayed(new Runnable() {
      @Override
      public void run() {
        RongIM.getInstance().setOnReceiveUnreadCountChangedListener(mCountListener, conversationTypes);
      }
    }, 500);
  }
  public RongIM.OnReceiveUnreadCountChangedListener mCountListener = new RongIM.OnReceiveUnreadCountChangedListener() {
    @Override
    public void onMessageIncreased(int count) {
      Log.e("tag","count:" + count);
      if (count == 0) {
      }
      else if (count > 0 && count < 100) {
      }
      else {
      }
    }
  };
}