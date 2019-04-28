package com.stb.model;

import javax.persistence.*;

public class Activities {
    @Id
    @Column(name = "activity_id")
    private Integer activityId;

    @Column(name = "activity_title")
    private String activityTitle;

    @Column(name = "activity_time")
    private String activityTime;

    @Column(name = "activity_place")
    private String activityPlace;

    @Column(name = "activity_introduce")
    private String activityIntroduce;

    @Column(name = "activity_picture")
    private String activityPicture;

    @Column(name = "community_id")
    private Integer communityId;

    private Integer leixing;

    /**
     * @return activity_id
     */
    public Integer getActivityId() {
        return activityId;
    }

    /**
     * @param activityId
     */
    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    /**
     * @return activity_title
     */
    public String getActivityTitle() {
        return activityTitle;
    }

    /**
     * @param activityTitle
     */
    public void setActivityTitle(String activityTitle) {
        this.activityTitle = activityTitle;
    }

    /**
     * @return activity_time
     */
    public String getActivityTime() {
        return activityTime;
    }

    /**
     * @param activityTime
     */
    public void setActivityTime(String activityTime) {
        this.activityTime = activityTime;
    }

    /**
     * @return activity_place
     */
    public String getActivityPlace() {
        return activityPlace;
    }

    /**
     * @param activityPlace
     */
    public void setActivityPlace(String activityPlace) {
        this.activityPlace = activityPlace;
    }

    /**
     * @return activity_introduce
     */
    public String getActivityIntroduce() {
        return activityIntroduce;
    }

    /**
     * @param activityIntroduce
     */
    public void setActivityIntroduce(String activityIntroduce) {
        this.activityIntroduce = activityIntroduce;
    }

    /**
     * @return activity_picture
     */
    public String getActivityPicture() {
        return activityPicture;
    }

    /**
     * @param activityPicture
     */
    public void setActivityPicture(String activityPicture) {
        this.activityPicture = activityPicture;
    }

    /**
     * @return community_id
     */
    public Integer getCommunityId() {
        return communityId;
    }

    /**
     * @param communityId
     */
    public void setCommunityId(Integer communityId) {
        this.communityId = communityId;
    }

    /**
     * @return leixing
     */
    public Integer getLeixing() {
        return leixing;
    }

    /**
     * @param leixing
     */
    public void setLeixing(Integer leixing) {
        this.leixing = leixing;
    }
}