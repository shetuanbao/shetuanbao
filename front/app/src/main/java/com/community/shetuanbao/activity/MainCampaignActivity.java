package com.community.shetuanbao.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.community.shetuanbao.R;
import com.community.shetuanbao.utils.Exit;
import com.community.shetuanbao.utils.F_GetBitmap;
import com.community.shetuanbao.utils.FontManager;
import com.community.shetuanbao.utils.GetActivityInfo;
import com.community.shetuanbao.utils.RefreshableView;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;

public class MainCampaignActivity extends Activity implements ViewPager.OnPageChangeListener,AbsListView.OnScrollListener {
    private ViewPager viewPager;
    private ImageView[] tips;
    private ImageView[] mImageViews;
    private int[] imgIdArray;
    private ScheduledExecutorService scheduledExecutorService;
    int currentItem;
    private TextView sousuo = null;
    private ListView listview = null;
    private List<String[]> msgIds1 = null;
    private RelativeLayout title = null;
    private RelativeLayout title_gone = null;
    private AutoCompleteTextView actv;
    ImageView search = null;
    ImageView back = null;
    String mes;
    String mes2;
    String mes3;
    String mes4;
    public ProgressDialog pd2;
    private String[][] all = null;
    private String[][] all_2 = null;
    private String[][] all_3 = null;
    private String[][] all_4 = null;
    private String[][] all_5 = null;
    private String[][] all_6 = null;
    private String huodong[][] = null;
    private String[] name = null;
    private String[] time = null;
    private String[] didian = null;
    private Integer[] id = null;
    private String[] image = null;
    private String[] imagedong = null;
    Bitmap[] imageData;
    Bitmap[] imagehuadong;
    Bitmap[] imageDatafu;
    byte[][] all_imagefu;
    byte[] all_image;
    byte[][] all_imagedong;
    byte[][] allimage;
    private List<Map<String, Object>> listItem = new ArrayList<Map<String, Object>>();
    private List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
    private List<Map<String, Object>> huadong = new ArrayList<Map<String, Object>>();
    List<String[]> namey = new ArrayList<String[]>();
    List<String[]> timey = new ArrayList<String[]>();
    List<String[]> didiany = new ArrayList<String[]>();
    List<String[]> idy = new ArrayList<String[]>();
    List<String[]> imagey = new ArrayList<String[]>();
    List<String[]> imaged = new ArrayList<String[]>();
    List<String[]> zong = new ArrayList<String[]>();
    private baseAdapter base = null;
    private RelativeLayout nodate = null;
    private ImageView nodate_image = null;
    ImageView school;
    RefreshableView refreshableView;
    static int num = 0;
    static int n;
    static int x = 0;
    private View moreView;
    private int reduce;// 璁板綍鍒楄〃涓垹闄や簡澶氬皯鏉℃暟鎹�
    protected int lastPosition = 0;
    private int count;
    static int countz = 0;
    static int countx = 0;
    static int idd=85001;
    static Integer idd2[]=null;//活动id
    static String namee[]=null;//活动名
    static String placee[]=null;//活动地点
    static String timee[]=null;//活动时间
//    private String huodongname[]=null;
    private int actnum;//表示活动的数量
    private boolean isRunning = true;
    private String huodongid[]=null;
    static int huodongcount=0;
    boolean shifou=false;
    ScrollView sc=null;
    LinearLayout tt;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {

            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
            if (isRunning) {

                handler.sendEmptyMessageDelayed(0, 2000);
            }
        };
    };
    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setTheme(android.R.style.Theme_Holo_Dialog);
        setContentView(R.layout.activity_main_campaign);
        shifou=false;
        Exit.getInstance().addActivities(this);
        moreView = getLayoutInflater().inflate(R.layout.load, null);
        listview = (ListView) findViewById(R.id.huodong_text2);
        RelativeLayout r = (RelativeLayout) findViewById(R.id.huodong);
        r.setFocusable(true);
        r.setFocusableInTouchMode(true);
        r.requestFocus();
        r.requestFocusFromTouch();
        ViewGroup group = (ViewGroup) findViewById(R.id.viewGroup);
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        imgIdArray = new int[] { R.mipmap.yuandan, R.mipmap.wushu, R.mipmap.chuantong, R.mipmap.chaoji,
                R.mipmap.yingxie };

        tips = new ImageView[imgIdArray.length];
        for (int i = 0; i < tips.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(new LayoutParams(30, 30));
            tips[i] = imageView;
            if (i == 0) {
                tips[i].setBackgroundResource(R.mipmap.page_indicator_focused1);
            } else {
                tips[i].setBackgroundResource(R.mipmap.page_indicator_unfocused1);
            }
            group.addView(imageView);
        }

        mImageViews = new ImageView[imgIdArray.length];
        for (int i = 0; i < mImageViews.length; i++) {
            ImageView imageView = new ImageView(this);
            mImageViews[i] = imageView;
            imageView.setBackgroundResource(imgIdArray[i]);
        }

        viewPager.setAdapter(new MyAdapter());

        viewPager.setCurrentItem((mImageViews.length) * 100);

        viewPager.setOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                setImageBackground(position % mImageViews.length);
                // lastPosition = position;
            }
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        handler.sendEmptyMessageDelayed(0, 2000);
        pd2 = new ProgressDialog(this);
        pd2.setMax(100);
        pd2.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd2.setCancelable(false);
        pd2.setMessage("鍔犺浇涓�...璇风◢鍚�");
        search = (ImageView)findViewById(R.id.main_search_title_image);
        title = (RelativeLayout)findViewById(R.id.huodong);
        title_gone = (RelativeLayout) findViewById(R.id.main_search_title_clock);
        back = (ImageView) findViewById(R.id.main_search_title_clock_back);
        nodate = (RelativeLayout) findViewById(R.id.nodate);
        nodate_image = (ImageView) nodate.findViewById(R.id.nodate_image);
        nodate_image.getBackground().setAlpha(150);
        school = (ImageView) findViewById(R.id.school_2);
        school.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://www.ncst.edu.cn"));
                startActivity(intent);
            }
        });
        sousuo = (TextView) findViewById(R.id.title_bar_sousuo);
        sousuo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String messa = actv.getText().toString();
                Intent it=new Intent(MainCampaignActivity.this,huodong_sousuo.class);
                it.putExtra("sousuo", messa);
                startActivity(it);
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                title.setVisibility(View.GONE);
                title_gone.setVisibility(View.VISIBLE);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                title.setVisibility(View.VISIBLE);
                title_gone.setVisibility(View.GONE);
            }
        });
        thread_ntd th = new thread_ntd();
        th.start();
        try {
            th.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        actv = (AutoCompleteTextView) findViewById(R.id.title_bar_name_text);
        actv.setThreshold(1);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(MainCampaignActivity.this,
                R.layout.autocompletetextview, namee);
        actv.setAdapter(adapter2);
        actv.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                for (int i = 0; i < actnum; i++) {
                    if (actv.getText().toString().trim().equals(namee[i].toString().trim())) {
                        String messa = actv.getText().toString();
                        Intent it=new Intent(MainCampaignActivity.this,huodong_sousuo.class);
                        it.putExtra("sousuo", messa);
                        startActivity(it);
                    }
                }
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
                // sp.edit().clear().commit();
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub

            }
        });
        FontManager.initTypeFace(this);
        FontManager.changeFonts(FontManager.getContentView(this), this);
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
        @Override
        public Object instantiateItem(View container, final int position) {
            ((ViewPager) container).addView(mImageViews[position % mImageViews.length], 0);
            currentItem = position;
            mImageViews[position % mImageViews.length].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent it = new Intent(MainCampaignActivity.this, HuoDongDetailActivity.class);
                    it.putExtra("activity", "MainActivity");
                    it.putExtra("name", namee[position % mImageViews.length]);
                    it.putExtra("time", timee[position % mImageViews.length]);
                    it.putExtra("place", placee[position % mImageViews.length]);
                    it.putExtra("id", idd2[position % mImageViews.length]);
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

    private void setImageBackground(int selectItems) {
        for (int i = 0; i < tips.length; i++) {
            if (i == selectItems) {
                tips[i].setBackgroundResource(R.mipmap.page_indicator_focused1);
            } else {
                tips[i].setBackgroundResource(R.mipmap.page_indicator_unfocused1);
            }
        }
    }
    private class thread_ntd extends Thread {//鑾峰彇娲诲姩淇℃伅鐨勫瓙绾跨▼
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public void run() {
            try {
                GetActivityInfo.GetActivities();
               actnum= GetActivityInfo.a;
//                all = new String[n][zong.get(0).length];
//                name = new String[all.length];
//                time = new String[all.length];
//                didian = new String[all.length];
                id = new Integer[actnum];//活动类型
                image = new String[actnum];//照片名
                all_image = new byte[actnum];
//                imageData = new Bitmap[all.length];
//                imageDatafu=new Bitmap[all.length];
//                all_imagefu=new byte[all.length][];
                idd2=new Integer[actnum];//活动id
                placee=new String[actnum];//活动地点
                namee=new String[actnum];//活动名
                timee=new String[actnum];//活动时间
                imageData = new Bitmap[actnum];
                num = actnum;
                if (num < 5) {
                    n = num;
                    x = n;
                    num = num - n;
                } else {
                    n = 5;
                    x = n;
                    num = num - n;
                }
                    for (int i = 0; i <actnum; i++) {
                            idd2[i]=GetActivityInfo.activityId[i];
                            namee[i]=GetActivityInfo.activityName[i];
                            timee[i]=GetActivityInfo.time[i];
                            placee[i]=GetActivityInfo.palce[i];
                            id[i]=GetActivityInfo.activityLei[i];
                            image[i] = GetActivityInfo.activityPicture[i] + ".png";

                    }
                    for (int i = 0; i < actnum; i++) {
                        if (F_GetBitmap.isEmpty(image[i])) {
                            all_image=GetActivityInfo.getImage2(image[i]);
//                        all_image[i] = NetInfoUtil.getPicture(image[i]);
                            F_GetBitmap.setInSDBitmap(all_image, image[i]);
                            InputStream input = null;
                            BitmapFactory.Options options = new BitmapFactory.Options();
                            options.inSampleSize = 2;
                            input = new ByteArrayInputStream(all_image);
                            @SuppressWarnings({ "rawtypes", "unchecked" })
                            SoftReference softRef = new SoftReference(BitmapFactory.decodeStream(input, null, options));
                            imageData[i] = (Bitmap) softRef.get();
                            System.out.println(imageData.length);
                        } else {
                            imageData[i] = F_GetBitmap.getSDBitmap(image[i]);// 鎷垮埌鐨勬槸BitMap绫诲瀷鐨勫浘鐗囨暟鎹�
                            if (F_GetBitmap.bitmap != null && !F_GetBitmap.bitmap.isRecycled()) {
                                F_GetBitmap.bitmap = null;
                            }
                        }
                    }
//                    for (int i = 0; i < actnum; i++) {
//                        if (F_GetBitmap.isEmpty(image[i])) {
////                        all_imagefu[i] = NetInfoUtil.getPicture(image[i]);
//                            F_GetBitmap.setInSDBitmap(all_imagefu[i], image[i]);
//                            InputStream input = null;
//                            BitmapFactory.Options options = new BitmapFactory.Options();
//                            options.inSampleSize = 2;
//                            input = new ByteArrayInputStream(all_imagefu[i]);
//                            @SuppressWarnings({ "rawtypes", "unchecked" })
//                            SoftReference softRef = new SoftReference(BitmapFactory.decodeStream(input, null, options));
//                            imageDatafu[i] = (Bitmap) softRef.get();
//                        } else {
//                            imageDatafu[i] = F_GetBitmap.getSDBitmap(image[i]);// 鎷垮埌鐨勬槸BitMap绫诲瀷鐨勫浘鐗囨暟鎹�
//                            if (F_GetBitmap.bitmap != null && !F_GetBitmap.bitmap.isRecycled()) {
//                                F_GetBitmap.bitmap = null;
//                            }
//                        }
//                    }
                    initList();


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void initList() {
        for (int i = 0; i < actnum; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("name", namee[i]);
            map.put("time", timee[i]);
            map.put("image", imageData[i]);
            map.put("didian", placee[i]);
            map.put("id", idd2[i]);
            listItem.add(map);
        }
        inithandler();
    }
    public void inithandler() {
        Message msg = new Message();
        msg.what = 2;
        mHandler2.sendMessage(msg);
    }
    public Handler mHandler2 = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0:
                    loadMoreData(); // 鍔犺浇鏇村鏁版嵁
                    base.notifyDataSetChanged();
                    moreView.setVisibility(View.GONE);
                    break;
                case 1:
                    reduceSomeData();// 鍒犻櫎瓒呭嚭鐨勬暟鎹�
                    loadMoreData(); // 鍔犺浇鏇村鏁版嵁锛岃繖閲屽彲浠ヤ娇鐢ㄥ紓姝ュ姞杞�
                    base.notifyDataSetChanged();
                    moreView.setVisibility(View.GONE);
                    break;
                case 2:
                    initBaseAdapter();
                    break;
                default:
                    break;
            }
        }
    };
    public void initBaseAdapter() {
        base = new baseAdapter(this);
        listview.addFooterView(moreView);
        listview.setAdapter(base);
        listItem.clear();
        listview.setOnScrollListener(this); // 璁剧疆listview鐨勬粴鍔ㄤ簨浠�
        setListViewHeightBasedOnChildren(listview);
        listview.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                // TODO Auto-generated method stub
                LinearLayout view1 = (LinearLayout) arg1;
                LinearLayout ll = (LinearLayout) view1.getChildAt(1);
                TextView text2 = (TextView) ll.getChildAt(0);
                TextView text3 = (TextView) ll.getChildAt(1);
                TextView text4 = (TextView) ll.getChildAt(2);
                mes = text2.getText().toString();
                mes2 = text3.getText().toString();
                mes3 = text4.getText().toString();
                Intent it = new Intent(MainCampaignActivity.this, HuoDongDetailActivity.class);
                it.putExtra("activity", "MainActivity");
                it.putExtra("name", mes);
                it.putExtra("time", mes2);
                it.putExtra("place", mes3);
                it.putExtra("id", huodongid[arg2]);
                startActivity(it);
            }
        });
    }
    private class baseAdapter extends BaseAdapter { // 鍐呴儴绫伙細閫傞厤鍣�
        private LayoutInflater mInflater = null;
        public baseAdapter(Context context) {
            mInflater = LayoutInflater.from(context);
            data.addAll(listItem);
            listItem.clear();
        }
        @Override
        public int getCount() {
            return data.size();// 璁板綍褰撳墠鍒楄〃涓湁澶氬皯鏉℃暟
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
                convertView = mInflater.inflate(R.layout.list_huodong, null);
                myViews.image = (ImageView) convertView.findViewById(R.id.image);
                myViews.name = (TextView) convertView.findViewById(R.id.name);
                myViews.time = (TextView) convertView.findViewById(R.id.time);
                myViews.didian = (TextView) convertView.findViewById(R.id.didian);
                convertView.setTag(myViews);
            } else {
                myViews = (ViewHolder) convertView.getTag();

            }
            myViews.image.setImageBitmap((Bitmap) data.get(position).get("image"));
            myViews.name.setText((String) data.get(position).get("name"));
            myViews.time.setText((String) data.get(position).get("time"));
            myViews.didian.setText((String) data.get(position).get("didian"));
            myViews.name.setTypeface(FontManager.tf);
            myViews.time.setTypeface(FontManager.tf);
            myViews.didian.setTypeface(FontManager.tf);
            return convertView;
        }
    }
    public void setListViewHeightBasedOnChildren(ListView listView) {//姝ｇ‘鏄剧ずlistview鐨勫搴�
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
        ((MarginLayoutParams) params).setMargins(10, 10, 10, 10);
        listView.setLayoutParams(params);
    }
    static class ViewHolder {
        private ImageView image;
        private TextView name;
        private TextView time;
        private TextView didian;
    }
    private void reduceSomeData() {
        for (int i = 0; i < 10; i++) {
            listItem.remove(i); // 娓呴櫎鍓嶅崄鏉℃暟鎹�
        }
        reduce = reduce + 10;
        count = listItem.size();// 璁板綍褰撳墠鍒楄〃涓湁澶氬皯鏉�
        data.addAll(listItem);
        listItem.clear();
    }
    private class thread_shangla extends Thread {//涓婃媺鍔犺浇鏇村淇℃伅鐨勫瓙绾跨▼
        @Override
        public void run() {
            if (num < 5) {
                n = num;
                x = x + n;
                num = num - n;
            } else {
                n = 5;
                x = x + n;
                num = num - n;
            }
            for (int i = 0; i <actnum; i++) {
                idd2[i]=GetActivityInfo.activityId[i];
                namee[i]=GetActivityInfo.activityName[i];
                timee[i]=GetActivityInfo.time[i];
                placee[i]=GetActivityInfo.palce[i];
                id[i]=GetActivityInfo.activityLei[i];
                image[i] = GetActivityInfo.activityPicture[i] + ".png";

            }
            for (int i = 0; i < actnum; i++) {
                if (F_GetBitmap.isEmpty(image[i])) {
                    all_image=GetActivityInfo.getImage2(image[i]);
//                        all_image[i] = NetInfoUtil.getPicture(image[i]);
                    F_GetBitmap.setInSDBitmap(all_image, image[i]);
                    InputStream input = null;
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 2;
                    input = new ByteArrayInputStream(all_image);
                    @SuppressWarnings({ "rawtypes", "unchecked" })
                    SoftReference softRef = new SoftReference(BitmapFactory.decodeStream(input, null, options));
                    imageData[i] = (Bitmap) softRef.get();
                    System.out.println(imageData.length);
                } else {
                    imageData[i] = F_GetBitmap.getSDBitmap(image[i]);// 鎷垮埌鐨勬槸BitMap绫诲瀷鐨勫浘鐗囨暟鎹�
                    if (F_GetBitmap.bitmap != null && !F_GetBitmap.bitmap.isRecycled()) {
                        F_GetBitmap.bitmap = null;
                    }
                }
            }
                for (int i = 0; i < name.length; i++) {
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("name", namee[i]);
                    map.put("time", timee[i]);
                    map.put("image", imageData[i]);
                    map.put("didian", placee[i]);
                    map.put("id", idd2[i]);
                    listItem.add(map);
                }
            }
        }

    private void loadMoreData() {
        thread_shangla lmd = new thread_shangla();
        lmd.start();
        try {
            lmd.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        count = listItem.size();// 璁板綍褰撳墠鍒楄〃涓湁澶氬皯鏉℃暟
        data.addAll(listItem);
        listItem.clear();
    }
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
    }
    @SuppressWarnings("static-access")
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        switch (scrollState) {
            // 褰撲笉婊氬姩鏃�
            case OnScrollListener.SCROLL_STATE_IDLE:
                // 鍒ゆ柇婊氬姩鍒板簳閮�
                if (count < 50 && view.getLastVisiblePosition() == (view.getCount() - 1)) {
                    moreView.setVisibility(View.VISIBLE);
                    // Toast.makeText(this, "婊戝�掑簳閮�", Toast.LENGTH_SHORT).show();
                    mHandler2.sendEmptyMessage(0);
                }
                if (count >= 10 && view.getLastVisiblePosition() == (view.getCount() - 1)) {
                    moreView.setVisibility(View.VISIBLE);
                    mHandler2.sendEmptyMessage(1);
                }
                break;
        }
    }
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        // TODO Auto-generated method stub
    }
    private class AysncTask_team extends AsyncTask<Void, Integer, Void> {
        @Override
        protected void onPreExecute() {
            pd2.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            return null;
        }
        @Override
        protected void onProgressUpdate(Integer... values) {
            pd2.setProgress(values[0]);
        }
        @Override
        protected void onPostExecute(Void result) {
            pd2.dismiss();
        }
    }
    @Override
    @SuppressWarnings("deprecation")
    public boolean onKeyDown(int keyCode, KeyEvent event)      //锟斤拷写锟斤拷锟截硷拷
    {
        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            AlertDialog isExit=new AlertDialog.Builder(this).create();
            isExit.setTitle("退出");
            isExit.setMessage("确定退出系统吗？");
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
