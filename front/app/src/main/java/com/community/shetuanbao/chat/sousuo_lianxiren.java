package com.community.shetuanbao.chat;

import android.app.Activity;
import android.content.Intent;
import android.os.Looper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.community.shetuanbao.R;
import com.community.shetuanbao.community.CommunityStaffActivity;
import com.community.shetuanbao.utils.Exit;
import com.community.shetuanbao.utils.RequestUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class sousuo_lianxiren extends Activity {

  public ImageView fanhui=null;//返回
  AutoCompleteTextView act=null;//自动补全文本
  int s;
  Map<String, Object> params=new HashMap<>();
  ImageView sousuo=null;//搜索框
  TextView tishi=null;//提示信息
  int[] sno;
  public boolean bool=true;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.lianxiren_sousuo);
    Exit.getInstance().addActivities(this);
    fanhui=findViewById(R.id.historytssool_t1);
    fanhui.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        // TODO Auto-generated method stub
        Intent openCameraIntent = new Intent(sousuo_lianxiren.this,CaptureActivity.class);
        startActivityForResult(openCameraIntent, 0);
      }
    });
    act=findViewById(R.id.titlSOUSUOREN);
    tishi=findViewById(R.id.texcct1);
    thread_sousuo gg=new thread_sousuo();
    gg.start();
    try{
      gg.join();
    }catch(Exception e){
      e.printStackTrace();
    }
    sousuo=findViewById(R.id.mn_searddchren_image);
    sousuo.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        // TODO Auto-generated method stub
        s=Integer.parseInt(act.getText().toString());
        for(int i=0;i<sno.length;i++)
        {
          if(s==sno[i])
            bool=false;
        }
        if(bool)
        {
          System.out.println("1234556");
          tishi.setText("请输入搜索的内容!");
          act.setText("");
        }else
        {
          bool=true;
          Intent intent=new Intent(sousuo_lianxiren.this,CommunityStaffActivity.class);
          Bundle bundle=new Bundle();
          bundle.putInt("sno", s);
          intent.putExtras(bundle);
          tishi.setText("");
          startActivity(intent);
        }
        act.setText("");
      }
    });
  }
  private class thread_sousuo extends Thread {
    @Override
    public void run() {
      String res ;
      params=new HashMap<>();
      try {
        res=RequestUtils.get("/users/list");
        Log.d("response", res);
        JSONObject jsonObject = new JSONObject(res);
        if (jsonObject.getInt("code") == 200) {
          JSONArray list = (JSONArray) jsonObject.get("data");
          Log.d("length:", String.valueOf(list.length()));
          sno = new int[list.length()];
          for (int i = 0; i < list.length(); i++) {
            sno[i]=list.getJSONObject(i).getInt("userId");
          }
          Looper.prepare();
          Toast.makeText(sousuo_lianxiren.this,"访问成功", Toast.LENGTH_LONG).show();

        } else {
          Looper.prepare();
          Toast.makeText(sousuo_lianxiren.this,"连接服务器失败", Toast.LENGTH_LONG).show();
        }
      } catch (IOException e) {
        e.printStackTrace();
      } catch (JSONException e) {
        e.printStackTrace();
      }
    }
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (resultCode == RESULT_OK) {
      Bundle bundle = data.getExtras();
      String scanResult = bundle.getString("result");
      act.setText(scanResult);
    }
  }
}