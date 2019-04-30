package com.yufan.service;

import com.yufan.bean.ItemCat;

import java.util.List;

public interface ItemCatRpcService {
    public List<ItemCat> queryItemCat(Long parentId);
}
