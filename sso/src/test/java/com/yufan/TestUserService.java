package com.yufan;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.yufan.bean.User;
import com.yufan.exception.CustomerException;
import com.yufan.service.UserService;

@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/applicationContext.xml")
public class TestUserService {

    @Autowired
    private UserService userService;

    @Test
    public void testUser(){

        User user=new User();
        user.setUsername("yufan030");
        user.setPassword("123456");
        user.setEmail("sdasfcom");
        user.setPhone("123456789");

        try {
            userService.register(user);
        } catch (CustomerException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    @Test
    public void test1(){
        Integer i=2;
        Integer y=4;
        System.out.println("----------------------------------------------------------------------------------"+(i>y));
    }

}
