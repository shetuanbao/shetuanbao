package com.stb.service;
import com.stb.model.Activities;

import java.util.List;

import com.stb.core.Service;


/**
 * Created by CodeGenerator on 2019/04/27.
 */
public interface ActivitiesService extends Service<Activities> {

    
	
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
