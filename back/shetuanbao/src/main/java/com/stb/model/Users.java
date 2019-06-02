package com.stb.model;

import javax.persistence.*;

public class Users {
    @Id
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "username")
    private String userName;

    @Column(name="userpassword")
    private String password;

    private String useremail;

    private String userphone;

    private String sex;

    private String userpen;

    private String userphoto;

    @Column(name="static")
    private String status;

    private String major;

    private String xueyuan;

    /**
     * @return user_id
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * @param userId
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * @return user_name
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return useremail
     */
    public String getUseremail() {
        return useremail;
    }

    /**
     * @param useremail
     */
    public void setUseremail(String useremail) {
        this.useremail = useremail;
    }

    /**
     * @return userphone
     */
    public String getUserphone() {
        return userphone;
    }

    /**
     * @param userphone
     */
    public void setUserphone(String userphone) {
        this.userphone = userphone;
    }

    /**
     * @return sex
     */
    public String getSex() {
        return sex;
    }

    /**
     * @param sex
     */
    public void setSex(String sex) {
        this.sex = sex;
    }

    /**
     * @return userpen
     */
    public String getUserpen() {
        return userpen;
    }

    /**
     * @param userpen
     */
    public void setUserpen(String userpen) {
        this.userpen = userpen;
    }

    /**
     * @return userphoto
     */
    public String getUserphoto() {
        return userphoto;
    }

    /**
     * @param userphoto
     */
    public void setUserphoto(String userphoto) {
        this.userphoto = userphoto;
    }

    /**
     * @return status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return major
     */
    public String getMajor() {
        return major;
    }

    /**
     * @param major
     */
    public void setMajor(String major) {
        this.major = major;
    }

    /**
     * @return xueyuan
     */
    public String getXueyuan() {
        return xueyuan;
    }

    /**
     * @param xueyuan
     */
    public void setXueyuan(String xueyuan) {
        this.xueyuan = xueyuan;
    }
}