package com.community.shetuanbao.community;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.community.shetuanbao.R;
import com.community.shetuanbao.activity.HuoDongDetailActivity;
import com.community.shetuanbao.utils.F_GetBitmap;
import com.community.shetuanbao.utils.FontManager;
import com.community.shetuanbao.utils.LocalLoader;
import com.community.shetuanbao.utils.NetLoader;
import com.community.shetuanbao.utils.ObservableScrollView;
import com.community.shetuanbao.utils.RequestUtils;
import com.community.shetuanbao.utils.activitytubiaoStrategy;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CommunityDetailActivity extends Activity implements ObservableScrollView.ScrollViewListener {
    //imgIdArray用于无数据测试
    private int[] imgIdArray;
    ProgressDialog pd;
    String re_name = null;
    int re_id;
    String re_kouhao = null;
    TextView tt = null;
    TextView tt2 = null;
    TextView tt3 = null;
    TextView tt4 = null;
    String detail = null;
    String people = null;
    public static String s = null;
    private List<Map<String, Object>> listItem = new ArrayList<Map<String, Object>>();
    private List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
    Bitmap imageData[]=null;
    byte[][] all_image;
    private String image[]=null;
    private int id[]=null;
    private int tempid[]=null;
    private String time[]=null;
    private String place[]=null;
    private String name[]=null;
    List<String[]> imagey = new ArrayList<String[]>();
    String all[][]=null;
    TextView tt0 = null;
    private ViewPager viewPager;
    private ImageView[] tips;
    private ImageView[] mImageViews;
    private boolean isRunning = true;
    int currentItem;
    ListView listview;
    baseAdapter base;
    private View footView;
    private ImageView ivDown;
    int itemCount=0;
    private int height ;
    String zhuangtai=null;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            // 执行滑动到下一个页面
            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
            if (isRunning) {
                // 在发一个handler延时
                handler.sendEmptyMessageDelayed(0, 3000);
            }
        };
    };
    Map<String, Object> params;
    RelativeLayout biaotilan;
    ObservableScrollView sc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.communitydetailactivity);
        footView = getLayoutInflater().inflate(R.layout.view_foot, null);
        ivDown = (ImageView) footView.findViewById(R.id.iv_down);
        pd = new ProgressDialog(this);
        pd.setMax(100);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setCancelable(false);
        pd.setMessage("加载中...请稍后");
        tt0 = (TextView) findViewById(R.id.shetuan_tool_text1);
        tt0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainCommunityActivity.pd.dismiss();
                finish();
            }
        });
        Intent intent = getIntent();
        re_name = intent.getStringExtra("name");
        re_id = intent.getIntExtra("id",0);
        re_kouhao = intent.getStringExtra("kouhao");
        thread_get th = new thread_get();
        th.start();
        try {
            th.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
        inittextview();
        ViewGroup group = (ViewGroup) findViewById(R.id.viewGroup3);
        viewPager = (ViewPager) findViewById(R.id.viewPager3);
        tips = new ImageView[imageData.length];
        for (int i = 0; i < tips.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(new Gallery.LayoutParams(30, 30));
            tips[i] = imageView;
            if (i == 0) {
                tips[i].setBackgroundResource(R.mipmap.page_indicator_focused1);
            }else {
                tips[i].setBackgroundResource(R.mipmap.page_indicator_unfocused1);
            }
            ViewGroup parent = (ViewGroup) imageView.getParent();
            if (parent != null) {
                parent.removeAllViewsInLayout();
            }
            group.addView(imageView);
        }
        // 将图片装载到数组中
        //用数据的装载
        mImageViews = new ImageView[imageData.length];
        for (int i = 0; i < mImageViews.length; i++) {
            ImageView imageView = new ImageView(this);
            mImageViews[i] = imageView;
            imageView.setImageBitmap(imageData[i]);
        }
        // 设置Adapter
        viewPager.setAdapter(new MyAdapter());
        // 设置ViewPager的默认项, 设置为长度的100倍，这样子开始就能往左滑动
        viewPager.setCurrentItem((mImageViews.length) * 100);
        // 设置监听，主要是设置点点的背景
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                setImageBackground(position % mImageViews.length);
            }
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // 页面正在滑动时间回调
            }
            @Override
            public void onPageScrollStateChanged(int state) {
                // 当pageView 状态发生改变的时候，回调
            }
        });
        handler.sendEmptyMessageDelayed(0, 3000);
        //initView();
        FontManager.initTypeFace(this);
        FontManager.changeFonts(FontManager.getContentView(this), this);
        s = tt.getText().toString();
    }
    @Override
    public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
        if(y<=height&&y>0 ){
            float scale =(float) (height-y) /height;
            float alpha =  (255 * scale);
            biaotilan.setBackgroundColor(Color.argb((int) alpha, 73, 196, 214));
            tt0.setTextColor(this.getResources().getColor(R.color.white));

        }
        else if(y > height&&y<=2*height){
            biaotilan.setBackgroundColor(Color.argb(0, 0xfd, 0x91, 0x5b));
            tt0.setTextColor(this.getResources().getColor(R.color.blue2));
        }else if(y>2*height) {
            //biaotilan.getBackground().setAlpha(255);
            // tt0.setTextColor(this.getResources().getColor(R.color.white));
            biaotilan.setBackgroundColor(Color.argb(0, 0xfd, 0x91, 0x5b));
            tt0.setTextColor(this.getResources().getColor(R.color.blue2));
        }
    }
    public class thread_get extends Thread {
        @Override
        public void run() {  try {
            params=new HashMap<>();
            params.put("communityName",re_name);
            String res = RequestUtils.post("/community/panfindByCommunityName",params);
            try {
                JSONObject jsonObject = new JSONObject(res);
                if (jsonObject.getInt("code") == 200) {
                    // 注意获取到的数据的数据类型，在后台是数组，则这里是JSONArray，在后台是类，则这里是JSONObject
                    JSONObject community = jsonObject.getJSONObject("data");
                    detail=community.getString("communityIntroduce");
                    params=new HashMap<>();
                    params.put("communityId",re_id);
                    String res1 = RequestUtils.post("/community/panfindByCommunityUser",params);
                    try {
                        JSONObject jsonObject1 = new JSONObject(res1);
                        if (jsonObject1.getInt("code") == 200) {
                            // 注意获取到的数据的数据类型，在后台是数组，则这里是JSONArray，在后台是类，则这里是JSONObject
                            JSONArray list = (JSONArray) jsonObject1.get("data");
                            tempid=new int[list.length()];
                            for(int i=0;i<list.length();i++){
                                tempid[i]=list.getInt(i);
                            }
                            String res2 = RequestUtils.get("/users/list");
                            try {
                                JSONObject jsonObject2 = new JSONObject(res2);
                                if (jsonObject2.getInt("code") == 200) {
                                    // 注意获取到的数据的数据类型，在后台是数组，则这里是JSONArray，在后台是类，则这里是JSONObject
                                    JSONArray list1 = (JSONArray) jsonObject2.get("data");
                                    people="";
                                    for(int i=0;i<tempid.length;i++){
                                        for(int j=0;j<list1.length();j++){
                                            if(tempid[i]==list1.getJSONObject(j).getInt("userId")){
                                                people+=list1.getJSONObject(j).getString("userName")+" ";
                                            }
                                        }
                                    }
                                    params=new HashMap<>();
                                    params.put("communityId",re_id);
                                    String res3 = RequestUtils.post("/activities/pangetactivityByCommunityId",params);
                                    try {
                                        JSONObject jsonObject3 = new JSONObject(res3);
                                        if (jsonObject3.getInt("code") == 200) {
                                            // 注意获取到的数据的数据类型，在后台是数组，则这里是JSONArray，在后台是类，则这里是JSONObject
                                            JSONArray list2 = (JSONArray) jsonObject3.get("data");
                                            name=new String[list2.length()];
                                            time=new String[list2.length()];
                                            place=new String[list2.length()];
                                            image=new String[list2.length()];
                                            imageData=new Bitmap[list2.length()];
                                            for(int i=0;i<list2.length();i++){
                                                name[i]=list2.getJSONObject(i).getString("activityTitle");
                                                time[i]=list2.getJSONObject(i).getString("activityTime");
                                                place[i]=list2.getJSONObject(i).getString("activityPlace");
                                                image[i]=list2.getJSONObject(i).getString("activityPicture")+".png";
                                            }
                                            all_image=new byte[image.length][];
                                            //加载图标的线程
                                            Thread_pic thread_pic=new Thread_pic();
                                            thread_pic.start();
                                            try {
                                                thread_pic.join();
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                            initList();
                                        } else {
                                            Looper.prepare();
                                            Toast.makeText(CommunityDetailActivity.this, "获取社团信息失败", Toast.LENGTH_LONG).show();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    Looper.prepare();
                                    Toast.makeText(CommunityDetailActivity.this, "获取信息失败", Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Looper.prepare();
                            Toast.makeText(CommunityDetailActivity.this, "获取信息失败", Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Looper.prepare();
                    Toast.makeText(CommunityDetailActivity.this, "获取信息失败", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        }
    }

    public class Thread_pic extends Thread{
        @Override
        public void run(){
            //策略模式加模板模式
            for(int i=0;i<all_image.length;i++) {
                if (F_GetBitmap.isEmpty(image[i])){
                    imageData[i]= new NetLoader().loadImage(image[i],new activitytubiaoStrategy());
                }
                else{
                    imageData[i]= new LocalLoader().loadImage(image[i],new activitytubiaoStrategy());
                }
            }
        }
    }

    public void initList() {
        for (int i = 0; i < name.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("name", name[i]);
            map.put("image", imageData[i]);
            map.put("place",  place[i]);
            map.put("time", time[i]);
            listItem.add(map);
        }
        initBaseAdapter();
    }
    public void initBaseAdapter() {
        listview=(ListView)findViewById(R.id.shetuandetail_listview);
        listview.addFooterView(footView);
        ivDown.setImageDrawable(getResources()
                .getDrawable(R.mipmap.icon_down));
        base = new baseAdapter(this);
        listview.setAdapter(base);
        setListViewHeightBasedOnChildren(listview);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                // TODO Auto-generated method stub
//                Intent it=new Intent(CommunityDetailActivity.this,HuoDongDetailActivity.class);
//                it.putExtra("name", name[arg2]);
//                //测试数据
//                it.putExtra("id", "123");
////                it.putExtra("id", id[arg2]);
//                it.putExtra("time", time[arg2]);
//                it.putExtra("place", place[arg2]);
//
//                startActivity(it);
            }
        });
        initEvent();
    }
    //用于是否展开listview的 收起来时只显示3个
    private void initEvent() {
        ivDown.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // 判断getCount()数据的数量，如果等于3点击后就设置getCount()为全部数量，设置修改标识，刷新。
                // 否则，相反。
                if (base.getCount() == 0) {
                    base.addItemNum(data.size());
                    ivDown.setImageDrawable(getResources().getDrawable(
                            R.mipmap.icon_up));
                    setListViewHeightBasedOnChildren(listview);
                    base.notifyDataSetChanged();
                } else {
                    base.addItemNum(0);
                    ivDown.setImageDrawable(getResources().getDrawable(
                            R.mipmap.icon_down));
                    setListViewHeightBasedOnChildren(listview);
                    base.notifyDataSetChanged();
                }
            }
        });
    }

    private class baseAdapter extends BaseAdapter { // 内部类：适配器

        private LayoutInflater mInflater = null;

        public baseAdapter(Context context) {

            mInflater = LayoutInflater.from(context);

            data.addAll(listItem);
            listItem.clear();
        }

        @Override
        public int getCount() {
            return itemCount;
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder myViews;
            if (convertView == null) {
                myViews = new ViewHolder();
                convertView = mInflater.inflate(R.layout.sthuodong, null);
                myViews.image = (ImageView) convertView.findViewById(R.id.huodong_image);
                myViews.name = (TextView) convertView.findViewById(R.id.huodong_name);
                myViews.didian=(TextView)convertView.findViewById(R.id.huodong_didian);
                myViews.time=(TextView)convertView.findViewById(R.id.huodong_time);
                convertView.setTag(myViews);
            } else {
                myViews = (ViewHolder) convertView.getTag();

            }
            myViews.image.setImageBitmap((Bitmap) data.get(position).get("image"));
            myViews.name.setText((String) data.get(position).get("name"));
            myViews.didian.setText((String) data.get(position).get("place"));
            myViews.time.setText((String) data.get(position).get("time"));
            myViews.name.setTypeface(FontManager.tf);
            myViews.didian.setTypeface(FontManager.tf);
            myViews.time.setTypeface(FontManager.tf);
            return convertView;
        }
        public void addItemNum(int number)
        {
            itemCount = number;
        }

    }
    public void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        ((ViewGroup.MarginLayoutParams) params).setMargins(10, 10, 10, 10);
        listView.setLayoutParams(params);
    }

    static class ViewHolder {

        private ImageView image;
        private TextView name;
        private TextView time;
        private TextView didian;
    }
    private void inittextview() {
        // TODO Auto-generated method stub
        tt = (TextView) findViewById(R.id.shetuan_name_2);
        tt2 = (TextView) findViewById(R.id.shetuan_detail_2);
        tt3 = (TextView) findViewById(R.id.shetuan_people_2);
        tt4 = (TextView) findViewById(R.id.shetuan_kouhao_2);
        tt.setText(re_name);
        tt2.setText(detail);
        tt3.setText(people);
        tt4.setText(re_kouhao);
        tt3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
//                // TODO Auto-generated method stub
                Intent it = new Intent(CommunityDetailActivity.this, CommunityPeopleActivity.class);
                startActivity(it);
            }
        });
    }
    public class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(View container, int position, Object object) {
            ((ViewPager) container).removeView(mImageViews[position % mImageViews.length]);

        }

        /**
         * 载入图片进去，用当前的position 除以 图片数组长度取余数是关键
         */
        @Override
        public Object instantiateItem(View container, final int position) {
            if (mImageViews[position % mImageViews.length].getParent() != null) {
                ((ViewGroup) mImageViews[position % mImageViews.length].getParent())
                        .removeView(mImageViews[position % mImageViews.length]);
            }

            ((ViewPager) container).addView(mImageViews[position % mImageViews.length], 0);
            currentItem = position;
            mImageViews[position % mImageViews.length].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Toast.makeText(MainHuodongActivity.this,(position %
                    // mImageViews.length)+"",Toast.LENGTH_LONG).show();
                    Intent it=new Intent(CommunityDetailActivity.this,HuoDongDetailActivity.class);
                    it.putExtra("name", name[position % mImageViews.length]);
                    it.putExtra("id", id[position % mImageViews.length]);
                    it.putExtra("time", time[position % mImageViews.length]);
                    it.putExtra("place", place[position % mImageViews.length]);

                    startActivity(it);
                }
            });

            return mImageViews[position % mImageViews.length];

        }
    }
    /**
     * 设置选中的tip的背景
     *
     * @param selectItems
     */
    private void setImageBackground(int selectItems) {
        for (int i = 0; i < tips.length; i++) {
            if (i == selectItems) {
                tips[i].setBackgroundResource(R.mipmap.page_indicator_focused1);
            } else {
                tips[i].setBackgroundResource(R.mipmap.page_indicator_unfocused1);
            }
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) // 重写返回键
    {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            MainCommunityActivity.pd.dismiss();
            finish();
        }
        return false;
    }
}