package com.community.shetuanbao.utils;

import android.content.Context;

public class User implements Cloneable{
    private static com.community.shetuanbao.utils.User Userstance;
    private String username;
    private String password;
    private int userId;
    private String userphone;
    private String sex;
    private String userpen;
    private String userphoto;
    private String status;
    private String major;
    private String xueyuan;
    private Userstate state=new Logoutstate();


    private User(){ }

    public static com.community.shetuanbao.utils.User getInstance(){
        if(Userstance == null){
            Userstance=new com.community.shetuanbao.utils.User();
        }
        return Userstance;
    }

    public void init(String username,String password,int userId,String userphone,String sex,String userpen,String userphoto,String status,String major,String xueyuan){
        this.username=username;
        this.password=password;
        this.userId=userId;
        this.userphone=userphone;
        this.sex=sex;
        this.userphoto=userphoto;
        this.userpen=userpen;
        this.status=status;
        this.major=major;
        this.xueyuan=xueyuan;
        setstate(new Loginstate());
    }

    public int getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public String getUserphone() {
        return userphone;
    }

    public String getSex() {
        return sex;
    }

    public String getMajor() {
        return major;
    }

    public String getStatus() {
        return status;
    }

    public String getUserpen() {
        return userpen;
    }

    public String getXueyuan() {
        return xueyuan;
    }

    public String getUserphoto() {
        return userphoto;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public void setUserId(int userId) {
        this.userId = userId;
    }


    public void setUsername(String username) {
        this.username = username;
    }

    public void setUserphone(String userphone) {
        this.userphone = userphone;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setUserpen(String userpen) {
        this.userpen = userpen;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setUserphoto(String userphoto) {
        this.userphoto = userphoto;
    }

    public void setXueyuan(String xueyuan) {
        this.xueyuan = xueyuan;
    }

    public void setstate(Userstate state){
        this.state=state;
    }

    public Userstate getState() {
        return state;
    }

    @Override
    public com.community.shetuanbao.utils.User clone(){
        com.community.shetuanbao.utils.User user=null;
        try{
            user=(com.community.shetuanbao.utils.User)super.clone();
        }catch (CloneNotSupportedException e){
            e.printStackTrace();
        }
        return user;
    }

    //确定是否处于可以添加朋友的状态
    public void addfriend(Context context, int id){
        state.addfriend(context,id);
    }
}
