package com.community.shetuanbao.utils;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.community.shetuanbao.Login.LoginActivity;

public class Logoutstate implements Userstate {
    @Override
    public void addfriend(Context context, int id){
        Toast.makeText(context,"用户未登录",Toast.LENGTH_LONG).show();
        Intent intent=new Intent(context, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }
}