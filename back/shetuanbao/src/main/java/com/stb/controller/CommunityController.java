package com.stb.controller;
import com.stb.core.Result;
import com.stb.core.ResultGenerator;
import com.stb.model.Community;
import com.stb.model.Users;
import com.stb.service.CommunityService;
import com.stb.util.Imageutil;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

/**
* Created by CodeGenerator on 2019/04/28.
*/
@RestController
@RequestMapping("/community")
public class CommunityController {
    @Resource
    private CommunityService communityService;

    @PostMapping("/add")
    public Result add(@RequestBody String body) {
    	JSONObject jsonObject = JSONObject.parseObject(body);
    	Community community = new Community();
    	if (jsonObject.getInteger("communityId") != null) {
    		community.setCommunityId(jsonObject.getInteger("communityId"));
    	}
    	if (jsonObject.getString("communityName") != null) {
    		community.setCommunityName(jsonObject.getString("communityName"));
    	}
    	if (jsonObject.getString("communityKouhao") != null) {
    		community.setCommunityName(jsonObject.getString("communityKouhao"));
    	}
    	communityService.save(community);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/delete")
    public Result delete(@RequestParam Integer id) {
        communityService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/update")
    public Result update(Community community) {
        communityService.update(community);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/detail")
    public Result detail(@RequestParam Integer id) {
        Community community = communityService.findById(id);
        return ResultGenerator.genSuccessResult(community);
    }

    @PostMapping("/list")
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<Community> list = communityService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
    
  //pan通过社团名查找社团
    @PostMapping("/panfindByCommunityName")
    public Result panfindByCommunityName(@RequestBody String body) {
        JSONObject jsonObject = JSONObject.parseObject(body);
        String communityName = jsonObject.getString("communityName");
        Community community = communityService.panfindByCommunityName(communityName);
        if (community == null) {
            return ResultGenerator.genFailResult("社团不存在");
        }
        return ResultGenerator.genSuccessResult(community);
    }
    
  //pan通过社团id查找社团成员
    @PostMapping("/panfindByCommunityUser")
    public Result panfindByCommunityUser(@RequestBody String body) {
        JSONObject jsonObject = JSONObject.parseObject(body);
        int communityId = jsonObject.getInteger("communityId");
        List<Integer> result= communityService.panfindByCommunityUser(communityId);
        return ResultGenerator.genSuccessResult(result);
    }
    
    //pan通过社团图标名称获取头像
    @PostMapping("/panfindtubiao")
    public Result panfindtubiao(@RequestBody String body) {
        JSONObject jsonObject = JSONObject.parseObject(body);
        String name = jsonObject.getString("tubiao");
        byte[] result=Imageutil.getImage(name);
        List<Byte> result1=new ArrayList();
        for(int i=0;i<result.length;i++) {
        	result1.add(result[i]);
        }
        return ResultGenerator.genSuccessResult(result1);
    }

    //lu通过用户id从community_users和community表获取用户参加过的社团
    @PostMapping("/lugetCommunitiesByUserId")
    public Result lugetCommunitiesByUserId(@RequestBody String body) {
        JSONObject jsonObject = JSONObject.parseObject(body);
        int userId = jsonObject.getIntValue("userId");
        List<String> list = communityService.lugetCommunityNamesByUserId(userId);
        return ResultGenerator.genSuccessResult(list);
    }

    //检查是否为社团成员
    @PostMapping("/checkCommunity")
    public Result check(@RequestBody String body){
        JSONObject jsonObject=JSONObject.parseObject(body);
        int activityId=jsonObject.getInteger("communityId");
        int userId=jsonObject.getInteger("userId");
        int a=communityService.check(activityId,userId);
        return ResultGenerator.genSuccessResult(a);
    }

}
