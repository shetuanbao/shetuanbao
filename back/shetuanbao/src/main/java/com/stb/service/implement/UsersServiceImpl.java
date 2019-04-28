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
    public Users findByUserName(String userName) {
        return usersMapper.findByUserName(userName);
    }

}
