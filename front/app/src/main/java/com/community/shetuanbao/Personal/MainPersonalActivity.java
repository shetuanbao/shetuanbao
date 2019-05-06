package com.community.shetuanbao.Personal;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.community.shetuanbao.R;
import com.community.shetuanbao.utils.Constant;
import com.community.shetuanbao.utils.Exit;
import com.community.shetuanbao.utils.F_GetBitmap;
import com.community.shetuanbao.utils.FontManager;
import com.community.shetuanbao.utils.NetInfoUtil;
import com.community.shetuanbao.utils.RoundImageView;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.ref.SoftReference;

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
    private String image=null;
    private String name=null;
    private Bitmap imageData;
    private byte[] all_image;
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
        thread_getuserpicture th=new thread_getuserpicture();
        th.start();
        try{
            th.join();
        }catch(Exception e){
            e.printStackTrace();
        }
        photo.setImageBitmap(imageData);
        System.out.println("hhhhhhhhhhhhhhhhhhhhhh"+zhuangtai);
        if(zhuangtai.equals("1")){
            information.setText(name+"|ID:"+Constant.userName);
        }else if(zhuangtai.equals("0")){
            information.setText(name+"|ID:"+Constant.userName+"|账号被封禁");
        }

        shezhi=(TextView)findViewById(R.id.mine_shezhi);
        shezhi.setOnClickListener(this);
        lianxi=(TextView)findViewById(R.id.mine_lianxiwomen);
        lianxi.setOnClickListener(this);
        yonghubangzhu=(TextView)findViewById(R.id.mine_yonghubangzhu);
        yonghubangzhu.setOnClickListener(this);
        yijianfankui=(TextView)findViewById(R.id.mine_yijianfankui);
        yijianfankui.setOnClickListener(this);
        guanli=(TextView)findViewById(R.id.mine_guanli);
        guanli.setOnClickListener(this);
        FontManager.initTypeFace(this);
        FontManager.changeFonts(FontManager.getContentView(this),this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mine_shezhi:
                Intent intent1 = new Intent(MainPersonalActivity.this, PersonalSettingsActivity.class);
                intent1.putExtra("name", name);
                startActivity(intent1);
                break;
            case R.id.mine_lianxiwomen:
                Intent intent2 = new Intent(MainPersonalActivity.this, user_lianxiwomen.class);
                startActivity(intent2);
                break;
            case R.id.mine_yonghubangzhu:
                Intent intent3 = new Intent(MainPersonalActivity.this, user_yonghubangzhu.class);
                //����Activity
                startActivity(intent3);
                break;
            case R.id.mine_yijianfankui:
                Intent intent4 = new Intent(MainPersonalActivity.this, user_yijianfankui.class);
                startActivity(intent4);
                break;
            case R.id.mine_guanli:
                Intent intent5 = new Intent(MainPersonalActivity.this, user_guanli.class);
                startActivity(intent5);
                break;
            default:
        }
    }

    private class thread_getuserpicture extends Thread{
        @Override
        public void run(){
            image=NetInfoUtil.getuseronephoto(Constant.userName);
            name=NetInfoUtil.getusername(Constant.userName);
            zhuangtai=NetInfoUtil.getuserstatic(Constant.userName);
            if(F_GetBitmap.isEmpty(image))
            {
                all_image=NetInfoUtil.getPicture(image);
                F_GetBitmap.setInSDBitmap(all_image, image);
                InputStream input = null;
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 2;
                input = new ByteArrayInputStream(all_image);
                @SuppressWarnings({ "rawtypes", "unchecked" })
                SoftReference softRef = new SoftReference(BitmapFactory.decodeStream(
                        input, null, options));
                imageData = (Bitmap) softRef.get();
            }
            else{
                imageData=F_GetBitmap.getSDBitmap(image);//拿到的是BitMap类型的图片数据
                if(F_GetBitmap.bitmap!=null && !F_GetBitmap.bitmap.isRecycled())
                {
                    F_GetBitmap.bitmap = null;
                }
            }
        }
    }
}
