package com.stb.controller;
import com.stb.core.Result;
import com.stb.core.ResultGenerator;
import com.stb.model.Activities;
import com.stb.model.activityPinlun;
import com.stb.service.ActivitiesService;
import com.stb.util.Imageutil;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CodeGenerator on 2019/04/27.
 */
@RestController
@RequestMapping("/activities")
public class ActivitiesController {
    @Resource
    private ActivitiesService activitiesService;

    @PostMapping("/add")
    public Result add(Activities activities) {
        activitiesService.save(activities);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/delete")
    public Result delete(@RequestParam Integer id) {
        activitiesService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/update")
    public Result update(Activities activities) {
        activitiesService.update(activities);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/detail")
    public Result detail(@RequestParam Integer id) {
        Activities activities = activitiesService.findById(id);
        return ResultGenerator.genSuccessResult(activities);
    }

    @PostMapping("/list")
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<Activities> list = activitiesService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    //pan通过社团id获取活动
    @PostMapping("/pangetactivityByCommunityId")
    public Result pangetactivityByCommunityId(@RequestBody String body) {
        JSONObject jsonObject = JSONObject.parseObject(body);
        int communityId=jsonObject.getInteger("communityId");
        List<Activities> list = activitiesService.pangetactivityByCommunityId(communityId);
        return ResultGenerator.genSuccessResult(list);
    }

    //pan通过活动图标名称获取图标
    @PostMapping("/panfindpicture")
    public Result panfindpicture(@RequestBody String body) {
        JSONObject jsonObject = JSONObject.parseObject(body);
        String name = jsonObject.getString("picture");
        byte[] result=Imageutil.getImage(name);
        List<Byte> result1=new ArrayList();
        for(int i=0;i<result.length;i++) {
            result1.add(result[i]);
        }
        return ResultGenerator.genSuccessResult(result1);
    }
    @PostMapping("/yangGetAlbum")
    public  Result yangGetAlbum(@RequestBody String body){
        JSONObject jsonObject = JSONObject.parseObject(body);
        int activityId=jsonObject.getInteger("activityId");
        List<String> list = activitiesService.getAlbum(activityId);
        return ResultGenerator.genSuccessResult(list);
    }
    @PostMapping("/getActivityDetail")
    public  Result getActivityDetail(@RequestBody String body){
        JSONObject jsonObject = JSONObject.parseObject(body);
        int activityId=jsonObject.getInteger("activityId");
        Activities list = activitiesService.getDetail(activityId);
        return ResultGenerator.genSuccessResult(list);
    }

}
