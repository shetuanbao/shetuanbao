package com.stb.service.implement;

import com.stb.core.AbstractService;
import com.stb.dao.ActivityPinglunMapper;
import com.stb.model.activityPinlun;
import com.stb.service.ActivityPinglunService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class ActivityPinglunServiceImpl extends AbstractService<activityPinlun> implements ActivityPinglunService {
    @Resource
    ActivityPinglunMapper activityPinglunMapper;
    @Override
    public List<activityPinlun> getPinglun(String activityId) {
        return  activityPinglunMapper.getPinglun(activityId);
    }
    @Override
    public void deletePinglun(String activityId,String userId){
        activityPinglunMapper.deletePinglun(activityId,userId);
    }
    @Override
    public void insertPinglun(String activityId,String userId,String sdetail,String stime,String sname,String spicture){
    	activityPinglunMapper.insertPinglun(activityId,userId,sdetail,stime,sname,spicture);
    }
    @Override
    public void moDeletePinglun(String activityId) {
    	activityPinglunMapper.moDeletePinglun(activityId);
    }
}
