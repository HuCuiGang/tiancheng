package com.yufan.service;

import com.yufan.bean.Item;
import com.yufan.exception.CustomerException;

public interface ItemService {

    public Item queryItemById(Long itemId) throws CustomerException;

    public void deleteCache(Long itemId);
}
