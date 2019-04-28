package com.stb.service.implement;

import com.stb.dao.CommunityXueshuMapper;
import com.stb.model.CommunityXueshu;
import com.stb.service.CommunityXueshuService;
import com.stb.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2019/04/28.
 */
@Service
@Transactional
public class CommunityXueshuServiceImpl extends AbstractService<CommunityXueshu> implements CommunityXueshuService {
    @Resource
    private CommunityXueshuMapper communityXueshuMapper;

}
