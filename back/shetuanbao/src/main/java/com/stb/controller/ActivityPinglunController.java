package com.stb.controller;

import com.alibaba.fastjson.JSONObject;
import com.stb.core.Result;
import com.stb.core.ResultGenerator;
import com.stb.model.activityPinlun;
import com.stb.service.ActivityPinglunService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/pinglun")
public class ActivityPinglunController {
    @Resource
    private ActivityPinglunService pinglunService;

    @PostMapping("/getPinglun")
    public Result getActivityPinglun(@RequestBody String body) {
        JSONObject jsonObject = JSONObject.parseObject(body);
        String activityId = jsonObject.getString("activityId");
        List<activityPinlun> list = pinglunService.getPinglun(activityId);
        return ResultGenerator.genSuccessResult(list);
    }
    @PostMapping("/deletePinglun")
    public Result deletePinglun(@RequestBody String body){
        JSONObject jsonObject=JSONObject.parseObject(body);
        String activityId=jsonObject.getString("activityId");
        String userId=jsonObject.getString("userId");
        pinglunService.deletePinglun(activityId,userId);
        return ResultGenerator.genSuccessResult();
    }
    
    //mo
    @PostMapping("/insertPinglun")
    public Result insertPinglun(@RequestBody String body){
        JSONObject jsonObject=JSONObject.parseObject(body);
        String activityId=jsonObject.getString("activityId");
        String userId=jsonObject.getString("userId");
        String sdetail=jsonObject.getString("sdetail");
        String stime=jsonObject.getString("stime");
        String sname=jsonObject.getString("sname");
        String spicture=jsonObject.getString("spicture");
        
        pinglunService.insertPinglun(activityId,userId,sdetail,stime,sname,spicture);
        return ResultGenerator.genSuccessResult();
    }
    
    //mo
    @PostMapping("/moDeletePinglun")
    public Result moDeletePinglun(@RequestBody String body){
        JSONObject jsonObject=JSONObject.parseObject(body);
        String activityId=jsonObject.getString("activityId");
        pinglunService.moDeletePinglun(activityId);
        return ResultGenerator.genSuccessResult();
    }
    
    
}
