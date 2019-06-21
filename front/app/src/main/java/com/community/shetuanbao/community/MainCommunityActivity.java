package com.community.shetuanbao.community;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.community.shetuanbao.R;
import com.community.shetuanbao.utils.Exit;
import com.community.shetuanbao.utils.F_GetBitmap;
import com.community.shetuanbao.utils.FontManager;
import com.community.shetuanbao.utils.LocalLoader;
import com.community.shetuanbao.utils.NetLoader;
import com.community.shetuanbao.utils.RequestUtils;
import com.community.shetuanbao.utils.communitytubiaoStrategy;

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



public class MainCommunityActivity extends Activity implements ViewPager.OnPageChangeListener, AbsListView.OnScrollListener {
    private ListView listview = null;
    private String all[][] = null;
    private String[] shetuan = null;
    private int[] id = null;
    private String[] kouhao = null;
    private String image[] = null;
    public static ProgressDialog pd;
    private List<String[]> zong = new ArrayList<String[]>();
    private List<Map<String, Object>> listItem = new ArrayList<Map<String, Object>>();
    private List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
    private baseAdapter base = null;
    private LinearLayout t1 = null;
    private LinearLayout t2 = null;
    private LinearLayout t3 = null;
    private LinearLayout t4 = null;
    private Bitmap[] imageData;
    private byte[][] all_image;
    private TextView sousuo = null;
    ImageView school;
    private int num = 0;
    private int n=0;
    private int x = 0;
    private int countid = 0;
    private int countpicture = 0;
    private int count = 0;
    private int reduce = 0;
    private int idd[]=null;
    private String kouhaoo[]=null;
    private String namee[]=null;
    private View moreView;
    private RelativeLayout title = null;
    private RelativeLayout title_gone = null;
    private AutoCompleteTextView actv;
    ImageView search = null;
    ImageView back = null;
    private ViewPager viewPager;
    private ImageView[] tips;
    private ImageView[] mImageViews;
    private int[] imgIdArray;
    int currentItem;
    private boolean isRunning = true;
    private String shetuanname[]=null;
    private int shetuanid[]=null;
    static int bushu=0;
    boolean shifou=false;
    Map<String, Object> params;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.maincommunityactivity);
        Exit.getInstance().addActivities(this);
        shifou=false;
        moreView = getLayoutInflater().inflate(R.layout.load, null);
        ViewGroup group = (ViewGroup) findViewById(R.id.viewGroup2);
        viewPager = (ViewPager) findViewById(R.id.viewPager2);

        // 载入图片资源ID
        imgIdArray = new int[] { R.mipmap.zuqiu, R.mipmap.jiewu, R.mipmap.yumao, R.mipmap.pingpang,
                R.mipmap.lunhua };

        // 将点点加入到ViewGroup中(轮播图中的指示点)
        tips = new ImageView[imgIdArray.length];
        for (int i = 0; i < tips.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(30, 30));
            tips[i] = imageView;
            if (i == 0) {
                tips[i].setBackgroundResource(R.mipmap.page_indicator_focused1);
            } else {
                tips[i].setBackgroundResource(R.mipmap.page_indicator_unfocused1);
            }
            ViewGroup parent = (ViewGroup) imageView.getParent();
            if (parent != null) {
                parent.removeAllViewsInLayout();
            }
            group.addView(imageView);
        }
        // 将图片装载到数组中
        mImageViews = new ImageView[imgIdArray.length];
        for (int i = 0; i < mImageViews.length; i++) {
            ImageView imageView = new ImageView(this);
            mImageViews[i] = imageView;
            imageView.setBackgroundResource(imgIdArray[i]);
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
                // lastPosition = position;
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
        pd = new ProgressDialog(this);
        pd.setMax(100);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setCancelable(false);
        pd.setMessage("加载中，请稍后........");
        search = (ImageView) findViewById(R.id.shetuan_search_image);
        title = (RelativeLayout) findViewById(R.id.shetuan_1);
        title_gone = (RelativeLayout) findViewById(R.id.main_search_title_clock);
        back = (ImageView) findViewById(R.id.main_search_title_clock_back);
        //跳转到学校主页
        school = (ImageView) findViewById(R.id.school);
        school.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://www.scut.edu.cn/new/"));
                startActivity(intent);
            }
        });
        //点击搜索按钮后 跳转到community搜索页面
        sousuo = (TextView) findViewById(R.id.shetuantitle_bar_sousuo);
        sousuo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(actv.getText().toString().equals("")){
                    Toast.makeText(MainCommunityActivity.this,"请输入搜索内容",Toast.LENGTH_LONG).show();
                }
                // TODO Auto-generated method stub
                else {
                    shifou = true;
                    String messa = actv.getText().toString();
                    Intent it = new Intent(MainCommunityActivity.this, CommunitySearchActivity.class);
                    it.putExtra("messa", messa);
                    startActivity(it);
                }
            }
        });
        //点击搜索按钮打开搜索title
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                title.setVisibility(View.GONE);
                title_gone.setVisibility(View.VISIBLE);
            }
        });
        //关闭搜索title
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                title.setVisibility(View.VISIBLE);
                title_gone.setVisibility(View.GONE);

            }
        });
        //加载listview数据的线程
//        initList();
        thread_shetuan th = new thread_shetuan();
        th.start();
        try {
            th.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //跳转到体育社团
        t1 = (LinearLayout) findViewById(R.id.shetuan_button_1);
        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MainCommunityActivity.this,CommunitySportActivity.class);
                startActivity(it);
            }
        });
        //跳转到艺术社团
        t2 = (LinearLayout) findViewById(R.id.shetuan_button_2);
        t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MainCommunityActivity.this, CommunityLiteratureActivity.class);
                startActivity(it);
            }
        });
        //跳转到文学社团
        t3 = (LinearLayout) findViewById(R.id.shetuan_button_3);
        t3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MainCommunityActivity.this, CommunityLearningActivity.class);
                startActivity(it);
            }
        });
        //跳转到爱心社团
        t4 = (LinearLayout) findViewById(R.id.shetuan_button_4);
        t4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MainCommunityActivity.this, CommunityCharitableActivity.class);
                startActivity(it);
            }
        });
        //搜索框的设置
        actv = (AutoCompleteTextView) findViewById(R.id.shetuantitle_bar_name_text);
        actv.setThreshold(1);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(MainCommunityActivity.this,
                R.layout.autocompletetextview, shetuanname);
        actv.setAdapter(adapter2);
        actv.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                shifou=true;
                for (int i = 0; i < shetuanname.length; i++) {
                    if (actv.getText().toString().trim().equals(shetuanname[i].toString().trim())) {
                        bushu=i;
                        String messa = actv.getText().toString();
                        Intent it=new Intent(MainCommunityActivity.this,CommunitySearchActivity.class);
                        it.putExtra("messa", messa);
                        startActivity(it);
                    }
                }
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub

            }
        });
        FontManager.initTypeFace(this);
        FontManager.changeFonts(FontManager.getContentView(this), this);
    }
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    pd.show();
                    break;
                case 2:
                    Toast.makeText(MainCommunityActivity.this, "已经滑到底部!",
                            Toast.LENGTH_LONG).show();
            }
        }
    };
    private class thread_shetuan extends Thread {
        @Override
        public void run() {
            //返回所有社团的所有信息
            try {
                params=new HashMap<>();
                params.put("page",0);
                params.put("size",0);
                String res = RequestUtils.post("/community/list",params);
                try {
                    JSONObject jsonObject = new JSONObject(res);
                    if (jsonObject.getInt("code") == 200) {
                        // 注意获取到的数据的数据类型，在后台是数组，则这里是JSONArray，在后台是类，则这里是JSONObject
                        JSONArray list = (JSONArray) jsonObject.getJSONObject("data").get("list");
                        num=list.length();
                        if (num < 5) {
                            n = num;
                            x = n;
                            num = num - n;
                        } else {
                            n = 5;
                            x = n;
                            num = num - n;
                        }
                        shetuan = new String[n];
                        id = new int[n];
                        kouhao = new String[n];
                        idd=new int[n];
                        kouhaoo=new String[n];
                        namee=new String[n];
                        imageData = new Bitmap[n];
                        image=new String[n];
                        for (int i = 0; i < n; i++) {
                            shetuan[i]=list.getJSONObject(i).getString("communityName");
                            id[i]=list.getJSONObject(i).getInt("communityId");
                            kouhao[i]=list.getJSONObject(i).getString("communityKouhao");
                            image[i]=list.getJSONObject(i).getString("communityTubiao")+".png";
                            //用于记录第一次加载的数据
                            idd[i]=id[i];
                            kouhaoo[i]=kouhao[i];
                            namee[i]=shetuan[i];
                        }
                        shetuanname=new String[list.length()];
                        shetuanid=new int[list.length()];
                        for(int i=0;i<list.length();i++) {
                            shetuanname[i] = list.getJSONObject(i).getString("communityName");
                            shetuanid[i]=list.getJSONObject(i).getInt("communityId");
                        }
                        all_image=new byte[n][];
                        //加载图片线程
                        Thread_pic thread_pic=new Thread_pic();
                        thread_pic.start();
                        try {
                            thread_pic.join();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        initList();
                    } else {
                        Looper.prepare();
                        Toast.makeText(MainCommunityActivity.this, "获取社团信息失败", Toast.LENGTH_LONG).show();
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
                    imageData[i]= new NetLoader().loadImage(image[i],new communitytubiaoStrategy());
                }
                else{
                    imageData[i]= new LocalLoader().loadImage(image[i],new communitytubiaoStrategy());
                }
            }
        }
    }
    public void initList() {
        for (int i = 0; i < shetuan.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("name", shetuan[i]);
            map.put("id", id[i]);
            map.put("kouhao", kouhao[i]);
            map.put("image", imageData[i]);
            listItem.add(map);
        }
        inithandler();
    }
    public void inithandler() {
        Message msg = new Message();
        msg.what = 2;
        mHandler2.sendMessage(msg);
    }
    private Handler mHandler2 = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0:
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    loadMoreData(); // 加载更多数据
                    base.notifyDataSetChanged();
                    moreView.setVisibility(View.GONE);
                    break;
                case 1:
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    reduceSomeData();// 删除超出的数据
                    loadMoreData(); // 加载更多数据，这里可以使用异步加载
                    base.notifyDataSetChanged();
                    moreView.setVisibility(View.GONE);
                    break;
                case 2:
                    initBaseAdapter();
                    break;
                case 3:
                    pd.show();
                    break;
                case 4:
                    pd.dismiss();
                    break;
                default:
                    break;
            }
        }
    };
    public void initBaseAdapter() {
        base = new baseAdapter(this);
        listview = (ListView) findViewById(R.id.shetuan_listview);
        listview.addFooterView(moreView);
        listview.setAdapter(base);
        listItem.clear();
        listview.setOnScrollListener(this);
        // setListViewHeightBasedOnChildren(listview);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                pd.show();
                LinearLayout view1 = (LinearLayout) arg1;
                LinearLayout ll = (LinearLayout) view1.getChildAt(1);
                LinearLayout l = (LinearLayout) ll.getChildAt(0);
                LinearLayout l2 = (LinearLayout) ll.getChildAt(1);
                TextView text = (TextView) l.getChildAt(0);
                TextView text2 = (TextView) l2.getChildAt(0);
                String mes = text.getText().toString();
                String mes2 = text2.getText().toString();
                Intent it = new Intent(MainCommunityActivity.this, CommunityDetailActivity.class);
                it.putExtra("name", mes);
                it.putExtra("id", shetuanid[arg2]);
                it.putExtra("kouhao", mes2);
                startActivity(it);
            }
        });
    }
    private class baseAdapter extends BaseAdapter {
        private LayoutInflater mInflater = null;
        public baseAdapter(Context context) {
            mInflater = LayoutInflater.from(context);
            data.addAll(listItem);
            listItem.clear();
        }
        @Override
        public int getCount() {
            return data.size();
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
                convertView = mInflater.inflate(R.layout.shetuan_list, null);
                myViews.image = (ImageView) convertView.findViewById(R.id.shetuan_image);
                myViews.name = (TextView) convertView.findViewById(R.id.shetuan_name);
                myViews.id = (TextView) convertView.findViewById(R.id.shetuan_id);
                convertView.setTag(myViews);
            } else {
                myViews = (ViewHolder) convertView.getTag();
            }
            myViews.image.setImageBitmap((Bitmap) data.get(position).get("image"));
            myViews.name.setText((String) data.get(position).get("name"));
            myViews.id.setText((String) data.get(position).get("kouhao"));
            myViews.name.setTypeface(FontManager.tf);
            myViews.id.setTypeface(FontManager.tf);
            return convertView;
        }
    }
    static class ViewHolder {
        private ImageView image;
        private TextView name;
        private TextView id;
    }
    private void reduceSomeData() {
        for (int i = 0; i < 10; i++) {
            listItem.remove(i); // 清除前十条数据
        }
        reduce = reduce + 10;
        count = listItem.size();// 记录当前列表中有多少条
        data.addAll(listItem);
        listItem.clear();
    }
    //loadmoredata()中使用加载数据
    private class thread_shetuan2 extends Thread {
        @Override
        public void run() {
            try {
                params=new HashMap<>();
                params.put("page",0);
                params.put("size",0);
                String res = RequestUtils.post("/community/list",params);
                try {
                    JSONObject jsonObject = new JSONObject(res);
                    if (jsonObject.getInt("code") == 200) {
                        // 注意获取到的数据的数据类型，在后台是数组，则这里是JSONArray，在后台是类，则这里是JSONObject
                        JSONArray list = (JSONArray) jsonObject.getJSONObject("data").get("list");
                        if (num < 5) {
                            n = num;
                            x = x + n;
                            num = num - n;
                        } else {
                            n = 5;
                            x = x + n;
                            num = num - n;
                        }
                        shetuan = new String[n];
                        id = new int[n];
                        kouhao = new String[n];
                        imageData = new Bitmap[n];
                        image=new String[n];
                        for (int i = 0; i < n; i++) {
                            shetuan[i]=list.getJSONObject(x - n + i).getString("communityName");
                            id[i]=list.getJSONObject(x - n + i).getInt("communityId");
                            kouhao[i]=list.getJSONObject(x - n + i).getString("communityKouhao");
                            image[i]=list.getJSONObject(x-n+i).getString("communityTubiao")+ ".png";
                        }
                        all_image=new byte[n][];
                        //加载图片线程
                        Thread_pic thread_pic=new Thread_pic();
                        thread_pic.start();
                        try {
                            thread_pic.join();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        Looper.prepare();
                        Toast.makeText(MainCommunityActivity.this, "获取社团信息失败", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < shetuan.length; i++) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("name", shetuan[i]);
                map.put("id", id[i]);
                map.put("kouhao", kouhao[i]);
                map.put("image", imageData[i]);
                listItem.add(map);
            }
        }
    }
    private void loadMoreData() {
        thread_shetuan2 lmd = new thread_shetuan2();
        lmd.start();
        try {
            lmd.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        count = listItem.size();// 记录当前列表中有多少条数
        data.addAll(listItem);
        listItem.clear();
    }
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
    }
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        switch (scrollState) {
            // 当不滚动时
            case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                // 判断滚动到底部
                if (count < 50 && view.getLastVisiblePosition() == (view.getCount() - 1)) {
                    moreView.setVisibility(View.VISIBLE);
                    mHandler2.sendEmptyMessage(0);
                }
                break;
        }
    }

    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        // TODO Auto-generated method stub

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
                    Intent it = new Intent(MainCommunityActivity.this, CommunityDetailActivity.class);
                    it.putExtra("name", namee[position % mImageViews.length]);
                    it.putExtra("id", idd[position % mImageViews.length ]);
                    it.putExtra("kouhao", kouhaoo[position % mImageViews.length]);
                    startActivity(it);
                }
            });
            return mImageViews[position % mImageViews.length];
        }
    }
    @Override
    public void onPageScrollStateChanged(int arg0) {

    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {

    }

    @Override
    public void onPageSelected(int arg0) {
        setImageBackground(arg0 % mImageViews.length);

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
}