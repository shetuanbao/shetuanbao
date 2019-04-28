package com.stb.controller;
import com.stb.core.Result;
import com.stb.core.ResultGenerator;
import com.stb.model.Administrator;
import com.stb.service.AdministratorService;
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
@RequestMapping("/administrator")
public class AdministratorController {
    @Resource
    private AdministratorService administratorService;

    @PostMapping("/add")
    public Result add(Administrator administrator) {
        administratorService.save(administrator);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/delete")
    public Result delete(@RequestParam Integer id) {
        administratorService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/update")
    public Result update(Administrator administrator) {
        administratorService.update(administrator);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/detail")
    public Result detail(@RequestParam Integer id) {
        Administrator administrator = administratorService.findById(id);
        return ResultGenerator.genSuccessResult(administrator);
    }

    @PostMapping("/list")
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<Administrator> list = administratorService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
}
