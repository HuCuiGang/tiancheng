package com.yufan.controller;

import com.yufan.bean.ItemCat;
import com.yufan.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/cat")
public class ItemCatController {

    @Autowired
    private ItemCatService itemCatService;

    @RequestMapping(value="/list",method=RequestMethod.POST)
    @ResponseBody
    public List<ItemCat> queryItemCat(@RequestParam(value = "id" ,defaultValue = "0") long parentId){
        ItemCat itemCat = new ItemCat();
        itemCat.setParentId(parentId);
        return itemCatService.queryListByWhere(itemCat);
    }
}
