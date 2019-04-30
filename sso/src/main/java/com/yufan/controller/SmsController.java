package com.yufan.controller;

import com.yufan.enums.StatusCode;
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
        //校验电话号码格式
        if(!phone.matches("^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$")){
            return ResultUtils.buildFail(StatusCode.PHONE_TYPE_FAIL);
        }
        LOGGER.info("发送短信的手机号码为{}",phone);

        smsService.sendLoginSms(phone);
        return ResultUtils.buildSuccess();
    }
}
