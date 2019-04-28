package com.stb.service.implement;

import com.stb.dao.CommunityWenyiMapper;
import com.stb.model.CommunityWenyi;
import com.stb.service.CommunityWenyiService;
import com.stb.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2019/04/28.
 */
@Service
@Transactional
public class CommunityWenyiServiceImpl extends AbstractService<CommunityWenyi> implements CommunityWenyiService {
    @Resource
    private CommunityWenyiMapper communityWenyiMapper;

}
