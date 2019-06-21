package com.community.shetuanbao.community;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.community.shetuanbao.R;
import com.community.shetuanbao.utils.F_GetBitmap;
import com.community.shetuanbao.utils.FontManager;
import com.community.shetuanbao.utils.LocalLoader;
import com.community.shetuanbao.utils.NetLoader;
import com.community.shetuanbao.utils.RequestUtils;
import com.community.shetuanbao.utils.communitytubiaoStrategy;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CommunityLearningActivity extends Activity {
    private List<Map<String,Object>> listItem=new ArrayList<Map<String,Object>>();
    private List<Map<String,Object>> data=new ArrayList<Map<String,Object>>();
    private ListView listview=null;
    private String all[][]=null;
    private String all_2[][]=null;

    private byte[][] all_image;
    private int id[]=null;
    private int tempid[]=null;
    private String shetuan[]=null;
    private String image[]=null;
    private String kouhao[]=null;
    private String all_3[][]=null;
    private String all_4[][]=null;
    private baseAdapter base=null;

    private Bitmap imageData[];
    private List<String[]> shetuany=new ArrayList<String[]>();
    private List<String[]> idy=new ArrayList<String[]>();
    private List<String[]> imagey=new ArrayList<String[]>();
    private List<String[]> kouhaoy=new ArrayList<String[]>();
    private View footView;
    TextView js;
    ProgressDialog pd;
    Map<String, Object> params;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.communitylearningactivity);
        footView = getLayoutInflater().inflate(R.layout.listfoot, null);
        js = (TextView) footView.findViewById(R.id.iv_down_2);
        pd = new ProgressDialog(this);
        pd.setMax(100);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setCancelable(false);
        pd.setMessage("加载中...请稍后");
        //数据加载线程
        new AysncTask_team().execute();
        FontManager.initTypeFace(this);
        FontManager.changeFonts(FontManager.getContentView(this),this);
    }
    private class thread_tiyu extends Thread
    {
        @Override
        public void run(){
            try {
                params=new HashMap<>();
                params.put("page",0);
                params.put("size",0);
                String res = RequestUtils.post("/community/xueshu/list",params);
                try {
                    JSONObject jsonObject = new JSONObject(res);
                    if (jsonObject.getInt("code") == 200) {
                        // 注意获取到的数据的数据类型，在后台是数组，则这里是JSONArray，在后台是类，则这里是JSONObject
                        JSONArray list = (JSONArray) jsonObject.getJSONObject("data").get("list");
                        tempid = new int[list.length()];
                        for (int i = 0; i < list.length(); i++) {
                            tempid[i]=list.getJSONObject(i).getInt("communityId");
                        }
                    } else {
                        Looper.prepare();
                        Toast.makeText(CommunityLearningActivity.this, "获取社团信息失败", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String res1 = RequestUtils.post("/community/list",params);
                try {
                    JSONObject jsonObject = new JSONObject(res1);
                    if (jsonObject.getInt("code") == 200) {
                        // 注意获取到的数据的数据类型，在后台是数组，则这里是JSONArray，在后台是类，则这里是JSONObject
                        JSONArray list = (JSONArray) jsonObject.getJSONObject("data").get("list");
                        shetuan = new String[tempid.length];
                        id = new int[tempid.length];
                        kouhao = new String[tempid.length];
                        imageData = new Bitmap[tempid.length];
                        image=new String[tempid.length];
                        for (int i = 0; i < tempid.length; i++) {
                            for(int j=0;j<list.length();j++){
                                if(tempid[i]==list.getJSONObject(j).getInt("communityId")){
                                    shetuan[i]=list.getJSONObject(j).getString("communityName");
                                    id[i]=list.getJSONObject(j).getInt("communityId");
                                    kouhao[i]=list.getJSONObject(j).getString("communityKouhao");
                                    image[i]=list.getJSONObject(j).getString("communityTubiao")+".png";
                                    break;
                                }
                            }
                        }
                        all_image=new byte[tempid.length][];
                        //加载图标的线程
                        Thread_pic thread_pic=new Thread_pic();
                        thread_pic.start();
                        try {
                            thread_pic.join();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        Looper.prepare();
                        Toast.makeText(CommunityLearningActivity.this, "获取社团信息失败", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            initList();

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

    public void initList(){
        for(int i=0;i<shetuan.length;i++)
        {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("name", shetuan[i]);
            map.put("id", id[i]);
            map.put("kouhao", kouhao[i]);
            map.put("image", imageData[i]);
            listItem.add(map);
        }
        initadp();
    }
    public void initadp()
    {
        Message msg = new Message();
        msg.what = 2;
        mHandler.sendMessage(msg);
    }
    Handler mHandler=new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            switch(msg.what)
            {
                case 2:initBaseAdapter();
                    break;
                default:
                    break;
            }
        }
    };
    public void initBaseAdapter(){

        listview=(ListView)findViewById(R.id.shetuan_listview_xueshu);
        listview.addFooterView(footView);
        base=new baseAdapter(this);
        listview.setAdapter(base);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int arg2, long arg3) {
                LinearLayout view1=(LinearLayout)arg1;
                LinearLayout ll=(LinearLayout) view1.getChildAt(1);
                LinearLayout l=(LinearLayout) ll.getChildAt(0);
                LinearLayout l2=(LinearLayout)ll.getChildAt(1);
                TextView text=(TextView) l.getChildAt(0);
                TextView text2=(TextView)l2.getChildAt(0);
                String mes=text.getText().toString();
                String mes2=text2.getText().toString();
                Intent it = new Intent(CommunityLearningActivity.this,CommunityDetailActivity.class);
                it.putExtra("name", mes);
                it.putExtra("id" ,id[arg2]);
                it.putExtra("kouhao", mes2);
                startActivity(it);
            }
        });
    }
    private class baseAdapter extends BaseAdapter {
        private LayoutInflater mInflater=null;
        public baseAdapter(Context context){

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
            ViewHolder  myViews;
            if (convertView == null)
            {
                myViews = new ViewHolder ();
                convertView =mInflater.inflate(R.layout.shetuan_list, null) ;
                myViews.image = (ImageView) convertView.findViewById(R.id.shetuan_image);
                myViews.name = (TextView) convertView.findViewById(R.id.shetuan_name);
                myViews.id = (TextView) convertView.findViewById(R.id.shetuan_id);
                convertView.setTag(myViews);
            }
            else {
                myViews = (ViewHolder ) convertView.getTag();

            }
            myViews.name.setTypeface(FontManager.tf);
            myViews.id.setTypeface(FontManager.tf);
            js.setText("共"+shetuan.length+""+"个社团 ");
            myViews.image.setImageBitmap((Bitmap) data.get(position).get("image"));
            myViews.name.setText( (String) data.get(position).get("name"));
            myViews.id .setText( (String) data.get(position).get("kouhao"));
            return convertView;
        }

    }
    static class ViewHolder {

        private ImageView image;
        private TextView  name;
        private TextView  id;
    }
    class AysncTask_team extends AsyncTask<Void, Integer, Void> {
        @Override
        protected void onPreExecute() {
            //加载数据再测试
            pd.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
//            initList();
            thread_tiyu th2 = new thread_tiyu();
            th2.start();
            try {
                th2.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            pd.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Void result) {
            // 进行数据加载完成后的UI操作

            pd.dismiss();
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)      //��д���ؼ�
    {
        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            finish();
        }
        return false;
    }
}