package com.stb.service.implement;

import com.stb.dao.PresidentMapper;
import com.stb.model.President;
import com.stb.service.PresidentService;
import com.stb.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2019/04/28.
 */
@Service
@Transactional
public class PresidentServiceImpl extends AbstractService<President> implements PresidentService {
    @Resource
    private PresidentMapper presidentMapper;

}
