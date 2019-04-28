package com.stb.service.implement;

import com.stb.dao.CommunityCishanMapper;
import com.stb.model.CommunityCishan;
import com.stb.service.CommunityCishanService;
import com.stb.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2019/04/28.
 */
@Service
@Transactional
public class CommunityCishanServiceImpl extends AbstractService<CommunityCishan> implements CommunityCishanService {
    @Resource
    private CommunityCishanMapper communityCishanMapper;

}
