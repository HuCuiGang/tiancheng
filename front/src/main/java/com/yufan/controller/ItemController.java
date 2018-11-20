package com.yufan.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.yufan.exception.CustomerException;
import com.yufan.result.Result;
import com.yufan.result.ResultUtils;
import com.yufan.service.ItemService;

@Controller
@ResponseBody
@RequestMapping("/item")
public class ItemController {

    private Logger LOGGER=LoggerFactory.getLogger(ItemController.class);

    @Autowired
    private ItemService itemService;

    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public Result queryItem(@PathVariable("id") Long id) throws CustomerException {
        return ResultUtils.buildSuccess(itemService.queryItemById(id));
    }

    @RequestMapping(value ="/cache/{id}",method = RequestMethod.POST)
    public Result deleteCache(@PathVariable("id")Long id){
        LOGGER.debug("查询的商品id为{}",id);
        itemService.deleteCache(id);
        return ResultUtils.buildSuccess();
    }
}
