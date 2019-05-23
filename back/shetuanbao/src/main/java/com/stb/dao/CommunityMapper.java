package com.stb.dao;

import java.util.List;

import com.stb.core.Mapper;
import com.stb.model.Community;

public interface CommunityMapper extends Mapper<Community> {
	//pan通过社团名查找社团
	public Community panfindByCommunityName(String communityName);
	
	//pan通过社团id查找社团成员
	public List<Integer> panfindByCommunityUser(int communityId);
	
	public List<String> lugetCommunityNamesByUserId(int userId);
}