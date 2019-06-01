package com.stb.dao;

import com.stb.core.Mapper;
import com.stb.model.Users;

import java.util.List;

public interface UsersMapper extends Mapper<Users> {
    public Users findByUserName(int userId);
    
    //pan通过用户id获取状态
    public String pangetstatusByuserId(int userId);
    
    //pan通过用户id获取用户
    public Users pangetuserByuserId(int userId);
    
  //pan通过用户id检查是否存在朋友
    public int pancheckfriendByuserId(int userId,int friendId);
    
  //pan通过用户id添加朋友
    public void panaddfriendByuserId(int userId,int friendId);

    //通过账号查看账号是否重复
    public int yangGetIdCount(Integer userId);

    public void register(Users user);

    //yang根据userId得到user信息
    public Users yangGetUserById(Integer userId);

    //yang通过userId删除好友
    public void yangDeleteUserById(Integer userId,Integer friendId);
}