package com.community.shetuanbao.Personal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.community.shetuanbao.R;
import com.community.shetuanbao.utils.FontManager;

public class PersonalContactUsActivity extends Activity implements View.OnClickListener {

    TextView fanhui=null;
    TextView lianxiwomenqq=null;
    TextView lianxiwomenweixin=null;
    TextView lianxiwomenweibo=null;
    TextView lianxiwomendianhua=null;
    TextView lianxiwomenyouxiang=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_personal_contact_us);
        FontManager.changeFonts(FontManager.getContentView(this), this);
        fanhui=(TextView)findViewById(R.id.emailtool_text1);
        fanhui.setTypeface(FontManager.tf);
        fanhui.setOnClickListener(this);

        lianxiwomenqq=(TextView)findViewById(R.id.lianxi_qq);
        lianxiwomenqq.setOnClickListener(this);
        lianxiwomenweixin=(TextView)findViewById(R.id.lianxi_weixin);
        lianxiwomenweixin.setOnClickListener(this);
        lianxiwomenweibo=(TextView)findViewById(R.id.lianxi_guanfangweibo);
        lianxiwomenweibo.setOnClickListener(this);

        lianxiwomendianhua=(TextView)findViewById(R.id.lianxi_dianhua);
        lianxiwomendianhua.setOnClickListener(this);

        lianxiwomenyouxiang=(TextView)findViewById(R.id.lianxi_lianxiyouxiang);
        lianxiwomenyouxiang.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.emailtool_text1:
                finish();
                break;
            case R.id.lianxi_qq:
                Intent it1 = new Intent(PersonalContactUsActivity.this,PersonalContactQQActivity.class);
                startActivity(it1);
                break;
            case R.id.lianxi_weixin:
                Intent it2 = new Intent(PersonalContactUsActivity.this,PersonalContactWechatActivity.class);
                startActivity(it2);
                break;
            case R.id.lianxi_guanfangweibo:
                Intent it3 = new Intent(PersonalContactUsActivity.this,PersonalContactWeiboActivity.class);
                startActivity(it3);
                break;
            case R.id.lianxi_dianhua:
                Intent it4 = new Intent(PersonalContactUsActivity.this,PersonalContactPhoneActivity.class);
                startActivity(it4);
                break;
            case R.id.lianxi_lianxiyouxiang:
                Intent it5 = new Intent(PersonalContactUsActivity.this,PersonalContactEmailActivity.class);
                startActivity(it5);
                break;
            default:
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return false;
    }
}