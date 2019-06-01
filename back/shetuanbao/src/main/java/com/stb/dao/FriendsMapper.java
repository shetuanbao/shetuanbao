package com.stb.dao;

import com.stb.core.Mapper;
import com.stb.model.Friends;
import com.stb.model.Users;

import java.util.List;

public interface FriendsMapper extends Mapper<Friends> {
    public List<Users> findFriends(Integer userId) ;
}
