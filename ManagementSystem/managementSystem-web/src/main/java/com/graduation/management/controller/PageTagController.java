package com.graduation.management.controller;

import com.graduation.management.enumeration.AccessAuthorityEnum;
import com.graduation.management.result.BaseResult;
import com.graduation.management.service.PageTagService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Controller
@CrossOrigin
@RequestMapping(value = "/pageTag")
public class PageTagController {
    @Resource
    PageTagService pageTagService;

    /**
     * 查询标签页面
     * @return
     */
    @RequestMapping(value = "/getAllPageTag",method = RequestMethod.GET)
    @ResponseBody
    public BaseResult getAllPageTag(){
        return pageTagService.selectAllPageTagInfo();
    }
}
