package com.stb.service;

import com.stb.core.Service;
import com.stb.model.Friends;
import com.stb.model.Users;
import java.util.List;

public interface FriendsService extends Service<Friends> {
     public List<Users> findFriends(Integer userId);
}
