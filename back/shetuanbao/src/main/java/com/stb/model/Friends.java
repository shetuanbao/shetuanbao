package com.stb.model;

import javax.persistence.*;


public class Friends {
    @Id
    @Column(name="user_id")
    private Integer userId;

    @Column(name="friend_id")
    private Integer friendId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }


    public Integer getFriendId() {
        return friendId;
    }

    public void setFriendId(Integer friendId) {
        this.friendId = friendId;
    }
}
