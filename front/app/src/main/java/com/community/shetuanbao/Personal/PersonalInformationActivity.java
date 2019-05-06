package com.community.shetuanbao.Personal;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.community.shetuanbao.R;
import com.community.shetuanbao.utils.Constant;
import com.community.shetuanbao.utils.F_GetBitmap;
import com.community.shetuanbao.utils.FontManager;
import com.community.shetuanbao.utils.NetInfoUtil;
import com.community.shetuanbao.utils.RoundImageView;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.util.List;

public class PersonalInformationActivity extends Activity implements View.OnClickListener {
    String id = null;
    String photopath = "999";
    String path;
    ProgressDialog pd;
    TextView fanhui = null;
    TextView baocun = null;
    TextView username = null;
    EditText name = null;
    EditText sex = null;
    EditText e_mail = null;
    EditText phone = null;
    private EditText join=null;
    byte[] temp;
    Bitmap bit;
    RoundImageView touxiang = null;
    RelativeLayout changephoto;
    private List<String[]> usermessage = null;
    String message[][] = null;
    String mes;
    String zhanghao, xingming, xingbie, youxiang, thispen, dianhua;
    static Uri uri;
    Bitmap cameraBitmap;
    Bitmap bm = null;
    byte all_image[]=null;
    private EditText pen=null;
    private EditText thisxueyuan=null;
    private EditText thismajor=null;
    private String[] jointeam=null;

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
        photopath=Constant.userName;
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
        username = (TextView) findViewById(R.id.myselfmain_username_edit);
        username.setOnClickListener(this);
        name = (EditText) findViewById(R.id.myselfmain_name_edit);
        sex = (EditText) findViewById(R.id.myselfmain_sex_edit);
        sex.setOnClickListener(this);
        e_mail = (EditText) findViewById(R.id.myselfmain_e_mail_edit);
        phone = (EditText) findViewById(R.id.myselfmain_phone_edit);
        touxiang = (RoundImageView) findViewById(R.id.set_user_image);
        changephoto = (RelativeLayout) findViewById(R.id.myselfmain_1);
        changephoto.setOnClickListener(this);

        thread th_l = new thread();
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
            case R.id.myselftool_text1:
                if (bit != null && !bit.isRecycled()) {
                    bit.recycle();
                    bit = null;
                }
                if (cameraBitmap != null && !cameraBitmap.isRecycled()) {
                    cameraBitmap.recycle();
                    cameraBitmap = null;
                }
                System.gc();
                finish();
                break;
            case R.id.myselftool_text3:
                if (username.getText().equals(null) || name.getText().equals("") || sex.getHint().equals(null) || sex.getText().equals("") || e_mail.getText().equals("") || pen.getText().equals("") || phone.getText().equals("")) {
                    Toast.makeText(PersonalInformationActivity.this, "不可有空的选项",
                            Toast.LENGTH_SHORT).show();
                } else {
                    thread_set th = new thread_set();
                    th.start();
                    try {
                        th.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    AlertDialog.Builder b = new AlertDialog.Builder(PersonalInformationActivity.this);
                    b.setTitle("信息保存");
                    b.setMessage("保存成功！");// 设置信息
                    b.setPositiveButton// 为对话框设置按钮
                            ("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String picFilePath = path;
                                    BitmapFactory.Options options = new BitmapFactory.Options();
                                    options.inDither = false; /* 不进行图片抖动处理 */
                                    options.inPreferredConfig = null; /* 设置让解码器以最佳方式解码 */
                                    options.inSampleSize = 1; /* 图片长宽方向缩小倍数 */
                                    Bitmap bm = BitmapFactory.decodeFile(picFilePath, options);
                                    if (bm == null) {
                                        MainPersonalActivity.photo.setImageBitmap(bit);
                                    } else {
                                        MainPersonalActivity.photo.setImageBitmap(bm);
                                    }
                                    finish();
                                }
                            });
                    b.create().show();
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

    class thread extends Thread {
        @Override
        public void run() {
            jointeam=NetInfoUtil.getuserjointeam(Constant.userName);
            usermessage = NetInfoUtil.getusermessagebyid(Constant.userName);
            message = new String[usermessage.size()][usermessage.get(0).length];
            for (int i = 0; i < usermessage.size(); i++) {
                for (int j = 0; j < usermessage.get(i).length; j++) {
                    message[i][j] = usermessage.get(i)[j];
                }
            }
            String data=" ";
            for(int i=0;i<jointeam.length;i++)
            {
                data+=jointeam[i]+" ";
            }
            join.setText(data);
            username.setText(message[0][1]);
            name.setHint(message[0][2]);
            sex.setHint(message[0][3]);
            e_mail.setHint(message[0][4]);
            phone.setHint(message[0][5]);
            pen.setHint(message[0][6]);
            thisxueyuan.setHint(message[0][7]);
            thismajor.setHint(message[0][8]);
            String image = message[0][0];
            if (F_GetBitmap.isEmpty(image)) {
                temp = NetInfoUtil.getscPicture(image);
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
                bit = F_GetBitmap.getSDBitmap(image);// �õ�����BitMap���͵�ͼƬ����
                if (F_GetBitmap.bitmap != null
                        && !F_GetBitmap.bitmap.isRecycled()) {
                    F_GetBitmap.bitmap = null;
                }
            }
            touxiang.setImageBitmap(bit);
        }
    }

    class thread_set extends Thread {
        @Override
        public void run() {
            zhanghao = username.getText().toString();
            mes = zhanghao;
            if (!name.getText().toString().equals("")) {
                xingming = name.getText().toString();
            } else {
                xingming = name.getHint().toString();
            }
            mes = mes + "<#>" + xingming;
            if (!sex.getText().toString().equals("")) {
                xingbie = sex.getText().toString();
            } else {
                xingbie = sex.getHint().toString();
            }
            mes = mes + "<#>" + xingbie;
            if (!e_mail.getText().toString().equals("")) {
                youxiang = e_mail.getText().toString();
            } else {
                youxiang = e_mail.getHint().toString();
            }
            mes = mes + "<#>" + youxiang;
            if (!phone.getText().toString().equals("")) {
                dianhua = phone.getText().toString();
            } else {
                dianhua = phone.getHint().toString();
            }
            mes = mes + "<#>" + dianhua;
            if (!pen.getText().toString().equals("")) {
                thispen = pen.getText().toString();
            } else {
                thispen = pen.getHint().toString();
            }
            mes=mes+"<#>"+thispen;
            mes = mes + "<#>" + photopath;
            NetInfoUtil.setusermessagebyid(mes);
        }
    }
}
