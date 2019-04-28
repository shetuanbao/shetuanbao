package com.stb.service.implement;

import com.stb.dao.ActivitiesMapper;
import com.stb.model.Activities;
import com.stb.service.ActivitiesService;
import com.stb.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2019/04/27.
 */
@Service
@Transactional
public class ActivitiesServiceImpl extends AbstractService<Activities> implements ActivitiesService {
    @Resource
    private ActivitiesMapper activitiesMapper;

}
