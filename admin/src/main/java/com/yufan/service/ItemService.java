package com.yufan.service;

import com.yufan.bean.Item;

public interface ItemService extends BaseService<Item> {
    public void itemAdd(Item item,String desc);

    void updateItem(Item item, String desc);
}
