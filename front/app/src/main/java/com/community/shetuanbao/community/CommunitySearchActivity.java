package com.community.shetuanbao.community;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.community.shetuanbao.R;
import com.community.shetuanbao.utils.F_GetBitmap;
import com.community.shetuanbao.utils.FontManager;
import com.community.shetuanbao.utils.RequestUtils;

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


public class CommunitySearchActivity extends Activity {
    private List<Map<String, Object>> listItem = new ArrayList<Map<String, Object>>();
    private List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
    private List<String[]> shetuany = new ArrayList<String[]>();
    private List<String[]> idy = new ArrayList<String[]>();
    private List<String[]> kouhaoy = new ArrayList<String[]>();
    private List<String[]> imaged = new ArrayList<String[]>();
    private String all[][] = null;
    private String all_2[][] = null;
    private String all_3[][] = null;
    private String all_4[][] = null;
    private String[] shetuan = null;
    private int[] id = null;
    private String[] kouhao = null;
    private String image[] = null;
    String messa=null;
    byte all_image[][];
    Bitmap imageData[];
    TextView fanhui;
    ListView listview;
    baseAdapter base;
    RelativeLayout nodate;
    Map<String, Object> params;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.communitysearchactivity);
        nodate = (RelativeLayout) findViewById(R.id.nodate);
        listview = (ListView) findViewById(R.id.shetuan_sousuo_listview);
        fanhui=(TextView)findViewById(R.id.shetuan_sousuo_1);
        fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mm.pd.dismiss();
                finish();

            }
        });
        Intent intent = getIntent();
        messa = intent.getStringExtra("messa");
        //加载搜索结果listview
//        initList();
        thread_sousuo gg=new thread_sousuo();
        gg.start();
        try{
            gg.join();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    private class thread_sousuo extends Thread {
        @Override
        public void run() {
            try {
                params=new HashMap<>();
                params.put("communityName" ,messa);
                String res = RequestUtils.post("/community/panfindByCommunityName",params);
                try {
                    JSONObject jsonObject = new JSONObject(res);
                    if (jsonObject.getInt("code") == 200) {
                        // 注意获取到的数据的数据类型，在后台是数组，则这里是JSONArray，在后台是类，则这里是JSONObject
                            shetuan = new String[1];
                            id = new int[1];
                            kouhao = new String[1];
                            imageData = new Bitmap[1];
                            image=new String[1];
                            shetuan[0]=jsonObject.getJSONObject("data").getString("communityName");
                            id[0]=jsonObject.getJSONObject("data").getInt("communityId");
                            kouhao[0]=jsonObject.getJSONObject("data").getString("communityKouhao");
                            image[0]=jsonObject.getJSONObject("data").getString("communityTubiao")+".png";
                            all_image=new byte[1][];
                            //加载图标的线程
                            Thread_pic thread_pic=new Thread_pic();
                            thread_pic.start();
                            try {
                                thread_pic.join();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            initList();
                    }else if(jsonObject.getInt("code") == 400){
                        nodate.setVisibility(View.VISIBLE);
                        listview.setVisibility(View.GONE);
                    } else {
                        Looper.prepare();
                        Toast.makeText(CommunitySearchActivity.this, "获取社团信息失败", Toast.LENGTH_LONG).show();
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
            try {
                for(int i=0;i<all_image.length;i++) {
                    if (F_GetBitmap.isEmpty(image[i])) {
                        params = new HashMap<>();
                        params.put("tubiao", image[i]);
                        String res1 = RequestUtils.post("/community/panfindtubiao", params);
                        try {
                            JSONObject jsonObject1 = new JSONObject(res1);
                            if (jsonObject1.getInt("code") == 200) {
                                // 注意获取到的数据的数据类型，在后台是数组，则这里是JSONArray，在后台是类，则这里是JSONObject
                                JSONArray list1 = (JSONArray) jsonObject1.get("data");
                                all_image[i] = new byte[list1.length()];
                                for (int j = 0; j < list1.length(); j++) {
                                    all_image[i][j] = (byte) list1.getInt(j);
                                }
                                F_GetBitmap.setInSDBitmap(all_image[i], image[i]);
                                InputStream input = null;
                                BitmapFactory.Options options = new BitmapFactory.Options();
                                options.inSampleSize = 1;
                                input = new ByteArrayInputStream(all_image[i]);
                                @SuppressWarnings({ "rawtypes", "unchecked" })
                                SoftReference softRef = new SoftReference(BitmapFactory.decodeStream(input, null, options));
                                imageData[i] = (Bitmap) softRef.get();
                                System.out.println(imageData.length);
                            } else {
                                Looper.prepare();
                                Toast.makeText(CommunitySearchActivity.this, "获取社团信息失败", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    else{
                        imageData[i] = F_GetBitmap.getSDBitmap(image[i]);
                        if (F_GetBitmap.bitmap != null && !F_GetBitmap.bitmap.isRecycled()) {
                            F_GetBitmap.bitmap = null;
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
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
            initBaseAdapter();
    }
    public void initBaseAdapter() {
        base = new baseAdapter(this);
        listview.setAdapter(base);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {


                LinearLayout view1 = (LinearLayout) arg1;
                LinearLayout ll = (LinearLayout) view1.getChildAt(1);
                LinearLayout l = (LinearLayout) ll.getChildAt(0);
                LinearLayout l2 = (LinearLayout) ll.getChildAt(1);
                TextView text = (TextView) l.getChildAt(0);
                TextView text2 = (TextView) l2.getChildAt(0);
                String mes = text.getText().toString();
                String mes2 = text2.getText().toString();

                Intent it=new Intent(CommunitySearchActivity.this,CommunityDetailActivity.class);
                it.putExtra("name", mes);
                it.putExtra("id", id[arg2]);
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
                convertView = mInflater.inflate(R.layout.shetuan_sousuo_list, null);
                myViews.image = (ImageView) convertView.findViewById(R.id.shetuan_image_sousuo);
                myViews.name = (TextView) convertView.findViewById(R.id.shetuan_name_sousuo);
                myViews.id = (TextView) convertView.findViewById(R.id.shetuan_id_sousuo);
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
}
