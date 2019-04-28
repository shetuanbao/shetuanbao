package com.stb.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "community_compaign")
public class CommunityCompaign {
    @Id
    @Column(name = "compaign_id")
    private Integer compaignId;

    @Column(name = "compaign_picture_id")
    private Integer compaignPictureId;

    @Column(name = "compaign_introduce")
    private String compaignIntroduce;

    @Column(name = "compaign_time")
    private Date compaignTime;

    @Column(name = "community_id")
    private Integer communityId;

    /**
     * @return compaign_id
     */
    public Integer getCompaignId() {
        return compaignId;
    }

    /**
     * @param compaignId
     */
    public void setCompaignId(Integer compaignId) {
        this.compaignId = compaignId;
    }

    /**
     * @return compaign_picture_id
     */
    public Integer getCompaignPictureId() {
        return compaignPictureId;
    }

    /**
     * @param compaignPictureId
     */
    public void setCompaignPictureId(Integer compaignPictureId) {
        this.compaignPictureId = compaignPictureId;
    }

    /**
     * @return compaign_introduce
     */
    public String getCompaignIntroduce() {
        return compaignIntroduce;
    }

    /**
     * @param compaignIntroduce
     */
    public void setCompaignIntroduce(String compaignIntroduce) {
        this.compaignIntroduce = compaignIntroduce;
    }

    /**
     * @return compaign_time
     */
    public Date getCompaignTime() {
        return compaignTime;
    }

    /**
     * @param compaignTime
     */
    public void setCompaignTime(Date compaignTime) {
        this.compaignTime = compaignTime;
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
}