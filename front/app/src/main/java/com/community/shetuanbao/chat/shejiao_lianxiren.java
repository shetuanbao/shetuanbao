package com.community.shetuanbao.chat;

import android.annotation.SuppressLint;
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
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.community.shetuanbao.R;
import com.community.shetuanbao.utils.F_GetBitmap;
import com.community.shetuanbao.utils.GetFriendInfo;
import com.community.shetuanbao.utils.RefreshableView;

import org.json.JSONException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.rong.imkit.RongIM;
import io.rong.imlib.model.UserInfo;

public class shejiao_lianxiren extends Fragment implements AdapterView.OnItemClickListener, RongIM.UserInfoProvider {
    public static List<Map<String, Object>> list = null;//联系人的列表
    public static List<String> xuehao = null;//学号列表
    private List<String> name;//名字数组
    private List<String> pen;//签名数组
    private List<String> picname;
    private List<String> sno;
    private Bitmap bitmap[];
    private byte[] byteArray;
    private byte[][] bb;
    public static int x = 0;
    private ListView listView = null;
    private List<Friend> userInfo;
    private String user[];
    private RefreshableView rd = null;
    public static baseAdapter ba;
    private TextView rl;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lianxiren_shejiao, container, false);
        listView =  view.findViewById(android.R.id.list);
        rd =  view.findViewById(R.id.refreshable_view);
        rl =  view.findViewById(R.id.meiyoulianxiren);
        list = new ArrayList<Map<String, Object>>();
        userInfo = new ArrayList<Friend>();//得到朋友信息
        // user = Constant.userToken.split("#");
        //userInfo.add(new Friend(user[0], user[1], user[2]));
        init();
        RongIM.setUserInfoProvider(this, true);
        rd.setOnRefreshListener(new com.community.shetuanbao.utils.RefreshableView.PullToRefreshListener(){
            @Override
            public void onRefresh() {
                try {
                    Thread.sleep((long) ((Math.random() + 1) * 1000));
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                rd.finishRefreshing();//下拉更新的设置
            }
        }, 0);
        return view;
    }

    @SuppressLint("NewApi")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    initList();
                    break;
                case 1:
                    listView.setVisibility(View.GONE);
                    rl.setVisibility(View.VISIBLE);
                    break;
                default:
                    break;
            }
        }
    };

    public void init() {
        new Thread() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void run() {
                try {
                    name=GetFriendInfo.getFriendNames();
                    pen=GetFriendInfo.getFriendPens();
                    picname=GetFriendInfo.getFriendPhotos();
                    sno=GetFriendInfo.getUserIds();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            //获取用户信息
                xuehao = new ArrayList<String>();
                for (int i = 0; i < sno.size(); i++) {
                    xuehao.add(sno.get(i));
                }
                if (name.get(0).equals("")) {

                    mHandler.sendEmptyMessage(1);
                } else {
                    for (int i = 0; i < picname.size(); i++) {
                        if (F_GetBitmap.isEmpty(picname.get(i))) {
                            byte[] b = GetFriendInfo.getImage(picname.get(i));
                            F_GetBitmap.setInSDBitmap(b, picname.get(i));
                            InputStream input = null;
                            BitmapFactory.Options options = new BitmapFactory.Options();
                            options.inSampleSize = 1;
                            input = new ByteArrayInputStream(b);
                            @SuppressWarnings({"rawtypes", "unchecked"})
                            SoftReference softRef = new SoftReference(BitmapFactory.decodeStream(
                                    input, null, options));
                            bitmap[i] = (Bitmap) softRef.get();
                        } else {
                            bitmap[i] = F_GetBitmap.getSDBitmap(picname.get(i));
                            if (F_GetBitmap.bitmap != null && !F_GetBitmap.bitmap.isRecycled()) {
                                F_GetBitmap.bitmap = null;
                            }
                        }
                        userInfo.add(new Friend(sno.get(i), name.get(i), "file:///" + Environment.getExternalStorageDirectory().toString() + "/download_test/" + picname.get(i)));
                    }
                    initListEx();
                    initAdp();
                }
            }
        }.start();
    }

    private void initListEx() {
        for (int i = 0; i < name.size(); i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("name", name.get(i));
            map.put("pen", pen.get(i));
            map.put("photo", bitmap[i]);
            list.add(map);
        }
    }

    public void initAdp() {
        Message msg = new Message();
        msg.what = 0;
        mHandler.sendMessage(msg);
    }

    public void initList() {
        ba = new baseAdapter(this.getActivity());
        listView.setAdapter(ba);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        @SuppressWarnings("unchecked")
        HashMap<String, Object> t = (HashMap<String, Object>) arg0.getItemAtPosition(arg2);
        Intent it = new Intent(getActivity(), lianxiren_message.class);
        Bundle bundle = new Bundle();
        bundle.putString("sno", xuehao.get(arg2));
        it.putExtras(bundle);
        x = arg2;
        System.out.println("shuzishi+" + x);
        startActivity(it);
    }

    public class baseAdapter extends BaseAdapter {
        private LayoutInflater mInflater = null;

        public baseAdapter(Context context) {
            mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup partent) {
            convertView = getActivity().getLayoutInflater().inflate(
                    R.layout.lianxiren_list, null);
            TextView tv =  convertView.findViewById(R.id.shejiao_lianxiren_friend);
            TextView tv2 =  convertView.findViewById(R.id.shejiao_lianxiren_pen);
            ImageView imageView =  convertView.findViewById(R.id.shejiao_lianxiren_touxiang);
            tv.setText((String) list.get(position).get("name"));
            tv2.setText((String) list.get(position).get("pen"));
            imageView.setImageBitmap((Bitmap) list.get(position).get("photo"));
            return convertView;
        }
    }

    public static Bitmap zoomBitmap(Bitmap target) {
        int width = target.getWidth();
        int height = target.getHeight();
        Matrix matrix = new Matrix();
        float scaleWidth = ((float) 160) / width;
        float scaleHeight = ((float) 160) / height;
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap result = Bitmap.createBitmap(target, 0, 0, width,
                height, matrix, true);
        return result;
    }

    @Override
    public UserInfo getUserInfo(String s) {
        for (Friend i : userInfo) {
            if (i.getUserId().equals(s)) {
                return new UserInfo(i.getUserId(), i.getUserName(), Uri.parse(i.getPortraitUri()));
            }
        }
        return null;
    }
}
