package com.community.shetuanbao.Personal;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.community.shetuanbao.R;
import com.community.shetuanbao.utils.FontManager;

public class PersonalContactPhoneActivity extends Activity {

  TextView fanhui=null;
  TextView number1,number2,number3;
  ImageView phone1,phone2,phone3;
  String phone_number=null;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.activity_personal_contact_phone);
    FontManager.changeFonts(FontManager.getContentView(this), this);
    fanhui=(TextView)findViewById(R.id.phonetool_text1);
    fanhui.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        // TODO Auto-generated method stub
        finish();
      }
    });

    number1=(TextView)findViewById(R.id.contact_main_1_right_text);
    number2=(TextView)findViewById(R.id.contact_main_2_right_text);
    number3=(TextView)findViewById(R.id.contact_main_3_right_text);

    phone1=(ImageView)findViewById(R.id.contact_main_1_image);
    phone2=(ImageView)findViewById(R.id.contact_main_2_image);
    phone3=(ImageView)findViewById(R.id.contact_main_3_image);
    phone1.setOnClickListener(new MyOnClickListener());
    phone2.setOnClickListener(new MyOnClickListener());
    phone3.setOnClickListener(new MyOnClickListener());
  }

  private class MyOnClickListener implements View.OnClickListener
  {

    @Override
    public void onClick(View v) {
      // TODO Auto-generated method stub
      int mBtnid = v.getId();
      switch(mBtnid)
      {
        case R.id.contact_main_1_image:
          phone_number = number1.getText().toString();
          phone_number = phone_number.trim();
          if(PhoneNumberUtils.isGlobalPhoneNumber(phone_number))
          {
            Intent intent=new Intent(Intent.ACTION_DIAL,Uri.parse("tel://"+phone_number));
            //Intent i=new Intent(Intent.ACTION_CALL_BUTTON);
            PersonalContactPhoneActivity.this.startActivity(intent);
          }
          else
          {
            Toast.makeText(PersonalContactPhoneActivity.this, "�绰���벻���ϸ�ʽ������", Toast.LENGTH_LONG).show();

          }
          break;

        case R.id.contact_main_2_image:
          phone_number = number2.getText().toString();
          phone_number = phone_number.trim();//ɾ���ַ����ײ���β���Ŀո�
          if(PhoneNumberUtils.isGlobalPhoneNumber(phone_number))
          {//�Ϸ��򲦺�
            Intent intent=new Intent(Intent.ACTION_DIAL,Uri.parse("tel://"+phone_number));
            //Intent i=new Intent(Intent.ACTION_CALL_BUTTON);
            PersonalContactPhoneActivity.this.startActivity(intent);
          }
          else
          {//���Ϸ�����ʾ
            Toast.makeText(
                    PersonalContactPhoneActivity.this, //������
                    "�绰���벻���ϸ�ʽ������", //��ʾ����
                    Toast.LENGTH_LONG			//��Ϣ��ʾʱ��
            ).show();

          }
          break;

        case R.id.contact_main_3_image:
          phone_number = number3.getText().toString();
          phone_number = phone_number.trim();//ɾ���ַ����ײ���β���Ŀո�
          if(PhoneNumberUtils.isGlobalPhoneNumber(phone_number))
          {//�Ϸ��򲦺�
            Intent intent=new Intent(Intent.ACTION_DIAL,Uri.parse("tel://"+phone_number));
            //Intent i=new Intent(Intent.ACTION_CALL_BUTTON);
            PersonalContactPhoneActivity.this.startActivity(intent);
          }
          else
          {//���Ϸ�����ʾ
            Toast.makeText(
                    PersonalContactPhoneActivity.this, //������
                    "�绰���벻���ϸ�ʽ������", //��ʾ����
                    Toast.LENGTH_LONG			//��Ϣ��ʾʱ��
            ).show();

          }
          break;

      }
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