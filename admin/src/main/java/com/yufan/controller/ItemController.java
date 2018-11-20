package com.yufan.controller;

import com.github.pagehelper.PageInfo;
import com.yufan.bean.Item;

import com.yufan.bean.ItemDesc;
import com.yufan.common.EasyUIResult;
import com.yufan.result.Result;
import com.yufan.result.ResultUtils;
import com.yufan.service.ItemDescService;
import com.yufan.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;


@Controller
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private ItemService itemService;
    @Autowired
    private ItemDescService itemDescService;

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseBody
    public Result itemAdd(Item item, String desc){
        itemService.itemAdd(item,desc);
        return ResultUtils.buildSuccess();
    }

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    @ResponseBody
    public EasyUIResult list(@RequestParam(value = "page",defaultValue = "1") Integer page,
                             @RequestParam(value = "rows",defaultValue = "10") Integer rows){

        PageInfo<Item> pageInfo = itemService.queryListByWherePage(null,page,rows);
        return new EasyUIResult(pageInfo.getTotal(),pageInfo.getList());
    }

    @RequestMapping(value = "/desc/{id}",method = RequestMethod.GET)
    @ResponseBody
    public Result itemAdd(@PathVariable("id")Long id){
        ItemDesc itemDesc = itemDescService.queryById(id);
        return ResultUtils.buildSuccess(itemDesc);
    }

    @ResponseBody
    @RequestMapping(value ="/update",method = RequestMethod.POST)
    public Result updateItem(Item item,String desc){

        if(item==null){
            return ResultUtils.buildFail(Result.NO_ITEM,"商品信息没有传递!");
        }
        if(StringUtils.isEmpty(desc)){
            return ResultUtils.buildFail(Result.NO_DESC,"商品详情没有传递!");
        }
        itemService.updateItem(item,desc);
        return ResultUtils.buildSuccess();
    }

    @ResponseBody
    @RequestMapping(value ="/reshelf",method = RequestMethod.POST)
    public Result reshelfItem(@RequestParam(value = "ids") Long[] id ){
        for (Long i:id) {
            Item item = itemService.queryById(i);
            item.setStatus(Item.STATUS_ON);
            item.setUpdated(new Date());
            itemService.update(item);
        }
        return ResultUtils.buildSuccess();
    }

    @ResponseBody
    @RequestMapping(value ="/instock",method = RequestMethod.POST)
    public Result instockItem(@RequestParam(value = "ids") Long[] id ){
        for (Long i:id) {
            Item item = itemService.queryById(i);
            item.setStatus(Item.STATUS_DOWN);
            item.setUpdated(new Date());
            itemService.update(item);
        }
        return ResultUtils.buildSuccess();
    }

    @ResponseBody
    @RequestMapping(value ="/delete",method = RequestMethod.POST)
    public Result deleteItem(@RequestParam(value = "ids") Long[] id ){
        for (Long i:id) {
            Item item = itemService.queryById(i);
            item.setStatus(Item.STATUS_DELETE);
            item.setUpdated(new Date());
            itemService.update(item);
        }
        return ResultUtils.buildSuccess();
    }

}
