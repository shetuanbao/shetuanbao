package com.stb.model;

import javax.persistence.*;

public class Administrator {
    @Id
    @Column(name = "community_id")
    private Integer communityId;

    @Column(name = "user_id")
    private Integer userId;

    private String password;

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
}