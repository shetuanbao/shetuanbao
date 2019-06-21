package com.community.shetuanbao.Personal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.community.shetuanbao.Login.LoginActivity;
import com.community.shetuanbao.R;
import com.community.shetuanbao.utils.FontManager;

public class PersonalSettingsActivity extends Activity implements View.OnClickListener {
    TextView fanhui=null;
    TextView gerenziliao=null;
    Button zhuxiao=null;
    private TextView my_Qr=null;
    String name=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_personal_settings);

        Intent intent =new Intent();
        name=intent.getStringExtra("nickname");

        FontManager.changeFonts(FontManager.getContentView(this), this);
        fanhui=(TextView)findViewById(R.id.emailtoolshezhi_text1);
        fanhui.setTypeface(FontManager.tf);
        fanhui.setOnClickListener(this);
        gerenziliao=(TextView)findViewById(R.id.set_message);
        gerenziliao.setOnClickListener(this);
        zhuxiao=(Button)findViewById(R.id.zhuxiao);
        zhuxiao.setOnClickListener(this);
        my_Qr=(TextView)findViewById(R.id.my_erweima);
        my_Qr.setOnClickListener(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return false;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.emailtoolshezhi_text1:
                finish();
                break;
            case R.id.set_message:
                Intent it=new Intent(PersonalSettingsActivity.this,PersonalInformationActivity.class);
                startActivity(it);
                break;
            case R.id.zhuxiao:
                Intent logoutIntent = new Intent(PersonalSettingsActivity.this, LoginActivity.class);

                logoutIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

                startActivity(logoutIntent);
                LoginActivity.sp.edit().clear().commit();
                break;
            case R.id.my_erweima:
                Intent intent=new Intent(PersonalSettingsActivity.this,PersonalQrActivity.class);
                startActivity(intent);
                break;
            default:
        }
    }
}