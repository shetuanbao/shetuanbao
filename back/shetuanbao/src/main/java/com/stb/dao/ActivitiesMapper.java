package com.stb.dao;

import java.util.List;

import com.stb.core.Mapper;
import com.stb.model.Activities;

public interface ActivitiesMapper extends Mapper<Activities> {

    //pan通过社团id获取活动
    public List<Activities> pangetactivityByCommunityId(int communityId);
    //mo
    public List<String> getAlbum(int activityId);
    public Activities getDetail(int activityId);
    public void insertUser(int activityId,int userId);
    public int check(int activityId,int userId);
    
  //mo通过社团name获取活动
    public Activities moGetActivityByName(String activityName);
    
 
}