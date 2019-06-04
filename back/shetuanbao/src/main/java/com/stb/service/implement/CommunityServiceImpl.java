package com.stb.service.implement;

import com.stb.dao.CommunityMapper;
import com.stb.model.Community;
import com.stb.service.CommunityService;
import com.stb.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2019/04/28.
 */
@Service
@Transactional
public class CommunityServiceImpl extends AbstractService<Community> implements CommunityService {
    @Resource
    private CommunityMapper communityMapper;
    
  //pan通过社团名查找社团
    public Community panfindByCommunityName(String communityName) {
    	return communityMapper.panfindByCommunityName(communityName);
    }
    
  //pan通过社团id查找社团成员
    public List<Integer> panfindByCommunityUser(int communityId){
    	return communityMapper.panfindByCommunityUser(communityId);
    }

<<<<<<< HEAD
    @Override
    public List<String> lugetCommunityNamesByUserId(int userId) {
        return communityMapper.lugetCommunityNamesByUserId(userId);
    }
=======
	@Override
	public List<String> lugetCommunityNamesByUserId(int userId) {
		return communityMapper.lugetCommunityNamesByUserId(userId);
	}
>>>>>>> remotes/origin/dev1
}
