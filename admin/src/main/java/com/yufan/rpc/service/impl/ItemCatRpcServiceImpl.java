package com.yufan.rpc.service.impl;

import com.yufan.bean.ItemCat;
import com.yufan.rpc.service.ItemCatRpcService;
import com.yufan.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ItemCatRpcServiceImpl implements ItemCatRpcService {

    @Autowired
    private ItemCatService itemCatService;
    @Override
    public List<ItemCat> queryItemCat(Long parentId) {
        ItemCat itemCat = new ItemCat();
        itemCat.setParentId(parentId);
        return itemCatService.queryListByWhere(itemCat) ;
    }
}
