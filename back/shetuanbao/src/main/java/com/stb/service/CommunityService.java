package com.stb.service;
import com.stb.model.Community;

import java.util.List;

import com.stb.core.Service;


/**
 * Created by CodeGenerator on 2019/04/28.
 */
public interface CommunityService extends Service<Community> {
	
	//pan通过社团名查找社团
	public Community panfindByCommunityName(String communityName);
	
	//pan通过社团id查找社团成员
	public List<Integer> panfindByCommunityUser(int communityId);
	
	public List<String> lugetCommunityNamesByUserId(int userId);
}
