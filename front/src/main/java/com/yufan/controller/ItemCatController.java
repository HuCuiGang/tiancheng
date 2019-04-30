package com.yufan.controller;

import com.yufan.bean.ItemCat;

import com.yufan.service.ItemCatRpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@ResponseBody
@RequestMapping("/cat")
public class ItemCatController {

    private Logger LOGGER=LoggerFactory.getLogger(ItemCatController.class);

    @Autowired
    private ItemCatRpcService itemCatRpcService;

    @RequestMapping(value = "/list",method = RequestMethod.POST)
    public List<ItemCat> queryItemCat(@RequestParam(value = "id" ,defaultValue = "0") Long parentId){
        return itemCatRpcService.queryItemCat(parentId);
    }
}
