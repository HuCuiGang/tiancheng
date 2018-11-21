package com.yufan.service;

import com.yufan.exception.CustomerException;

public interface SmsService {

    public void sendLoginSms(String phone) throws CustomerException;

}
