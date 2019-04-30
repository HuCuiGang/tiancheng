package com.yufan.service.impl.rpcIpml;

import com.yufan.bean.ItemCat;
import com.yufan.service.ItemCatRpcService;
import com.yufan.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service("itemCatRpcService")
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
