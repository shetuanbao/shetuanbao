package com.community.shetuanbao.Personal;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.community.shetuanbao.R;
import com.community.shetuanbao.utils.FontManager;

public class PersonalHelpActivity extends Activity {

    TextView fanhui=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_personal_help);

        FontManager.changeFonts(FontManager.getContentView(this), this);
        fanhui=(TextView)findViewById(R.id.yonghubangxhu_fanhui);
        fanhui.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                finish();
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return false;
    }
}