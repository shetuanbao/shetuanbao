package com.stb.service.implement;

import com.stb.dao.UsersMapper;
import com.stb.model.Users;
import com.stb.service.UsersService;
import com.stb.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2019/04/27.
 */
@Service
@Transactional
public class UsersServiceImpl extends AbstractService<Users> implements UsersService {
    @Resource
    private UsersMapper usersMapper;

    @Override
    public Users findByUserName(int userId) {
        return usersMapper.findByUserName(userId);
    }
    
    //pan通过用户id获取状态
    @Override
    public String pangetstatusByuserId(int userId) {
    	return usersMapper.pangetstatusByuserId(userId);
    }

  //pan通过用户id获取用户
    @Override
    public Users pangetuserByuserId(int userId) {
    	return usersMapper.pangetuserByuserId(userId);
    }
    
  //pan通过用户id检查是否存在朋友
    @Override
    public int pancheckfriendByuserId(int userId,int friendId) {
    	return usersMapper.pancheckfriendByuserId(userId,friendId);
    }
    
  //pan通过用户id添加朋友
    @Override
    public void panaddfriendByuserId(int userId,int friendId) {
    	usersMapper.panaddfriendByuserId(userId,friendId);
    }
}
