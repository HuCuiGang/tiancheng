package com.yufan.controller;

import com.sun.org.apache.bcel.internal.generic.LOOKUPSWITCH;
import com.yufan.exception.CustomerException;
import com.yufan.result.Result;
import com.yufan.result.ResultUtils;
import com.yufan.service.SmsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
@RequestMapping("/sms")
public class SmsController {
    private Logger LOGGER = LoggerFactory.getLogger(SmsController.class);

    @Autowired
    private SmsService smsService;

    @RequestMapping(value = "/login/{phone}",method = RequestMethod.GET)
    public Result sendLoginSms(@PathVariable("phone") String phone) throws CustomerException {
        LOGGER.info("发送短信的手机号码为{}",phone);
        smsService.sendLoginSms(phone);
        return ResultUtils.buildSuccess();
    }
}
