package com.stb.controller;
import com.stb.core.Result;
import com.stb.core.ResultGenerator;
import com.stb.model.CommunityCishan;
import com.stb.service.CommunityCishanService;
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
@RequestMapping("/community/cishan")
public class CommunityCishanController {
    @Resource
    private CommunityCishanService communityCishanService;

    @PostMapping("/add")
    public Result add(CommunityCishan communityCishan) {
        communityCishanService.save(communityCishan);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/delete")
    public Result delete(@RequestParam Integer id) {
        communityCishanService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/update")
    public Result update(CommunityCishan communityCishan) {
        communityCishanService.update(communityCishan);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/detail")
    public Result detail(@RequestParam Integer id) {
        CommunityCishan communityCishan = communityCishanService.findById(id);
        return ResultGenerator.genSuccessResult(communityCishan);
    }

    @PostMapping("/list")
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<CommunityCishan> list = communityCishanService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
}
