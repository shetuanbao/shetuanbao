package com.stb.controller;
import com.alibaba.fastjson.JSONObject;
import com.stb.core.Result;
import com.stb.core.ResultGenerator;
import com.stb.model.Users;
import com.stb.service.UsersService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
* Created by CodeGenerator on 2019/04/27.
*/
@RestController
@RequestMapping("/users")
public class UsersController {
    @Resource
    private UsersService usersService;
    @PostMapping("/login")
    public Result login(@RequestBody String body) {
        JSONObject jsonObject = JSONObject.parseObject(body);
        String userName = jsonObject.getString("userName");
        String password = jsonObject.getString("password");
        Users user = usersService.findByUserName(userName);
        if (user == null) {
            return ResultGenerator.genFailResult("用户不存在");
        }

        if (!user.getPassword().equals(password)) {
            return ResultGenerator.genFailResult("密码错误");
        }

        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/register")
    public Result register(Users user) {
        if (!user.getUserName().equals("") && !user.getPassword().equals("")) {
            usersService.save(user);
            return ResultGenerator.genSuccessResult();
        }
        return ResultGenerator.genFailResult();
    }

}
