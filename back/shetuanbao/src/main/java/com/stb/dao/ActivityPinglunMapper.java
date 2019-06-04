package com.stb.dao;

import com.stb.core.Mapper;
import com.stb.model.activityPinlun;

import java.util.List;

public interface ActivityPinglunMapper extends Mapper<activityPinlun> {
    public List<activityPinlun> getPinglun(String activityId);
    public void deletePinglun(String activityId,String userId);
}
