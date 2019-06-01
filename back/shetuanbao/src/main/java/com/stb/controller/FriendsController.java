package com.stb.controller;


import com.alibaba.fastjson.JSONObject;
import com.stb.core.Result;
import com.stb.core.ResultGenerator;
import com.stb.model.Friends;
import com.stb.model.Users;
import com.stb.service.FriendsService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/friends")
public class FriendsController {
    @Resource
    private FriendsService friendsService;
    @PostMapping("/findFriends")
    public Result findFriends(@RequestBody String body) {
        JSONObject jsonObject = JSONObject.parseObject(body);
        int userId=jsonObject.getInteger("userId");
        List<Users> list =  friendsService.findFriends(userId);
        return ResultGenerator.genSuccessResult(list);
    }
    @GetMapping("/list")
    public Result list() {
        List<Friends> list = friendsService.findAll();
        return ResultGenerator.genSuccessResult(list);
    }
}
