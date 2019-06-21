package com.community.shetuanbao.Personal;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.community.shetuanbao.R;
import com.community.shetuanbao.utils.ACache;
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

public class PersonalInformationActivity extends Activity implements View.OnClickListener {
    String id = null;
    String path;
    ProgressDialog pd;
    TextView fanhui = null;
    TextView baocun = null;
    TextView username = null;
    EditText nickname = null;
    EditText sex = null;
    EditText e_mail = null;
    EditText phone = null;
    private EditText join=null;
    byte[] temp;
    Bitmap bit;
    RoundImageView touxiang = null;
    RelativeLayout changephoto;
    private EditText pen=null;
    private EditText thisxueyuan=null;
    private EditText thismajor=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_personal_information);

        pd = new ProgressDialog(this);
        pd.setMax(100);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setCancelable(false);
        pd.setMessage("加载中...");
        pen=(EditText)findViewById(R.id.myselfmain_pen_edit);
        FontManager.changeFonts(FontManager.getContentView(this), this);
        thisxueyuan=(EditText)findViewById(R.id.myselfmain_xueyuan_edit);
        thisxueyuan.setOnClickListener(this);
        join=(EditText)findViewById(R.id.myselfmain_join_edit);
        thismajor=(EditText)findViewById(R.id.myselfmain_major_edit);
        thismajor.setOnClickListener(this);
        fanhui = (TextView) findViewById(R.id.myselftool_text1);
        fanhui.setOnClickListener(this);
        baocun = (TextView) findViewById(R.id.myselftool_text3);
        baocun.setOnClickListener(this);
        username = (TextView) findViewById(R.id.myselfmain_username_edit); //账号
        username.setOnClickListener(this);
        nickname = (EditText) findViewById(R.id.myselfmain_name_edit); //昵称
        sex = (EditText) findViewById(R.id.myselfmain_sex_edit);
        sex.setOnClickListener(this);
        e_mail = (EditText) findViewById(R.id.myselfmain_e_mail_edit);
        phone = (EditText) findViewById(R.id.myselfmain_phone_edit);
        touxiang = (RoundImageView) findViewById(R.id.set_user_image);
        changephoto = (RelativeLayout) findViewById(R.id.myselfmain_1);
        changephoto.setOnClickListener(this);

        PIThread th_l = new PIThread();
        th_l.start();
        try {
            th_l.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        FontManager.initTypeFace(this);
        FontManager.changeFonts(FontManager.getContentView(this), this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.myselftool_text1: // 返回
                if (bit != null && !bit.isRecycled()) {
                    bit.recycle();
                    bit = null;
                }
                System.gc();
                finish();
                break;
            case R.id.myselftool_text3: // 保存
                if (username.getText().equals(null)) {
                    Toast.makeText(PersonalInformationActivity.this, "账号不能为空",
                            Toast.LENGTH_SHORT).show();
                } else {
                    PISetThread th = new PISetThread();
                    th.start();
                    try {
                        th.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case R.id.myselfmain_username_edit:
                Toast.makeText(PersonalInformationActivity.this, "账号不可修改", Toast.LENGTH_LONG).show();
                break;
            case R.id.myselfmain_xueyuan_edit:
                Toast.makeText(PersonalInformationActivity.this, "学院不可修改", Toast.LENGTH_LONG).show();
                break;
            case R.id.myselfmain_major_edit:
                Toast.makeText(PersonalInformationActivity.this, "专业不可修改", Toast.LENGTH_LONG).show();
                break;
            case R.id.myselfmain_sex_edit:
                Toast.makeText(PersonalInformationActivity.this, "性别不可修改", Toast.LENGTH_LONG).show();
                break;
            case R.id.myselfmain_1:
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(intent, 0);
                break;
            default:
        }
    }

    class PIThread extends Thread {
        @Override
        public void run() {
            Map<String, Object> params = new HashMap<>();
            params.put("userId", 2013141006);
            try {
                String res = RequestUtils.post("/community/lugetCommunitiesByUserId", params);
                Log.d("response", res);

                // dosomething with res
                try {
                    JSONObject jsonObject = new JSONObject(res);
                    if (jsonObject.getInt("code") == 200) {
                        Looper.prepare();
                        JSONArray communities = jsonObject.getJSONArray("data");
                        String data=" ";
                        for(int i=0;i<communities.length();i++)
                        {
                            data+=communities.getString(i)+" ";
                        }
                        join.setText(data);

                    } else {
                        // 后台返回失败结果
                        Looper.prepare();
                        Toast.makeText(PersonalInformationActivity.this,"获取用户信息失败", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            ACache aCache = ACache.get(PersonalInformationActivity.this);
            JSONObject user = aCache.getAsJSONObject("user");
            try {
                Integer userId = user.getInt("userId");
                username.setText(userId.toString());
                nickname.setText(user.getString("userName"));
                if (user.getString("sex").equals("0")) {
                    sex.setHint("男");
                } else {
                    sex.setHint("女");
                }
                e_mail.setText(user.getString("useremail"));
                phone.setText(user.getString("userphone"));
                pen.setText(user.getString("userpen"));
                thisxueyuan.setHint(user.getString("xueyuan"));
                thismajor.setHint(user.getString("major"));

                String image = user.getString("userphoto");
                path = image;
                if (F_GetBitmap.isEmpty(image)) {
                    temp = aCache.getAsBinary("userphoto");
                    F_GetBitmap.setInSDBitmap(temp, image);
                    InputStream input = null;
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 2;
                    input = new ByteArrayInputStream(temp);
                    @SuppressWarnings({ "unchecked", "rawtypes" })
                    SoftReference softRef = new SoftReference(
                            BitmapFactory.decodeStream(input, null, options));
                    bit = (Bitmap) softRef.get();
                } else {
                    bit = F_GetBitmap.getSDBitmap(image);
                    if (F_GetBitmap.bitmap != null
                            && !F_GetBitmap.bitmap.isRecycled()) {
                        F_GetBitmap.bitmap = null;
                    }
                }
                touxiang.setImageBitmap(bit);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    class PISetThread extends Thread {
        @Override
        public void run() {
            Map<String, Object> params = new HashMap<>();
            params.put("userId", username.getText().toString());
            if (!nickname.getText().toString().equals("")) {
                params.put("userName", nickname.getText().toString());
            }
            if (!sex.getText().toString().equals("")) {
                params.put("sex", sex.getText().toString());
            }
            if (!e_mail.getText().toString().equals("")) {
                params.put("useremail", e_mail.getText().toString());
            }
            if (!phone.getText().toString().equals("")) {
                params.put("userphone", phone.getText().toString());
            }
            if (!pen.getText().toString().equals("")) {
                params.put("userpen", pen.getText().toString());
            }

            try {
                String res = RequestUtils.post("/users/luupdateUser", params);
                Log.d("response", res);

                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(res);
                    if (jsonObject.getInt("code") == 200) {
                        Looper.prepare();
                        Toast.makeText(PersonalInformationActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inDither = false; /* 不进行图片抖动处理 */
                        options.inPreferredConfig = null; /* 设置让解码器以最佳方式解码 */
                        options.inSampleSize = 1; /* 图片长宽方向缩小倍数 */
                        Bitmap bm = BitmapFactory.decodeFile(path, options);
                        if (bm == null) {
                            MainPersonalActivity.photo.setImageBitmap(bit);
                        } else {
                            MainPersonalActivity.photo.setImageBitmap(bm);
                        }
                        finish();
                    } else {
                        Looper.prepare();
                        Toast.makeText(PersonalInformationActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
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
