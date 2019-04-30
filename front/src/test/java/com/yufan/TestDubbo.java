package com.yufan;

import com.yufan.bean.Item;
import com.yufan.service.ItemRpcService;
import com.yufan.utils.JsonUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.junit.Ignore;
@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/applicationContext.xml"})
public class TestDubbo {

    @Autowired
    private ItemRpcService itemRpcService;

    @Test
    public void Test(){
        Item item = itemRpcService.queryItemById(1L);
        System.out.println(JsonUtils.objectToJson(item));
    }
}
