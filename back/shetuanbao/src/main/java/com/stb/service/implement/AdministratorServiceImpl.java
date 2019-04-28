package com.stb.service.implement;

import com.stb.dao.AdministratorMapper;
import com.stb.model.Administrator;
import com.stb.service.AdministratorService;
import com.stb.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2019/04/28.
 */
@Service
@Transactional
public class AdministratorServiceImpl extends AbstractService<Administrator> implements AdministratorService {
    @Resource
    private AdministratorMapper administratorMapper;

}
