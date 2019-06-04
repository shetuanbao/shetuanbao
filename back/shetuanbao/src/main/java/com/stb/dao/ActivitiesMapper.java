package com.stb.dao;

import java.util.List;

import com.stb.core.Mapper;
import com.stb.model.Activities;

public interface ActivitiesMapper extends Mapper<Activities> {

    //pan通过社团id获取活动
    public List<Activities> pangetactivityByCommunityId(int communityId);
    public List<String> getAlbum(int activityId);
    public Activities getDetail(int activityId);

}