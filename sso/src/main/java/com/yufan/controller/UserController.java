package com.yufan.controller;


import com.yufan.bean.User;
import com.yufan.enums.StatusCode;
import com.yufan.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
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
            StatusCode.VALID_FAIL.setMessage(message);
            return ResultUtils.buildFail(StatusCode.VALID_FAIL);
        }
        LOGGER.info("校验成功，开始注册....");
        userService.register(user);
        LOGGER.info("注册成功,用户名{}",user.getUsername());
        return ResultUtils.buildSuccess();
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
        String message= StringUtils.join(list,",");
        LOGGER.info("校验失败，失败信息为：{}",message);
        return message;
    }

    @RequestMapping(value = "/login")
    public Result login(String username,String password) throws CustomerException {
        if(StringUtils.isEmpty(username)){
            LOGGER.info("用户名不能为空!");
            return ResultUtils.buildFail(StatusCode.USERNAME_NOT_EMPTY);
        }
        if(StringUtils.isEmpty(password)){
            LOGGER.info("密码不能为空!");
            return ResultUtils.buildFail(StatusCode.PASSWROD_NOT_EMPTY);
        }

        String token=userService.login(username,password);

        if(StringUtils.isEmpty(token)){
            LOGGER.info("登录失败,用户名密码错误");
            return ResultUtils.buildFail(StatusCode.USERNAME_AND_PASSWORD_ERROR);
        }
        return ResultUtils.buildSuccess(token);
    }

    @RequestMapping(value = "/login/{phone}",method = RequestMethod.POST)
    public Result loginByPhone(@PathVariable("phone") String phone,String code) throws CustomerException {
        if(StringUtils.isEmpty(phone)){
            LOGGER.info("电话号码不能为空!");
            return ResultUtils.buildFail(StatusCode.PHONE_NOT_EMPTY);
        }
        if(StringUtils.isEmpty(code)){
            LOGGER.info("验证码不能为空!");
            return ResultUtils.buildFail(StatusCode.CODE_NOT_EMPTY);
        }

        //校验电话号码格式
        if(!phone.matches("^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$")){
            return ResultUtils.buildFail(StatusCode.PHONE_TYPE_FAIL);
        }

        String token=userService.loginByPhone(phone,code);

        if(StringUtils.isEmpty(token)){
            LOGGER.info("验证码错误!");
            return ResultUtils.buildFail(StatusCode.CODE_FAIL);
        }

        return ResultUtils.buildSuccess(token);
    }

    @RequestMapping(value = "/{token}",method = RequestMethod.GET)
    public Result queryUserByToken(@PathVariable("token") String token) throws CustomerException {
        LOGGER.info("查询的token为：{}",token);
        User user = userService.queryUserByToken(token);
        return ResultUtils.buildSuccess(user);
    }



}
