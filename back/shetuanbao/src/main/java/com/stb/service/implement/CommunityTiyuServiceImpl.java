package com.stb.service.implement;

import com.stb.dao.CommunityTiyuMapper;
import com.stb.model.CommunityTiyu;
import com.stb.service.CommunityTiyuService;
import com.stb.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2019/04/28.
 */
@Service
@Transactional
public class CommunityTiyuServiceImpl extends AbstractService<CommunityTiyu> implements CommunityTiyuService {
    @Resource
    private CommunityTiyuMapper communityTiyuMapper;

}
