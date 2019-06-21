package com.community.shetuanbao.Personal;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.community.shetuanbao.Login.LoginActivity;
import com.community.shetuanbao.R;
import com.community.shetuanbao.utils.ACache;
import com.community.shetuanbao.utils.Exit;
import com.community.shetuanbao.utils.F_GetBitmap;
import com.community.shetuanbao.utils.FontManager;
import com.community.shetuanbao.utils.RequestUtils;
import com.community.shetuanbao.utils.RoundImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

public class MainPersonalActivity extends Activity implements View.OnClickListener {
    private LinearLayout myself_background=null;
    private TextView shezhi=null;
    private TextView lianxi=null;
    private TextView yijianfankui=null;
    private TextView yonghubangzhu=null;
    private TextView gerenzhongxin=null;
    public static TextView information=null;
    public static RoundImageView photo=null;
    public static Button denglu=null;
    public static Button zhuce=null;
    private byte[] image;
    private String imageStr;
    private String nickname =null;
    private int name = 0;
    private Bitmap imageData;
    private TextView guanli=null;
    private String zhuangtai=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Exit.getInstance().addActivities(this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main_personal);
//        Exit.getInstance().addActivities(this);
        information=(TextView)findViewById(R.id.mine_userphoto_text);
        photo=(RoundImageView)findViewById(R.id.mine_userphoto_imagezhu);
        GetUserPictureThread th=new GetUserPictureThread();
        th.start();
        try{
            th.join();
        }catch(Exception e){
            e.printStackTrace();
        }
        photo.setImageBitmap(imageData);
        System.out.println("hhhhhhhhhhhhhhhhhhhhhh"+zhuangtai);
        if(zhuangtai.equals("1")){
            information.setText(nickname +" | ID:"+name);
        }else if(zhuangtai.equals("0")){
            information.setText(nickname +"|ID:"+name+"|账号被封禁");
        }

        shezhi=(TextView)findViewById(R.id.mine_shezhi);
        shezhi.setOnClickListener(this);
        lianxi=(TextView)findViewById(R.id.mine_lianxiwomen);
        lianxi.setOnClickListener(this);
        yonghubangzhu=(TextView)findViewById(R.id.mine_yonghubangzhu);
        yonghubangzhu.setOnClickListener(this);
        yijianfankui=(TextView)findViewById(R.id.mine_yijianfankui);
        yijianfankui.setOnClickListener(this);
//        guanli=(TextView)findViewById(R.id.mine_guanli);
//        guanli.setOnClickListener(this);
        FontManager.initTypeFace(this);
        FontManager.changeFonts(FontManager.getContentView(this),this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mine_shezhi:
                Intent intent1 = new Intent(MainPersonalActivity.this, PersonalSettingsActivity.class);
                intent1.putExtra("nickname", nickname);
                startActivity(intent1);
                break;
            case R.id.mine_lianxiwomen:
                Intent intent2 = new Intent(MainPersonalActivity.this, PersonalContactUsActivity.class);
                startActivity(intent2);
                break;
            case R.id.mine_yonghubangzhu:
                Intent intent3 = new Intent(MainPersonalActivity.this, PersonalHelpActivity.class);
                startActivity(intent3);
                break;
            case R.id.mine_yijianfankui:
                Intent intent4 = new Intent(MainPersonalActivity.this, PersonalAdviceActivity.class);
                startActivity(intent4);
                break;
//            case R.id.mine_guanli:
//                Intent intent5 = new Intent(MainPersonalActivity.this, user_guanli.class);
//                startActivity(intent5);
//                break;
            default:
        }
    }

    private class GetUserPictureThread extends Thread{
        @Override
        public void run(){
            // 使用user_id获取用户信息
            Map<String, Object> params = new HashMap<>();

            params.put("userId", LoginActivity.sp.getInt("SNO", 0));
//            params.put("userId", 2);
            try {
                String res = RequestUtils.post("/users/lugetUserByUserId", params);
                Log.d("response", res);

                // dosomething with res
                try {
                    JSONObject jsonObject = new JSONObject(res);
                    if (jsonObject.getInt("code") == 200) {
                        // 后台返回成功结果
                        //Toast里面启动了一个handler，所以要加Looper
                        Looper.prepare();
                        JSONObject data = jsonObject.getJSONObject("data");
                        JSONObject user = data.getJSONObject("user");

                        // 将user信息存到缓存中
                        ACache aCache = ACache.get(MainPersonalActivity.this);
                        aCache.put("user", user);

                        name = user.getInt("userId");
                        nickname = user.getString("userName");
                        zhuangtai = user.getString("status");
                        imageStr = user.getString("userphoto");

                        if(F_GetBitmap.isEmpty(imageStr))
                        {
                            JSONArray photo = data.getJSONArray("photo");
                            image = new byte[photo.length()];
                            for (int i = 0; i < photo.length(); i++) {
                                image[i] = (byte)photo.getInt(i);
                            }
                            F_GetBitmap.setInSDBitmap(image, imageStr);
                            InputStream input = null;
                            BitmapFactory.Options options = new BitmapFactory.Options();
                            options.inSampleSize = 2;
                            input = new ByteArrayInputStream(image);
                            @SuppressWarnings({ "rawtypes", "unchecked" })
                            SoftReference softRef = new SoftReference(BitmapFactory.decodeStream(
                                    input, null, options));
                            imageData = (Bitmap) softRef.get();
                        }
                        else{
                            imageData=F_GetBitmap.getSDBitmap(imageStr);//拿到的是BitMap类型的图片数据
                            if(F_GetBitmap.bitmap!=null && !F_GetBitmap.bitmap.isRecycled())
                            {
                                F_GetBitmap.bitmap = null;
                            }
                        }
                        aCache.put("userphoto", imageData);
                    } else {
                        // 后台返回失败结果
                        Looper.prepare();
                        Toast.makeText(MainPersonalActivity.this,"获取用户信息失败", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
