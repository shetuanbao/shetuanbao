package com.stb.model;

import javax.persistence.*;

@Table(name = "community_xueshu")
public class CommunityXueshu {
    @Id
    @Column(name = "community_id")
    private String communityId;

    @Column(name = "community_name")
    private String communityName;

    /**
     * @return community_id
     */
    public String getCommunityId() {
        return communityId;
    }

    /**
     * @param communityId
     */
    public void setCommunityId(String communityId) {
        this.communityId = communityId;
    }

    /**
     * @return community_name
     */
    public String getCommunityName() {
        return communityName;
    }

    /**
     * @param communityName
     */
    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }
}