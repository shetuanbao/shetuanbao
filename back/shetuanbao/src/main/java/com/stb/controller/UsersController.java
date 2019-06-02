package com.stb.controller;
import com.alibaba.fastjson.JSONObject;
import com.stb.core.Result;
import com.stb.core.ResultGenerator;
import com.stb.model.Activities;
import com.stb.model.Users;
import com.stb.service.UsersService;
import com.stb.util.Imageutil;

import com.stb.util.JwtUtil;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        int userId = jsonObject.getInteger("userId");
        String password = jsonObject.getString("password");
        Users user = usersService.findByUserName(userId);
        if (user == null) {
            return ResultGenerator.genFailResult("用户不存在");
        }

        if (!user.getPassword().equals(password)) {
            return ResultGenerator.genFailResult("密码错误");
        }

        return ResultGenerator.genSuccessResult(user);
    }

    @PostMapping("/register")
    public Result register(@RequestBody String body) {
        JSONObject jsonObject = JSONObject.parseObject(body);
        int userId=jsonObject.getInteger("userId");
        String username=jsonObject.getString("userName");
        String password=jsonObject.getString("password");
        String useremail=jsonObject.getString("useremail");
        String userphone=jsonObject.getString("userphone");
        String sex=jsonObject.getString("sex");
        String userpen=jsonObject.getString("userpen");
        String major=jsonObject.getString("major");
        String xueyuan=jsonObject.getString("xueyuan");
        Users u=new Users();
        u.setMajor(major);
        u.setPassword(password);
        u.setSex(sex);
        u.setUseremail(useremail);
        u.setUserId(userId);
        u.setUserName(username);
        u.setUserpen(userpen);
        u.setUserphone(userphone);
        u.setXueyuan(xueyuan);
        usersService.register(u);
        String res = "注册成功";
        return ResultGenerator.genSuccessResult(res);
    }
    
    //pan通过用户id获取状态
    @PostMapping("/pangetstatusByuserId")
    public Result pangetstatusByuserId(@RequestBody String body) {
    	JSONObject jsonObject = JSONObject.parseObject(body);
    	int userId=jsonObject.getInteger("userId");
        String status=usersService.pangetstatusByuserId(userId);
        return ResultGenerator.genSuccessResult(status);
    }
    
    @GetMapping("/list")
    public Result list() {
        List<Users> list = usersService.findAll();
        return ResultGenerator.genSuccessResult(list);
    }
    
  //pan通过用户id获取用户
    @PostMapping("/pangetuserByuserId")
    public Result pangetuserByuserId(@RequestBody String body) {
    	JSONObject jsonObject = JSONObject.parseObject(body);
    	int userId=jsonObject.getInteger("userId");
        Users user=usersService.pangetuserByuserId(userId);
        return ResultGenerator.genSuccessResult(user);
    }

  //pan通过用户id检查是否存在朋友
    @PostMapping("/pancheckfriendByuserId")
    public Result pancheckfriendByuserId(@RequestBody String body) {
    	JSONObject jsonObject = JSONObject.parseObject(body);
    	int userId=jsonObject.getInteger("userId");
    	int friendId=jsonObject.getInteger("friendId");
        int resultuser=usersService.pancheckfriendByuserId(userId,friendId);
        return ResultGenerator.genSuccessResult(resultuser);
    }
    
    //pan通过用户id添加朋友
    @PostMapping("/panaddfriendByuserId")
    public Result panaddfriendByuserId(@RequestBody String body){
    	JSONObject jsonObject = JSONObject.parseObject(body);
    	int userId=jsonObject.getInteger("userId");
    	int friendId=jsonObject.getInteger("friendId");
        usersService.panaddfriendByuserId(userId,friendId);
        return ResultGenerator.genSuccessResult();
    }
    
  //pan通过用户图片名称获取头像
    @PostMapping("/panfindphoto")
    public Result panfindphoto(@RequestBody String body) {
        JSONObject jsonObject = JSONObject.parseObject(body);
        String name = jsonObject.getString("photo");
        byte[] result=Imageutil.getImage(name);
        List<Byte> result1=new ArrayList();
        for(int i=0;i<result.length;i++) {
        	result1.add(result[i]);
        }
        return ResultGenerator.genSuccessResult(result1);
    }
   @PostMapping("/yangGetIdCount")
    public Result yangGetIdCount(@RequestBody String body){
       JSONObject jsonObject = JSONObject.parseObject(body);
       Integer name = jsonObject.getInteger("userId");
       int a=usersService.yangGetIdCount(name);
       return ResultGenerator.genSuccessResult(a);
   }

   //yang利用userId查找用户
    @PostMapping("/yangGetUserById")
    public Result yangGetUserById(@RequestBody String body){
        JSONObject jsonObject = JSONObject.parseObject(body);
        Integer name = jsonObject.getInteger("userId");
        Users user=usersService.yangGetUserById(name);
        return ResultGenerator.genSuccessResult(user);
    }
    //根据
    @PostMapping("/yangGetPhoto")
    public Result panfindpicture(@RequestBody String body) {
        JSONObject jsonObject = JSONObject.parseObject(body);
        String name = jsonObject.getString("userphoto");
        byte[] result=Imageutil.getImage(name);
        List<Byte> result1=new ArrayList();
        for(int i=0;i<result.length;i++) {
             result1.add(result[i]);
        }
        return ResultGenerator.genSuccessResult(result1);
    }
     //得到token的代码
    @PostMapping("/getToken")
    public Result getToken(@RequestBody String body){
        JSONObject jsonObject = JSONObject.parseObject(body);
        int name = jsonObject.getInteger("userId");
        String a=JwtUtil.sign(name);
        return ResultGenerator.genSuccessResult(a);
    }

    //杨通过使用userId删除用户
    @PostMapping("/yangDeleteUserById")
    public Result yangDeleteUserById(@RequestBody String body){
        JSONObject jsonObject=JSONObject.parseObject(body);
        Integer user=jsonObject.getInteger("userId");
        Integer friend=jsonObject.getInteger("friendId");
        usersService.yangDeleteUserById(user,friend);
        return  ResultGenerator.genSuccessResult();
    }
    //lu通过用户id获取用户图片等信息
    @PostMapping("/lugetUserByUserId")
    public Result lugetUserByUserId(@RequestBody String body) {
        JSONObject jsonObject = JSONObject.parseObject(body);
        Integer userId = jsonObject.getInteger("userId");
        Users user = usersService.findById(userId);
        byte[] userphoto = Imageutil.getImage(user.getUserphoto());
        List<Byte> photo=new ArrayList<Byte>();
        for(int i=0;i<userphoto.length;i++) {
            photo.add(userphoto[i]);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("user", user);
        map.put("photo", photo);
        return ResultGenerator.genSuccessResult(map);
    }

    //lu更新用户个人信息
    @PostMapping("/luupdateUser")
    public Result luupdateUser(@RequestBody String body) {
        JSONObject jsonObject = JSONObject.parseObject(body);
        Users users = new Users();
        Integer userId = new Integer(jsonObject.getString("userId"));
        if (userId == null || usersService.findById(userId) == null) {
            return ResultGenerator.genFailResult("该用户不存在");
        } else {
            users.setUserId(userId);
        }
        if (jsonObject.getString("userName") != null) {
            users.setUserName(jsonObject.getString("userName"));
        }
        if (jsonObject.getString("sex") != null) {
            users.setSex(jsonObject.getString("sex"));
        }
        if (jsonObject.getString("useremail") != null) {
            users.setUseremail(jsonObject.getString("useremail"));
        }
        if (jsonObject.getString("userphone") != null) {
            users.setUserphone(jsonObject.getString("userphone"));
        }
        if (jsonObject.getString("userpen") != null) {
            users.setUserpen(jsonObject.getString("userpen"));
        }
        usersService.update(users);
        return ResultGenerator.genSuccessResult();
    }

}
