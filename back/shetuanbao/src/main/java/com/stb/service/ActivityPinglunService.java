package com.stb.service;

import com.stb.core.Service;
import com.stb.model.activityPinlun;

import java.util.List;

public interface ActivityPinglunService extends Service<activityPinlun> {
    public List<activityPinlun> getPinglun(String activityId);
    public void deletePinglun(String activityId,String userId);
}
