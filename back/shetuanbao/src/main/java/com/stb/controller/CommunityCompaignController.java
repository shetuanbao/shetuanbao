package com.stb.controller;
import com.stb.core.Result;
import com.stb.core.ResultGenerator;
import com.stb.model.CommunityCompaign;
import com.stb.service.CommunityCompaignService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
* Created by CodeGenerator on 2019/04/28.
*/
@RestController
@RequestMapping("/community/compaign")
public class CommunityCompaignController {
    @Resource
    private CommunityCompaignService communityCompaignService;

    @PostMapping("/add")
    public Result add(CommunityCompaign communityCompaign) {
        communityCompaignService.save(communityCompaign);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/delete")
    public Result delete(@RequestParam Integer id) {
        communityCompaignService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/update")
    public Result update(CommunityCompaign communityCompaign) {
        communityCompaignService.update(communityCompaign);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/detail")
    public Result detail(@RequestParam Integer id) {
        CommunityCompaign communityCompaign = communityCompaignService.findById(id);
        return ResultGenerator.genSuccessResult(communityCompaign);
    }

    @PostMapping("/list")
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<CommunityCompaign> list = communityCompaignService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
}
