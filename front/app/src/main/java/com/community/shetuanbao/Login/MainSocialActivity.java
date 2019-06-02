package com.community.shetuanbao.Login;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Looper;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.community.shetuanbao.R;
import com.community.shetuanbao.chat.CaptureActivity;
import com.community.shetuanbao.chat.FragmentAdapter;
import com.community.shetuanbao.chat.MyApp;
import com.community.shetuanbao.chat.shejiao_lianxiren;
import com.community.shetuanbao.chat.sousuo_lianxiren;
import com.community.shetuanbao.community.CommunityStaffActivity;
import com.community.shetuanbao.utils.Exit;
import com.community.shetuanbao.utils.RequestUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;

public class MainSocialActivity extends FragmentActivity {

    private ViewPager viewPager;//椤靛崱鍐呭
    private ImageView imageView;// 鍔ㄧ敾鍥剧墖
    private TextView textView1,textView2;
    private List<Fragment> views;// Tab椤甸潰鍒楄〃
    private int offset = 0;// 鍔ㄧ敾鍥剧墖鍋忕Щ閲�
    private int currIndex = 0;// 褰撳墠椤靛崱缂栧彿
    private int bmpW;// 鍔ㄧ敾鍥剧墖瀹藉害
    private Fragment  mFriendFg=null;
    private Fragment mCoversationList;
    private SharedPreferences st;
    ImageView school;
    ImageView sousuo=null;
    String scanResult=null;
    @RequiresApi(api = Build.VERSION_CODES.DONUT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_social);
        Exit.getInstance().addActivities(this);

        sousuo=(ImageView)findViewById(R.id.main_searc_image);
        sousuo.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                showPopupMenu(sousuo);
            }
        });
        school=(ImageView)findViewById(R.id.school_3);
        school.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent=new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://www.scut.edu.cn"));
                startActivity(intent);
            }
        });
//        mCoversationList=initConversation();
        st=this.getSharedPreferences("token",MODE_PRIVATE);
        if(st.getBoolean("CHECK", false))
        {

            connect(st.getString("TOKEN", ""));
        }
        else
        {
            getToken("2");
        }
        InitViewPager();
        InitImageView();
        InitTextView();
    }
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);

        popupMenu.getMenuInflater().inflate(R.menu.main, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_new:
                        Intent it=new Intent(MainSocialActivity.this,sousuo_lianxiren.class);
                        startActivity(it);
                        break;
                    case R.id.action_open:
                        Intent openCameraIntent = new Intent(MainSocialActivity.this,CaptureActivity.class);
                        startActivityForResult(openCameraIntent, 0);

                        break;
                }
                return false;
            }
        });

        popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(PopupMenu menu) {
                //Toast.makeText(getApplicationContext(), "鍏抽棴PopupMenu", Toast.LENGTH_SHORT).show();
            }
        });

        popupMenu.show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            scanResult = bundle.getString("result");
            Intent intent=new Intent(MainSocialActivity.this,CommunityStaffActivity.class);
        }


    }
    private void getToken(String userId)
    {
        new Thread(new Runnable()
        {
            @RequiresApi(api = Build.VERSION_CODES.DONUT)
            @Override
            public void run()
            {
//                String token=NetInfoUtil.getToken(Constant.userToken);
                Map<String,Object> params=new HashMap<>();
                params.put("userId",2);
                String res = null;
                try {
                    res = RequestUtils.post("/users/getToken",params);
                    Log.d("response",res);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    JSONObject jsonObject = new JSONObject(res);
                    if (jsonObject.getInt("code") == 200) {
                        // 注意获取到的数据的数据类型，在后台是数组，则这里是JSONArray，在后台是类，则这里是JSONObject
                        String token=jsonObject.getString("data");
                        SharedPreferences.Editor editor = st.edit();
                        editor.putBoolean("CHECK", true);
                        editor.putString("TOKEN",token);
                        editor.commit();
                        connect(token);
                    } else {
                        Looper.prepare();
                        Toast.makeText(MainSocialActivity.this, "获取信息失败", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }).start();

    }
    private void InitViewPager() {
        viewPager=(ViewPager) findViewById(R.id.vPager);
        views=new ArrayList<Fragment>();
        views.add(new shejiao_lianxiren());
        views.add(new ConversationListFragment());
        Log.d("numofPage:", String.valueOf(views.size()));
        viewPager.setAdapter(new FragmentAdapter(
                this.getSupportFragmentManager(), views));
        viewPager.setCurrentItem(0);
        viewPager.setOnPageChangeListener((ViewPager.OnPageChangeListener) new MyOnPageChangeListener());
    }
    /**
     *  鍒濆鍖栧ご鏍�
     */

    private void InitTextView() {
        textView1 = (TextView) findViewById(R.id.text1);
        textView2 = (TextView) findViewById(R.id.text2);
        textView1.setOnClickListener((View.OnClickListener) new MyOnClickListener(0));
        textView2.setOnClickListener((View.OnClickListener) new MyOnClickListener(1));

    }

    /**
     2      * 鍒濆鍖栧姩鐢伙紝杩欎釜灏辨槸椤靛崱婊戝姩鏃讹紝涓嬮潰鐨勬í绾夸篃婊戝姩鐨勬晥鏋滐紝鍦ㄨ繖閲岄渶瑕佽绠椾竴浜涙暟鎹�
     3 */

    private void InitImageView() {
        imageView= (ImageView) findViewById(R.id.cursor);
        bmpW = BitmapFactory.decodeResource(getResources(), R.mipmap.d).getWidth();
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenW = dm.widthPixels;
        offset = (screenW / 2 - bmpW) / 2;
        Matrix matrix = new Matrix();
        matrix.postTranslate(offset, 0);
        imageView.setImageMatrix(matrix);
    }
    private class MyOnClickListener implements View.OnClickListener {
        private int index=1;
        public MyOnClickListener(int i){
            index=i;
        }
        @Override
        public void onClick(View v) {
            viewPager.setCurrentItem(index);
        }

    }
    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener
    {

        int one = offset * 2 + bmpW;
        @Override
        public void onPageScrollStateChanged(int arg0)
        {

        }
        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2)
        {

        }

        @Override
        public void onPageSelected(int arg0) {
            Animation animation = new TranslateAnimation(one*currIndex, one*arg0, 0, 0);//鏄剧劧杩欎釜姣旇緝绠�娲侊紝鍙湁涓�琛屼唬鐮併��
            currIndex = arg0;
            animation.setFillAfter(true);
            animation.setDuration(120);
            imageView.startAnimation(animation);
        }

    }
    @RequiresApi(api = Build.VERSION_CODES.DONUT)
    private Fragment initConversation()
    {
        if (mCoversationList == null) {
//            ConversationListFragment listFragment = ConversationListFragment.getInstance();
            ConversationListFragment listFragment = new ConversationListFragment();
            Uri uri = Uri.parse("rong://" + getApplicationInfo().packageName).buildUpon()
                    .appendPath("conversationlist")
                    .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false") //璁剧疆绉佽亰浼氳瘽鏄惁鑱氬悎鏄剧ず
                    .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "false")//缇ょ粍
                    .appendQueryParameter(Conversation.ConversationType.DISCUSSION.getName(), "false")//璁ㄨ缁�
                    .appendQueryParameter(Conversation.ConversationType.PUBLIC_SERVICE.getName(), "false")//鍏叡鏈嶅姟鍙�
                    .appendQueryParameter(Conversation.ConversationType.APP_PUBLIC_SERVICE.getName(), "false")//璁㈤槄鍙�
                    .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "false")//绯荤粺
                    .build();
            listFragment.setUri(uri);
            return listFragment;
        }else
        {
            return mCoversationList;
        }
    }
    @Override
    @SuppressWarnings("deprecation")
    public boolean onKeyDown(int keyCode, KeyEvent event)      //锟斤拷写锟斤拷锟截硷拷
    {
        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            AlertDialog isExit=new AlertDialog.Builder(this).create();
            isExit.setTitle("绯荤粺鎻愮ず");
            isExit.setMessage("纭畾閫�鍑哄悧锛�");
            isExit.setButton("纭畾",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Exit.exitActivity();
                        }
                    }
            );
            isExit.setButton2("鍙栨秷",
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
    @RequiresApi(api = Build.VERSION_CODES.DONUT)
    private void connect(String token) {

        if (getApplicationInfo().packageName.equals(MyApp.getCurProcessName(getApplicationContext()))) {

            RongIM.connect(token, new RongIMClient.ConnectCallback() {

                @Override
                public void onTokenIncorrect() {

                    InitViewPager();
                }

                @Override
                public void onSuccess(String userid) {
                    setConnectedListener();
                    InitViewPager();
                    Log.d("message","连接成功");
                }

                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {

                    InitViewPager();
                    Log.d("message","连接失败");
                }
            });
        }
    }

    public void setConnectedListener() {

//        InputProvider.ExtendProvider[] singleProvider = {
//                new ImageInputProvider(RongContext.getInstance()),//鍥剧墖
//                new CameraInputProvider(RongContext.getInstance()),//閿熸枻鎷烽敓锟�
//
//        };
//
//        InputProvider.ExtendProvider[] muiltiProvider = {
//                new ImageInputProvider(RongContext.getInstance()),//鍥剧墖
//                new CameraInputProvider(RongContext.getInstance()),//閿熸枻鎷烽敓锟�
//        };
//
//        RongIM.getInstance();
//        RongIM.resetInputExtensionProvider(Conversation.ConversationType.PRIVATE, singleProvider);
//        RongIM.getInstance();
//        RongIM.resetInputExtensionProvider(Conversation.ConversationType.DISCUSSION, muiltiProvider);
//        RongIM.getInstance();
//        RongIM.resetInputExtensionProvider(Conversation.ConversationType.CUSTOMER_SERVICE, muiltiProvider);
//        RongIM.getInstance();
//        RongIM.resetInputExtensionProvider(Conversation.ConversationType.GROUP, muiltiProvider);
    }

}
//
//package com.community.shetuanbao.Login;
//
//        import io.rong.imkit.RongContext;
//        import io.rong.imkit.RongIM;
//        import io.rong.imkit.fragment.ConversationListFragment;
////import io.rong.imkit.widget.provider.RecallMessageItemProvider;
////import io.rong.imkit.widget.provider.CameraInputProvider;
////import io.rong.imkit.widget.provider.ImageInputProvider;
////import io.rong.imkit.widget.provider.InputProvider;
//        import io.rong.imlib.RongIMClient;
//        import io.rong.imlib.model.Conversation;
//
//        import java.util.ArrayList;
//        import java.util.List;
//
//        import android.app.AlertDialog;
//        import android.content.DialogInterface;
//        import android.content.Intent;
//        import android.content.SharedPreferences;
//        import android.content.SharedPreferences.Editor;
//        import android.graphics.BitmapFactory;
//        import android.graphics.Matrix;
//        import android.net.Uri;
//        import android.os.Bundle;
//        import android.support.v4.app.Fragment;
//        import android.support.v4.app.FragmentActivity;
//        import android.support.v4.view.ViewPager;
//        import android.support.v4.view.ViewPager.OnPageChangeListener;
//        import android.support.v7.widget.PopupMenu;
//        import android.util.DisplayMetrics;
//        import android.view.KeyEvent;
//        import android.view.MenuItem;
//        import android.view.View;
//        import android.view.View.OnClickListener;
//        import android.view.animation.Animation;
//        import android.view.animation.TranslateAnimation;
//        import android.widget.ImageView;
//        import android.widget.TextView;
//
//        import com.community.shetuanbao.chat.CaptureActivity;
//        import com.community.shetuanbao.chat.FragmentAdapter;
//        import com.community.shetuanbao.R;
//        import com.community.shetuanbao.chat.MyApp;
//        import com.community.shetuanbao.chat.shejiao_lianxiren;
//        import com.community.shetuanbao.chat.sousuo_lianxiren;
//        import com.community.shetuanbao.community.CommunityStaffActivity;
//        import com.community.shetuanbao.utils.Exit;
//
//
//public class MainSocialActivity extends FragmentActivity {
//
//    private ViewPager viewPager;//椤靛崱鍐呭
//    private ImageView imageView;// 鍔ㄧ敾鍥剧墖
//    private TextView textView1,textView2;
//    private List<Fragment> views;// Tab椤甸潰鍒楄〃
//    private int offset = 0;// 鍔ㄧ敾鍥剧墖鍋忕Щ閲�
//    private int currIndex = 0;// 褰撳墠椤靛崱缂栧彿
//    private int bmpW;// 鍔ㄧ敾鍥剧墖瀹藉害
//    private Fragment  mFriendFg=null;
//    private Fragment mCoversationList;
//    private SharedPreferences st;
//    ImageView school;
//    ImageView sousuo=null;
//    String scanResult=null;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main_social);
//        Exit.getInstance().addActivities(this);
//
//        sousuo=(ImageView)findViewById(R.id.main_searc_image);
//        sousuo.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//                Intent it=new Intent(MainSocialActivity.this,sousuo_lianxiren.class);
//                startActivity(it);
//                //showPopupMenu(sousuo);
//            }
//        });
//        school=(ImageView)findViewById(R.id.school_3);
//        school.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//                Intent intent=new Intent(Intent.ACTION_VIEW);
//                intent.setData(Uri.parse("http://www.ncst.edu.cn"));
//                startActivity(intent);
//            }
//        });
//        mCoversationList=initConversation();
//        st=this.getSharedPreferences("token",MODE_WORLD_READABLE);
//        if(st.getBoolean("CHECK", false))
//        {
//
//            connect(st.getString("TOKEN", ""));
//        }
//        else
//        {
////            getToken(Constant.userName);
//        }
//        InitImageView();
//        InitTextView();
//    }
//    private void showPopupMenu(View view) {
//        PopupMenu popupMenu = new PopupMenu(this, view);
//        popupMenu.getMenuInflater().inflate(R.menu.main, popupMenu.getMenu());
//        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                switch (item.getItemId()) {
//                    case R.id.action_new:
//                        Intent it=new Intent(MainSocialActivity.this,sousuo_lianxiren.class);
//                        startActivity(it);
//                        break;
//                    case R.id.action_open:
//                        Intent openCameraIntent = new Intent(MainSocialActivity.this,CaptureActivity.class);
//                        startActivityForResult(openCameraIntent, 0);
//
//                        break;
//                }
//                return false;
//            }
//        });
//
//        popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
//            @Override
//            public void onDismiss(PopupMenu menu) {
//            }
//        });
//
//        popupMenu.show();
//    }
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (resultCode == RESULT_OK) {
//            Bundle bundle = data.getExtras();
//            scanResult = bundle.getString("result");
//            Intent intent=new Intent(MainSocialActivity.this,CommunityStaffActivity.class);
//        }
//
//
//    }
//    private void getToken(String userId)
//    {
//        new Thread(new Runnable()
//        {
//            @Override
//            public void run()
//            {
////                String token=NetInfoUtil.getToken(Constant.userToken);
//                String token=null;
//                Editor editor = st.edit();
//                editor.putBoolean("CHECK", true);
//                editor.putString("TOKEN",token);
//                editor.commit();
//                connect(token);
//            }
//
//        }).start();
//
//    }
//    private void InitViewPager() {
//        viewPager=(ViewPager) findViewById(R.id.vPager);
//        views=new ArrayList<Fragment>();
//        views.add(new shejiao_lianxiren());
//        views.add(mCoversationList);
//        viewPager.setAdapter(new FragmentAdapter(
//                this.getSupportFragmentManager(), views));
//        viewPager.setCurrentItem(0);
//        viewPager.setOnPageChangeListener(new MyOnPageChangeListener());
//    }
//
//    private void InitTextView() {
//        textView1 = (TextView) findViewById(R.id.text1);
//        textView2 = (TextView) findViewById(R.id.text2);
//        textView1.setOnClickListener(new MyOnClickListener(0));
//        textView2.setOnClickListener(new MyOnClickListener(1));
//
//    }
//
//    private void InitImageView() {
//        imageView= (ImageView) findViewById(R.id.cursor);
//        bmpW = BitmapFactory.decodeResource(getResources(), R.mipmap.d).getWidth();// 鑾峰彇鍥剧墖瀹藉害
//        DisplayMetrics dm = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(dm);
//        int screenW = dm.widthPixels;// 鑾峰彇鍒嗚鲸鐜囧搴�
//        offset = (screenW / 2 - bmpW) / 2;// 璁＄畻鍋忕Щ閲�
//        Matrix matrix = new Matrix();
//        matrix.postTranslate(offset, 0);
//        imageView.setImageMatrix(matrix);// 璁剧疆鍔ㄧ敾鍒濆浣嶇疆
//    }
//    private class MyOnClickListener implements OnClickListener{
//        private int index=0;
//        public MyOnClickListener(int i){
//            index=i;
//        }
//        @Override
//        public void onClick(View v) {
//            viewPager.setCurrentItem(index);
//        }
//
//    }
//    public class MyOnPageChangeListener implements OnPageChangeListener
//    {
//
//        int one = offset * 2 + bmpW;// 椤靛崱1 -> 椤靛崱2 鍋忕Щ閲�
//        @Override
//        public void onPageScrollStateChanged(int arg0)
//        {
//
//        }
//        @Override
//        public void onPageScrolled(int arg0, float arg1, int arg2)
//        {
//
//        }
//
//        @Override
//        public void onPageSelected(int arg0) {
//            Animation animation = new TranslateAnimation(one*currIndex, one*arg0, 0, 0);//鏄剧劧杩欎釜姣旇緝绠�娲侊紝鍙湁涓�琛屼唬鐮併��
//            currIndex = arg0;
//            animation.setFillAfter(true);// True:鍥剧墖鍋滃湪鍔ㄧ敾缁撴潫浣嶇疆
//            animation.setDuration(120);
//            imageView.startAnimation(animation);
//        }
//
//    }
//    private Fragment initConversation()
//    {
//        if (mCoversationList == null) {
////            ConversationListFragment listFragment = ConversationListFragment.getInstance();
//            ConversationListFragment listFragment = new ConversationListFragment();
//            Uri uri = Uri.parse("rong://" + getApplicationInfo().packageName).buildUpon()
//                    .appendPath("conversationlist")
//                    .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false") //璁剧疆绉佽亰浼氳瘽鏄惁鑱氬悎鏄剧ず
//                    .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "false")//缇ょ粍
//                    .appendQueryParameter(Conversation.ConversationType.DISCUSSION.getName(), "false")//璁ㄨ缁�
//                    .appendQueryParameter(Conversation.ConversationType.PUBLIC_SERVICE.getName(), "false")//鍏叡鏈嶅姟鍙�
//                    .appendQueryParameter(Conversation.ConversationType.APP_PUBLIC_SERVICE.getName(), "false")//璁㈤槄鍙�
//                    .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "false")//绯荤粺
//                    .build();
//            listFragment.setUri(uri);
//            return listFragment;
//        }else
//        {
//            return mCoversationList;
//        }
//
//    }
//    @Override
//    @SuppressWarnings("deprecation")
//    public boolean onKeyDown(int keyCode, KeyEvent event)      //锟斤拷写锟斤拷锟截硷拷
//    {
//        if(keyCode == KeyEvent.KEYCODE_BACK)
//        {
//            AlertDialog isExit=new AlertDialog.Builder(this).create();
//            isExit.setTitle("绯荤粺鎻愮ず");
//            isExit.setMessage("纭畾閫�鍑哄悧锛�");
//            isExit.setButton("纭畾",
//                    new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            Exit.exitActivity();
//                        }
//                    }
//            );
//            isExit.setButton2("鍙栨秷",
//                    new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                        }
//                    }
//            );
//            isExit.show();
//        }
//        return true;
//    }
//    private void connect(String token) {
//
//        if (getApplicationInfo().packageName.equals(MyApp.getCurProcessName(getApplicationContext()))) {
//
//            /**
//             * IMKit SDK璋冪敤绗簩姝�,寤虹珛涓庢湇鍔″櫒鐨勮繛鎺�
//             */
//            RongIM.connect(token, new RongIMClient.ConnectCallback() {
//
//                /**
//                 * Token 閿欒锛屽湪绾夸笂鐜涓嬩富瑕佹槸鍥犱负 Token 宸茬粡杩囨湡锛屾偍闇�瑕佸悜 App Server 閲嶆柊璇锋眰涓�涓柊鐨� Token
//                 */
//                @Override
//                public void onTokenIncorrect() {
//
//                    InitViewPager();
//                }
//
//                /**
//                 * 杩炴帴铻嶄簯鎴愬姛
//                 * @param userid 褰撳墠 token
//                 */
//                @Override
//                public void onSuccess(String userid) {
//
//                    setConnectedListener();
//                    InitViewPager();
//                }
//
//                /**
//                 * 杩炴帴铻嶄簯澶辫触
//                 * @param errorCode 閿欒鐮侊紝鍙埌瀹樼綉 鏌ョ湅閿欒鐮佸搴旂殑娉ㄩ噴
//                 *                  http://www.rongcloud.cn/docs/android.html#甯歌閿欒鐮�
//                 */
//                @Override
//                public void onError(RongIMClient.ErrorCode errorCode) {
//
//                    InitViewPager();
//                }
//            });
//        }
//    }
//
//    public void setConnectedListener() {
//
////        InputProvider.ExtendProvider[] singleProvider = {
////                new ImageInputProvider(RongContext.getInstance()),//鍥剧墖
////                new CameraInputProvider(RongContext.getInstance()),//閿熸枻鎷烽敓锟�
////
////        };
////
////        InputProvider.ExtendProvider[] muiltiProvider = {
////                new ImageInputProvider(RongContext.getInstance()),//鍥剧墖
////                new CameraInputProvider(RongContext.getInstance()),//閿熸枻鎷烽敓锟�
////        };
////
////        RongIM.getInstance();
////        RongIM.resetInputExtensionProvider(Conversation.ConversationType.PRIVATE, singleProvider);
////        RongIM.getInstance();
////        RongIM.resetInputExtensionProvider(Conversation.ConversationType.DISCUSSION, muiltiProvider);
////        RongIM.getInstance();
////        RongIM.resetInputExtensionProvider(Conversation.ConversationType.CUSTOMER_SERVICE, muiltiProvider);
////        RongIM.getInstance();
////        RongIM.resetInputExtensionProvider(Conversation.ConversationType.GROUP, muiltiProvider);
//    }
//
//}

