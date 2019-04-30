package com.yufan.service.impl.rpcIpml;

import com.yufan.bean.Item;
import com.yufan.bean.ItemDesc;
import com.yufan.service.ItemDescService;
import com.yufan.service.ItemRpcService;
import com.yufan.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("itemRpcService")
public class ItemRpcServiceImpl implements ItemRpcService {

    @Autowired
    private ItemService itemService;

    @Autowired
    private ItemDescService itemDescService;

    @Override
    public Item queryItemById(Long itemId) {
        return itemService.queryById(itemId);
    }

    @Override
    public ItemDesc queryItemDescById(Long itemId) {
        return itemDescService.queryById(itemId);
    }
}
