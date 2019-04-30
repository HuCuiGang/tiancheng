package com.yufan.service;

import com.yufan.bean.Item;
import com.yufan.bean.ItemDesc;

public interface ItemRpcService {

    public Item queryItemById(Long itemId);

    public ItemDesc queryItemDescById(Long itemId);

}
