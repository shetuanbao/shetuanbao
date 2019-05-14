package com.stb.service;
import com.stb.model.Users;
import com.stb.core.Service;

import java.util.List;


/**
 * Created by CodeGenerator on 2019/04/27.
 */
public interface UsersService extends Service<Users> {
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

}
