package com.community.shetuanbao.Personal;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.community.shetuanbao.R;
import com.community.shetuanbao.utils.Constant;
import com.community.shetuanbao.utils.FontManager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PersonalAdviceActivity extends Activity implements View.OnClickListener {

    EditText content;
    EditText contact;
    TextView hasnumTV;
    int num = 100;
    private Button submit = null;
    TextView fanhui = null;
    List<String[]> zong=new ArrayList<String[]>();
    String all[][]=null;
    String name[]=null;
    String picture[]=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_personal_advice);
        FontManager.changeFonts(FontManager.getContentView(this), this);
        contact = (EditText) findViewById(R.id.advicee_mail_edit);
        hasnumTV = (TextView) findViewById(R.id.advicecount_text);
        hasnumTV.setText("0/" + num);
        submit = (Button) findViewById(R.id.submitmain_Button);
        submit.setBackgroundResource(R.drawable.buttonloginclock);
        submit.setOnClickListener(this);
        fanhui = (TextView) findViewById(R.id.emailtool_text1);
        fanhui.setOnClickListener(this);
        content = (EditText) findViewById(R.id.advice_tianjia);
        content.addTextChangedListener(new TextWatcher() {
            private CharSequence temp;
            private int selectionStart;
            private int selectionEnd;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                temp = s;
            }

            @Override
            public void afterTextChanged(Editable s) {
                int number = s.length();
                hasnumTV.setText(number + "/" + num);
                selectionStart = content.getSelectionStart();
                selectionEnd = content.getSelectionEnd();
                if (temp.length() > num) {
                    s.delete(selectionStart - 1, selectionEnd);
                    int tempSelection = selectionEnd;
                    content.setText(s);
                    content.setSelection(tempSelection);// ���ù�������
                }
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.submitmain_Button:
                if (!content.getText().toString().trim().equals("")
                        && !contact.getText().toString().trim().equals("")) {
                    feedback thread = new feedback();
                    thread.start();
                    try {
                        thread.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    finish();
                    Toast.makeText(PersonalAdviceActivity.this, "提交成功",
                            Toast.LENGTH_LONG).show();
                } else if (content.getText().toString().trim().equals("")) {
                    Toast.makeText(PersonalAdviceActivity.this, "意见不可以为空",
                            Toast.LENGTH_LONG).show();
                } else if (contact.getText().toString().trim().equals("")) {
                    Toast.makeText(PersonalAdviceActivity.this, "联系方式不可以为空",
                            Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.emailtool_text1:
                finish();
                break;
            default:
        }
    }

    class feedback extends Thread {
        @Override
        public void run() {
            Date date=new Date();
            DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time=format.format(date);
//            zong=NetInfoUtil.getyijianren(Constant.userName);
            zong=new ArrayList<>();
            all=new String[zong.size()][zong.get(0).length];
            name=new String[all.length];
            picture=new String[all.length];
            if (zong.get(0)[0].equals("")) {

            } else {
                for (int i = 0; i < zong.size(); i++) {
                    for (int j = 0; j < zong.get(i).length; j++) {
                        all[i][j]=zong.get(i)[j];
                        name[i]=all[i][0];
                        picture[i]=all[i][1];
                    }
                }
//                NetInfoUtil.feedback(Constant.userName+"#"+contact.getText().toString().trim()+"#"+content.getText().toString().trim()+"#"+time+"#"+name[0]+"#"+picture[0]);
            }
        }
    }
}