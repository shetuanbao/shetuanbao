package com.stb.service;
import com.stb.model.Users;
import com.stb.core.Service;


/**
 * Created by CodeGenerator on 2019/04/27.
 */
public interface UsersService extends Service<Users> {
    public Users findByUserName(String userName);
}
