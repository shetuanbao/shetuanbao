package com.community.shetuanbao.Login;

import android.annotation.SuppressLint;
import android.app.ActivityGroup;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.community.shetuanbao.Personal.MainPersonalActivity;
import com.community.shetuanbao.R;
import com.community.shetuanbao.activity.MainCampaignActivity;
import com.community.shetuanbao.community.MainCommunityActivity;
import com.community.shetuanbao.utils.Exit;

import java.util.ArrayList;
import java.util.List;


@SuppressLint("HandlerLeak")
@SuppressWarnings("deprecation")
public class MainFrame extends ActivityGroup
{
  private LinearLayout frameBottomLuntan,frameBottomviewactivity,frameBottomviewtalk,
          frameBottomviewperson;
  private ImageView bottomviewLuntanImageview,bottomviewActivityImageview,bottomviewTalkImageview,
          bottomviewPersonImageview;
  private TextView bottomviewLuntanTextview,bottomviewActivityTextview,bottomviewTalkTextview,
          bottomviewPersonTextview;
  private android.support.v4.view.ViewPager mViewPager;
  private List<View> list=new ArrayList<View>();
  private View view=null;
  private View view1=null;
  private View view2=null;
  private View view4=null;
  private PagerAdapter pagerAdapter=null;
  @Override
  protected void onCreate(Bundle savedInstanceState){
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.mainframe);
    Exit.getInstance().addActivities(this);
    initView();
  }
  private void initView()
  {
    mViewPager=(ViewPager)findViewById(R.id.framepager);
    frameBottomLuntan = (LinearLayout)findViewById(R.id.frame_bottomview_luntan);
    frameBottomviewactivity = (LinearLayout)findViewById(R.id.frame_bottomview_activity);
    frameBottomviewtalk = (LinearLayout)findViewById(R.id.frame_bottomview_talk);
    frameBottomviewperson = (LinearLayout)findViewById(R.id.frame_bottomview_person);
    bottomviewLuntanImageview = (ImageView) findViewById(R.id.frame_bottomview_luntan_imageview);
    bottomviewActivityImageview = (ImageView) findViewById(R.id.frame_bottomview_activity_imageview);
    bottomviewTalkImageview = (ImageView) findViewById(R.id.frame_bottomview_talk_imageview);
    bottomviewPersonImageview = (ImageView) findViewById(R.id.frame_bottomview_person_imageview);

    bottomviewLuntanTextview = (TextView) findViewById(R.id.frame_bottemview_luntan_textview);
    bottomviewActivityTextview = (TextView) findViewById(R.id.frame_bottomview_activity_textview);
    bottomviewTalkTextview = (TextView) findViewById(R.id.frame_bottomview_talk_textview);
    bottomviewPersonTextview = (TextView) findViewById(R.id.frame_bottomview_person_textview);
    list.add(0,getZero());
    list.add(1,null);
    list.add(2,null);
    list.add(3,null);
    mViewPager.setOffscreenPageLimit(2);
    pagerAdapter=new PagerAdapter(){
      View v=null;
      @Override
      public boolean isViewFromObject(View agr0,Object agr1)
      {
        return agr0==agr1;
      }
      @Override
      public int getCount()
      {
        return list.size();
      }
      @Override
      public void destroyItem(ViewGroup container , int position,
                              Object object){
        container.removeView(list.get(position));

      }
      @Override
      public Object instantiateItem(ViewGroup container,int position){
        if(position==0)
        {
          v=getZero();
          container.removeView(v);
          container.addView(v);
          list.remove(0);
          list.add(0,v);
        }
        else if(position==1)
        {
                    v=getOne();
                    container.removeView(v);
                    container.addView(v);
                    list.remove(1);
                    list.add(1,v);
        }
        else if(position==2)
        {
          v=getTwo();
          container.removeView(v);
          container.addView(v);
          list.remove(2);
          list.add(2,v);
        }
        else if(position==3)
        {
                    v=getThree();
                    container.removeView(v);
                    container.addView(v);
                    list.remove(3);
                    list.add(3,v);
        }
        return v;
      }

    };
    mViewPager.setAdapter(pagerAdapter);
    MyBtnOnClick myTouchlistener=new MyBtnOnClick();
    frameBottomLuntan.setOnClickListener(myTouchlistener);
    frameBottomviewactivity.setOnClickListener(myTouchlistener);
    frameBottomviewtalk.setOnClickListener(myTouchlistener);
    frameBottomviewperson.setOnClickListener(myTouchlistener);
    //vierpager子页面改变时底部按钮的改变（类似与选中提示）
    mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
      @Override

      public void onPageSelected(int arg0) {
        initButtomBth();

        if(arg0 == 0) {
          bottomviewLuntanImageview
                  .setImageResource(R.mipmap.b_shetuan);
          bottomviewLuntanTextview.setTextColor(Color.parseColor("#49c4d6"));

        }else if(arg0 == 1) {
          bottomviewActivityImageview
                  .setImageResource(R.mipmap.b_huodong);
          bottomviewActivityTextview.setTextColor(Color.parseColor("#49c4d6"));

        }else if(arg0 == 2) {
          bottomviewTalkImageview
                  .setImageResource(R.mipmap.b_shejiao);
          bottomviewTalkTextview.setTextColor(Color.parseColor("#49c4d6"));

        }else if(arg0 == 3) {
          bottomviewPersonImageview
                  .setImageResource(R.mipmap.b_geren);
          bottomviewPersonTextview.setTextColor(Color.parseColor("#49c4d6"));

        }
      }


      @Override
      public void onPageScrolled(int arg0, float arg1, int arg2) {
      }


      @Override
      public void onPageScrollStateChanged(int arg0) {
      }
    });
  }
  @SuppressWarnings("deprecation")
  public View getZero()                          //显示第一个页面
  {
    view = this
            .getLocalActivityManager()
            .startActivity("luntan",
                    new Intent(MainFrame.this, MainCommunityActivity.class))
            .getDecorView();
    return view;                               //返回第一个view
  }
  @SuppressWarnings("deprecation")
  public View getOne()                           //得到第二个view
  {
        view1 = MainFrame.this
                .getLocalActivityManager()
                .startActivity("lostandfind",
                        new Intent(MainFrame.this,MainCampaignActivity.class))
                .getDecorView();
        return view1;
  }
  @SuppressWarnings("deprecation")
  public View getTwo()                           //得到第三个view
  {
    view2 = MainFrame.this
            .getLocalActivityManager()
            .startActivity("shopping",
                    new Intent(MainFrame.this,MainSocialActivity.class))
            .getDecorView();
    return view2;
  }
  @SuppressWarnings("deprecation")
  public View getThree()                           //得到第四个view
  {
        view4 = MainFrame.this
                .getLocalActivityManager()
                .startActivity("more",
                        new Intent(MainFrame.this,MainPersonalActivity.class))
                .getDecorView();
        return view4;
  }
  //点击底部按钮时改变底部按钮的样式（类似与选中提示）
  private class MyBtnOnClick implements View.OnClickListener {
    @Override
    public void onClick(View arg0) {
      int mBtnid = arg0.getId();
      switch (mBtnid) {
        case R.id.frame_bottomview_luntan :
          mViewPager.setCurrentItem(0);
          initButtomBth();
          bottomviewLuntanImageview
                  .setImageResource(R.mipmap.b_shetuan);
          bottomviewLuntanTextview.setTextColor(Color.parseColor("#49c4d6"));
          break;
        case R.id.frame_bottomview_activity :
          mViewPager.setCurrentItem(1);
          initButtomBth();
          bottomviewActivityImageview
                  .setImageResource(R.mipmap.b_huodong);
          bottomviewActivityTextview.setTextColor(Color.parseColor("#49c4d6"));
          break;
        case R.id.frame_bottomview_talk :
          mViewPager.setCurrentItem(2);
          initButtomBth();
          bottomviewTalkImageview
                  .setImageResource(R.mipmap.b_shejiao);
          bottomviewTalkTextview.setTextColor(Color.parseColor("#49c4d6"));

          break;
        case R.id.frame_bottomview_person :

          mViewPager.setCurrentItem(3);
          initButtomBth();
          bottomviewPersonImageview.setImageResource(R.mipmap.b_geren);
          bottomviewPersonTextview.setTextColor(Color.parseColor("#49c4d6"));
          break;
      }
    }
  }
  private void initButtomBth() {
    bottomviewLuntanImageview.setImageResource(R.drawable.search_bottom_index);
    bottomviewActivityImageview.setImageResource(R.drawable.search_bottom_search);
    bottomviewTalkImageview.setImageResource(R.drawable.search_bottom_tuijian);
    bottomviewPersonImageview.setImageResource(R.drawable.search_bottom_myself);
    bottomviewLuntanTextview.setTextColor(getResources().getColor(
            R.color.search_bottom_textcolor));
    bottomviewActivityTextview.setTextColor(getResources().getColor(
            R.color.search_bottom_textcolor));
    bottomviewTalkTextview.setTextColor(getResources().getColor(
            R.color.search_bottom_textcolor));
    bottomviewPersonTextview.setTextColor(getResources().getColor(
            R.color.search_bottom_textcolor));
  }
  @Override
  @SuppressWarnings("deprecation")
  public boolean onKeyDown(int keyCode, KeyEvent event)      //��д���ؼ�
  {
    if(keyCode == KeyEvent.KEYCODE_BACK)
    {
      AlertDialog isExit=new AlertDialog.Builder(this).create();
      isExit.setTitle("系统提示");
      isExit.setMessage("确定退出吗？");
      isExit.setButton("确定",
              new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                  Exit.exitActivity();
                }
              }
      );
      isExit.setButton2("取消",
              new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
              }
      );
      isExit.show();
    }
    return true;
  }
  public void toastSelf(String msgStr)
  {
    Bundle bd=new Bundle();
    bd.putString("msg", msgStr);
    Message msg=new Message();
    msg.what=0;
    msg.setData(bd);
    hd.sendMessage(msg);
  }
  Handler hd=new Handler()                             //����һ��Handler
  {
    @SuppressLint("HandlerLeak")
    @Override
    public void handleMessage(Message msg)
    {
      switch(msg.what)
      {
        case 0:
          Bundle bd=msg.getData();
          String msgStr=bd.getString("msg");
          Toast.makeText(MainFrame.this, msgStr, Toast.LENGTH_LONG).show();
          break;
      }
    }
  };
}