package com.yufan.controller;

import com.yufan.bean.ContentCategory;
import com.yufan.result.Result;
import com.yufan.result.ResultUtils;
import com.yufan.service.ContentCategoryService;
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
public class ContentCategoryController {

    private Logger LOGGER = LoggerFactory.getLogger(ContentCategoryController.class);

    @Autowired
    private ContentCategoryService contentCategoryService;

    @ResponseBody
    @RequestMapping(value="/category/list",method = RequestMethod.GET)
    public List<ContentCategory> List(@RequestParam(value = "id",defaultValue = "0") Long parentId){
       LOGGER.debug("查询的内容分类的父类ID为：{}",parentId);
       ContentCategory contentCategory = new ContentCategory();
       contentCategory.setParentId(parentId);
       return contentCategoryService.queryListByWhere(contentCategory);
    }

    @ResponseBody
    @RequestMapping(value = "/category/add",method = RequestMethod.POST)
    public Result add(ContentCategory contentCategory){
        LOGGER.debug("添加分类名称为：{},父目录Id为：{}",contentCategory.getName(),contentCategory.getParentId());
        contentCategory.setParentId(contentCategory.getParentId());
        contentCategory.setName(contentCategory.getName());

        return ResultUtils.buildSuccess(contentCategoryService.insertContentCategory(contentCategory));
    }

    @ResponseBody
    @RequestMapping(value = "/category/rename",method = RequestMethod.POST)
    public Result reName(ContentCategory contentCategory){
        LOGGER.debug("修改分类名称的ID为：{},性名称为：{}",contentCategory.getId(),contentCategory.getName());
        contentCategory.setUpdated(new Date());
        contentCategoryService.update(contentCategory);
        return ResultUtils.buildSuccess();
    }

    @ResponseBody
    @RequestMapping(value = "/category/delete",method = RequestMethod.GET)
    public Result delete(ContentCategory contentCategory){
        LOGGER.debug("删除id为{}",contentCategory.getId());
        contentCategoryService.deleteContentCategory(contentCategory.getId());
        return ResultUtils.buildSuccess();
    }
}
