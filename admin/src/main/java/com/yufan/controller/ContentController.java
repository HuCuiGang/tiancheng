package com.yufan.controller;

import com.github.pagehelper.PageInfo;
import com.yufan.bean.Content;
import com.yufan.common.EasyUIResult;
import com.yufan.result.Result;
import com.yufan.result.ResultUtils;
import com.yufan.service.ContentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/content")
public class ContentController {

    private Logger LOOGER = LoggerFactory.getLogger(ContentController.class);

    @Autowired
    private ContentService contentService;

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    @ResponseBody
    public EasyUIResult list(@RequestParam(value = "categoryId",required = false) Long categoryId,
                             @RequestParam(value = "page",defaultValue = "1") Integer page,
                             @RequestParam(value = "rows",defaultValue = "10") Integer rows){

        LOOGER.debug("查询的页数为：{}，分页大小为：{}",page,rows);
        Content content = new Content();
        content.setCategoryId(categoryId);

        PageInfo<Content> pageInfo = contentService.queryListByWherePage(content,page,rows);

        return new EasyUIResult(pageInfo.getTotal(),pageInfo.getList());
    }

    @ResponseBody
    @RequestMapping(value = "add",method = RequestMethod.POST)
    public Result add(Content content){
        LOOGER.debug("要增加的商品id为{}",content.getId());
        content.setId(null);
        content.setCreated(new Date());
        content.setUpdated(content.getCreated());

        contentService.save(content);
        return ResultUtils.buildSuccess();
    }


    @ResponseBody
    @RequestMapping(value = "/edit",method = RequestMethod.POST)
    public Result edit(Content content){
        LOOGER.debug("要修改的商品id为{}",content.getId());
        content.setUpdated(new Date());
        contentService.update(content);
        return ResultUtils.buildSuccess();
    }

    @ResponseBody
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public Result delete(@RequestParam(value = "ids") List<Object> ids){
        LOOGER.debug("要删除的商品id为{}",ids);
        contentService.deleteByWhere("id",ids);
        return ResultUtils.buildSuccess();
    }
}
