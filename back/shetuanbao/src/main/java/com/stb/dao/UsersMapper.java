package com.stb.dao;

import com.stb.core.Mapper;
import com.stb.model.Users;

public interface UsersMapper extends Mapper<Users> {
    public Users findByUserName(String userName);
}