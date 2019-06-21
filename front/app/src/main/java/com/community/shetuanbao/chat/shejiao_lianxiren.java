package com.community.shetuanbao.chat;
import io.rong.imkit.RongIM;
import io.rong.imkit.RongIM.UserInfoProvider;
import io.rong.imlib.model.UserInfo;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.community.shetuanbao.utils.F_GetBitmap;
import com.community.shetuanbao.utils.GetFriendInfo;
import com.community.shetuanbao.utils.RefreshableView;
import com.community.shetuanbao.R;
public class shejiao_lianxiren extends  Fragment  implements OnItemClickListener, UserInfoProvider {
    public  static List<Map<String, Object>> list=null;
    private List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
    public static ArrayList<Integer> xuehao=null;
    private String name[];
    private String pen[];
    private String picname[];
    private Integer[] sno;
    private Bitmap bitmap[];
    private  byte[] byteArray;
    private byte[][] bb;
    public static int x=0;
    private ListView listView=null;
    private List<Friend> userInfo;
    private String user[];
    private RefreshableView rd=null;
    public static baseAdapter ba=null;
    private TextView rl;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lianxiren_shejiao, container,false);
        rd=(RefreshableView)view.findViewById(R.id.refreshable_view);
        listView=view.findViewById(R.id.list);
        rl=(TextView)view.findViewById(R.id.meiyoulianxiren);
        list=new  ArrayList<Map<String, Object>>();
        userInfo=new ArrayList<Friend>();
        init();
        RongIM.setUserInfoProvider(this, true);
        rd.setOnRefreshListener(new com.community.shetuanbao.utils.RefreshableView.PullToRefreshListener() {
            @Override
            public void onRefresh() {
                try {
                    Thread.sleep((long) ((Math.random()+1)*1000));
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                rd.finishRefreshing();
            }
        }, 0);
        return view;
    }
    @SuppressLint("NewApi")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    Handler mHandler=new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            switch(msg.what)
            {
                case 0:
                    initList();
                    break;
                case 1:
                    listView.setVisibility(View.GONE);
                    rl.setVisibility(View.VISIBLE);
                    break;
                default:break;
            }
        }
    };
    public void init()
    {
        new Thread()
        {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void run()
            {
                try {
                    GetFriendInfo.getfriends();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                name=GetFriendInfo.name;
                pen=GetFriendInfo.pen;
                picname=GetFriendInfo.photo;
                bitmap=new Bitmap[name.length];
                sno=new Integer[name.length];
                sno=GetFriendInfo.id;
                xuehao=new ArrayList<Integer>();
                for(int i=0;i<sno.length;i++)
                {
                    xuehao.add(sno[i]);
                }
                if(name[0].equals("")){

                    mHandler.sendEmptyMessage(1);
                }else{
                    for(int i=0;i<picname.length;i++)
                    {
                        if(F_GetBitmap.isEmpty(picname[i]))
                        {
                            byte[] b=GetFriendInfo.getImage2(picname[i]);
                            F_GetBitmap.setInSDBitmap(b, picname[i]);
                            InputStream input = null;
                            BitmapFactory.Options options = new BitmapFactory.Options();
                            options.inSampleSize = 1;
                            input = new ByteArrayInputStream(b);
                            @SuppressWarnings({ "rawtypes", "unchecked" })
                            SoftReference softRef = new SoftReference(BitmapFactory.decodeStream(
                                    input, null, options));
                            bitmap[i] =(Bitmap) softRef.get();
                        }
                        else
                        {
                            bitmap[i]=F_GetBitmap.getSDBitmap(picname[i]);
                            if(F_GetBitmap.bitmap!=null && !F_GetBitmap.bitmap.isRecycled())
                            {
                                F_GetBitmap.bitmap = null;
                            }
                        }
                        userInfo.add(new Friend(sno[i],name[i],"file:///"+Environment.getExternalStorageDirectory().toString()+"/download_test/"+picname[i]));
                    }
                    initListEx();
                }
            }
        }.start();
    }
    private void initListEx()
    {
        for(int i=0;i<name.length;i++)
        {
            Map<String,Object> map=new HashMap<String,Object>();
            map.put("name", name[i]);
            map.put("pen", pen[i]);
            map.put("photo",bitmap[i]);
            list.add(map);
        }
        initAdp();
    }
    public void initAdp()
    {
        Message msg=new Message();
        msg.what=0;
        mHandler.sendMessage(msg);
    }
    public void initList()
    {
        ba=new baseAdapter(getActivity());
        listView.setAdapter(ba);
        listView.setOnItemClickListener(this);
    }
    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        @SuppressWarnings("unchecked")
        HashMap<String,Object> t = (HashMap<String,Object>) arg0.getItemAtPosition(arg2);
        Intent it = new Intent(this.getActivity(),lianxiren_message.class);
        Bundle bundle = new Bundle();
        bundle.putInt("sno", xuehao.get(arg2));
        it.putExtras(bundle);
        x=arg2;
        System.out.println("shuzishi+"+x);
        startActivity(it);
    }
    private  class baseAdapter extends BaseAdapter{
        public LayoutInflater mInflater=null;
        public baseAdapter(Context context)
        {
            mInflater = LayoutInflater.from(context);
            data.addAll(list);
        }
        @Override
        public int getCount(){
            return data.size();
        }
        @Override
        public Object getItem(int position)
        {
            return data.get(position);
        }
        @Override
        public long getItemId(int position)
        {
            return position;
        }
        @Override
        public View getView(int position,View convertView,ViewGroup partent)
        {
            ViewHolder myViews;
            if (convertView == null) {
                myViews = new ViewHolder();
                convertView = getActivity().getLayoutInflater().inflate(R.layout.lianxiren_list, null);
                myViews.name=(TextView)convertView.findViewById(R.id.shejiao_lianxiren_friend);
                myViews.pen=(TextView)convertView.findViewById(R.id.shejiao_lianxiren_pen);
                myViews.photo=(ImageView)convertView.findViewById(R.id.shejiao_lianxiren_touxiang);
                convertView.setTag(myViews);
            } else {
                myViews = (ViewHolder) convertView.getTag();
            }
            myViews.name.setText((String)data.get(position).get("name"));
            myViews.pen.setText((String)data.get(position).get("pen"));
            myViews.photo.setImageBitmap((Bitmap) data.get(position).get("photo"));
            return convertView;
        }
    }
    static class ViewHolder {
        private ImageView photo;
        private TextView name;
        private TextView pen;
    }

    public static Bitmap zoomBitmap(Bitmap target)
    {
        int width = target.getWidth();
        int height = target.getHeight();
        Matrix matrix = new Matrix();
        float scaleWidth = ((float)160)/ width;
        float scaleHeight = ((float)160)/ height;
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap result = Bitmap.createBitmap(target, 0, 0, width,
                height, matrix, true);
        return result;
    }
    @Override
    public UserInfo getUserInfo(String s) {
        for (Friend i : userInfo) {
            if (i.getUserId().equals(s)) {
                return new UserInfo(i.getUserId(),i.getUserName(), Uri.parse(i.getPortraitUri()));
            }
        }
        return null;
    }
}