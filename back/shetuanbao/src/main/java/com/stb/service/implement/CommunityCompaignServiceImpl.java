package com.stb.service.implement;

import com.stb.dao.CommunityCompaignMapper;
import com.stb.model.CommunityCompaign;
import com.stb.service.CommunityCompaignService;
import com.stb.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2019/04/28.
 */
@Service
@Transactional
public class CommunityCompaignServiceImpl extends AbstractService<CommunityCompaign> implements CommunityCompaignService {
    @Resource
    private CommunityCompaignMapper communityCompaignMapper;

}
