package com.stb.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "activity_pinglun")
public class activityPinlun {
    @Id
    @Column(name = "sid_activity")
    private String activityId;

    @Column(name = "sid_user")
    private String userId;

    private String sdetail;

    private  String sname;

    private  String spicture;

    private String stime;

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSdetail() {
        return sdetail;
    }

    public void setSdetail(String sdetail) {
        this.sdetail = sdetail;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sanme) {
        this.sname = sanme;
    }

    public String getSpicture() {
        return spicture;
    }

    public void setSpicture(String spicture) {
        this.spicture = spicture;
    }

    public String getStime() {
        return stime;
    }

    public void setStime(String stime) {
        this.stime = stime;
    }
}
