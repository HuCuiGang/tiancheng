package com.yufan.controller;


import com.yufan.bean.User;
import com.yufan.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.yufan.exception.CustomerException;
import com.yufan.result.Result;
import com.yufan.result.ResultUtils;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@ResponseBody
@RequestMapping("/user")
public class UserController {

    private Logger LOGGER=LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public Result register(@Valid User user, BindingResult bindingResult) throws CustomerException {
        //判断校验是否失败
        if(bindingResult.hasErrors()){
            String message = getCheckInfo(bindingResult);
            return ResultUtils.buildFail(105,message);
        }
        LOGGER.info("校验成功，开始注册....");
        userService.register(user);
        LOGGER.info("注册成功,用户名{}",user.getUsername());
        return ResultUtils.buildSuccess();
    }


    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public Result login(String username,String password){
        if(StringUtils.isEmpty(username)){
            LOGGER.info("用户名不能为空!");
            return ResultUtils.buildFail(110,"用户名不能为空!");
        }
        if(StringUtils.isEmpty(password)){
            LOGGER.info("密码不能为空!");
            return ResultUtils.buildFail(111,"密码不能为空!");
        }

        String token=userService.login(username,password);
        if(StringUtils.isEmpty(token)){
            LOGGER.info("登录失败!");
            return ResultUtils.buildFail(112,"用户名密码错误");
        }

        return ResultUtils.buildSuccess(token);
    }

    private String getCheckInfo(BindingResult bindingResult) {
        //校验失败
        //取出失败信息
        List<ObjectError> errors = bindingResult.getAllErrors();

        ArrayList list=new ArrayList();
        //取出失败信息,拼成字符串
        for (ObjectError error:errors){
            String msg = error.getDefaultMessage();
            list.add(msg);
        }
        //拼接字符串
        String message=org.apache.commons.lang3.StringUtils.join(list,",");
        LOGGER.info("校验失败，失败信息为{}",message);
        return message;
    }

}
