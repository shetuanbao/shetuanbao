package com.stb.service.implement;

import com.stb.core.AbstractService;
import com.stb.dao.FriendsMapper;
import com.stb.model.Friends;
import com.stb.model.Users;
import com.stb.service.FriendsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
@Service
@Transactional
public class FriendsServiceImpl extends AbstractService<Friends> implements FriendsService {
    @Resource
    private FriendsMapper friendsMapper;


    @Override
    public List<Users> findFriends(Integer userId) {
        return  friendsMapper.findFriends(userId);
    }
}
