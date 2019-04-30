package com.yufan.service;

import com.yufan.bean.User;
import com.yufan.exception.CustomerException;

public interface UserService {

    /**
     * 注册
     * @param user
     */
    public void register(User user) throws CustomerException;

    /**
     *
     * @param username
     * @param password
     * @return  返回token
     */
    public String login(String username,String password) throws CustomerException;

    /**
     *
     * @param phone
     * @param code
     * @return 返回token
     */
    public String loginByPhone(String phone,String code) throws CustomerException;

    /**
     *
     * @param token
     * @return
     */
    public User queryUserByToken(String token) throws CustomerException;

}
