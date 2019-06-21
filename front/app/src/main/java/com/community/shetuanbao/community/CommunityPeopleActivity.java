package com.community.shetuanbao.community;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.community.shetuanbao.R;
import com.community.shetuanbao.utils.FontManager;
import com.community.shetuanbao.utils.RequestUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CommunityPeopleActivity extends Activity implements AdapterView.OnItemClickListener {
    List<String[]> namey=new ArrayList<String[]>();
    private List<Map<String,Object>> data=new ArrayList<Map<String,Object>>();
    private List<Map<String,Object>> listItem=new ArrayList<Map<String,Object>>();
    private String[] name1=null;
    private String[][] all=null;
    private int sno[]=null;
    private int communityid;
    private baseAdapter base=null;
    private ListView listview = null;
    public static TextView text=null;
    public static TextView fanhui=null;
    Map<String, Object> params;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.communitypeopleactivity);
        fanhui=(TextView)findViewById(R.id.shetuanrenyuani_text1);
        fanhui.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                finish();
            }
        });
        thread_ntd th=new thread_ntd();
        th.start();
        try {
            th.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    private class thread_ntd extends Thread
    {
        @Override
        public void run()
        {

            String s=CommunityDetailActivity.s;
            try {
                params = new HashMap<>();
                params.put("communityName", s);
                String res = RequestUtils.post("/community/panfindByCommunityName", params);
                try {
                    JSONObject jsonObject = new JSONObject(res);
                    if (jsonObject.getInt("code") == 200) {
                        // 注意获取到的数据的数据类型，在后台是数组，则这里是JSONArray，在后台是类，则这里是JSONObject
                        communityid = jsonObject.getJSONObject("data").getInt("communityId");
                    } else {
                        Looper.prepare();
                        Toast.makeText(CommunityPeopleActivity.this, "获取信息失败", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                params = new HashMap<>();
                params.put("communityId", communityid);
                String res1 = RequestUtils.post("/community/panfindByCommunityUser", params);
                try {
                    JSONObject jsonObject = new JSONObject(res1);
                    if (jsonObject.getInt("code") == 200) {
                        // 注意获取到的数据的数据类型，在后台是数组，则这里是JSONArray，在后台是类，则这里是JSONObject
                        JSONArray list = (JSONArray) jsonObject.get("data");
                        sno=new int[list.length()];
                        for(int i=0;i<list.length();i++) {
                            sno[i] = list.getInt(i);
                        }
                    } else {
                        Looper.prepare();
                        Toast.makeText(CommunityPeopleActivity.this, "获取信息失败", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String res2 = RequestUtils.get("/users/list");
                try {
                    JSONObject jsonObject = new JSONObject(res2);
                    if (jsonObject.getInt("code") == 200) {
                        // 注意获取到的数据的数据类型，在后台是数组，则这里是JSONArray，在后台是类，则这里是JSONObject
                        JSONArray list = (JSONArray) jsonObject.get("data");
                        name1=new String[sno.length];
                        for (int i = 0; i < sno.length; i++) {
                            for(int j=0;j<list.length();j++){
                                if(sno[i]==list.getJSONObject(j).getInt("userId")){
                                    name1[i] = list.getJSONObject(j).getString("userName");
                                    break;
                                }
                            }
                        }
                        initList();
                    } else {
                        Looper.prepare();
                        Toast.makeText(CommunityPeopleActivity.this, "获取信息失败", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void initList()
    {
        for(int i=0;i<name1.length;i++)
        {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("name", name1[i]);
            listItem.add(map);
        }
        initBaseAdapter();
    }
    static class ViewHolder {

        private TextView  name;
    }
    public void initBaseAdapter()
    {
        base=new baseAdapter(this);
        listview=(ListView)findViewById(R.id.shetuancheng_text2);
        listview.setAdapter(base);
        listview.setOnItemClickListener(this);
    }
    @SuppressLint("HandlerLeak")
    Handler hd = new Handler()
    {
        @SuppressWarnings("deprecation")
        @SuppressLint("HandlerLeak")
        @Override
        public void handleMessage(Message msg) {
            showDialog(0);
        }
    };
    @SuppressWarnings("deprecation")
    @SuppressLint("InflateParams")
    private class baseAdapter extends BaseAdapter
    {
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
                convertView =mInflater.inflate(R.layout.chengyuan_list, null) ;

                myViews.name = (TextView) convertView.findViewById(R.id.chengyuan_name);
                convertView.setTag(myViews);
            }
            else {
                myViews = (ViewHolder ) convertView.getTag();

            }


            myViews.name.setText( (String) data.get(position).get("name"));
            myViews.name.setTypeface(FontManager.tf);
            return convertView;
        }
    }
    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        // TODO Auto-generated method stub
        Intent intent=new Intent(CommunityPeopleActivity.this,CommunityStaffActivity.class);
        Bundle bundle=new Bundle();
        bundle.putInt("sno", sno[arg2]);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}