package com.stb.service.implement;

import com.stb.dao.CommunityMapper;
import com.stb.model.Community;
import com.stb.service.CommunityService;
import com.stb.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2019/04/28.
 */
@Service
@Transactional
public class CommunityServiceImpl extends AbstractService<Community> implements CommunityService {
    @Resource
    private CommunityMapper communityMapper;

}
