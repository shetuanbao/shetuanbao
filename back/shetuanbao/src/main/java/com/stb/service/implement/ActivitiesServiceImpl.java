package com.stb.service.implement;

import com.stb.dao.ActivitiesMapper;
import com.stb.model.Activities;
import com.stb.service.ActivitiesService;
import com.stb.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2019/04/27.
 */
@Service
@Transactional
public class ActivitiesServiceImpl extends AbstractService<Activities> implements ActivitiesService {
    @Resource
    private ActivitiesMapper activitiesMapper;
    
  //pan通过社团id获取活动
    public List<Activities> pangetactivityByCommunityId(int communityId){
    	return activitiesMapper.pangetactivityByCommunityId(communityId);
    }
    
}
