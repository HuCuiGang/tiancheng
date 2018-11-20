package com.yufan.service.impl;

import com.yufan.bean.User;
import com.yufan.redis.RedisService;
import com.yufan.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import com.yufan.exception.CustomerException;
import com.yufan.repository.UserRepository;
import com.yufan.utils.JsonUtils;

import java.util.Date;
import java.util.UUID;


@Service
@Transactional
public class UserServiceImpl implements UserService {

    private static final String LOGIN="login:";

    private Logger LOGGER=LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RedisService redisService;

    @Override
    public void register(User user) throws CustomerException {
        if(!checkEmpty(user)){
            LOGGER.debug("校验失败!");
            return;
        }
        LOGGER.info("校验用户信息是否使用....");
        checkUse(user);
        LOGGER.info("开始执行注册....");

        //生成盐
        String salt= crateSalt();
        //设置盐
        user.setSalt(salt);

        //密码加密
        String md5Password=md5Password(user.getPassword(),salt);
         //设置密码
        user.setPassword(md5Password);
        user.setUpdated(new Date());
        user.setCreated(new Date());

        //保存用户到数据库
        userRepository.save(user);
    }

    /**
     * 随机生成盐
     * @return
     */
    private String crateSalt() {
        Date date=new Date();
        return DigestUtils.md5DigestAsHex((date.toString()+UUID.randomUUID().toString()).getBytes());
    }

    /**
     * 密码加密
     * @param password 密码
     * @param salt 盐
     * @return
     */
    private String md5Password(String password, String salt) {
        return DigestUtils.md5DigestAsHex((salt+password).getBytes());
    }


    /**
     * 校验用户信息是否使用
     * @param user
     * @throws CustomerException 如果抛出异常代表使用过相关信息
     */
    private void checkUse(User user) throws CustomerException {
        if(userRepository.findUserByUsername(user.getUsername())!=null){
            LOGGER.info("用户名已经注册:"+user.getUsername());
           throw  new CustomerException("用户名已经注册:"+user.getUsername());
        }
        if(userRepository.findUserByEmail(user.getEmail())!=null){
            LOGGER.info("邮箱已经注册:"+user.getEmail());
            throw  new CustomerException("邮箱已经注册:"+user.getEmail());
        }
        if(userRepository.findUserByPhone(user.getPhone())!=null){
            LOGGER.info("电话号码已经注册:"+user.getPhone());
            throw  new CustomerException("电话号码已经注册:"+user.getPhone());
        }
    }

    private boolean checkEmpty(User user) {
        if(user==null){
            LOGGER.debug("用户信息为为空");
            return false;
        }
        if(StringUtils.isEmpty(user.getUsername())){
            LOGGER.debug("用户名为空");
            return false;
        }
        if(StringUtils.isEmpty(user.getPhone())){
            LOGGER.debug("电话号码为空");
            return false;
        }
        if(StringUtils.isEmpty(user.getPassword())){
            LOGGER.debug("密码为空");
            return false;
        }
        if(StringUtils.isEmpty(user.getEmail())){
            LOGGER.debug("邮箱为空");
            return false;
        }
        LOGGER.debug("判空成功!");
        return true;
    }

    @Override
    public String login(String username, String password) {
        if(StringUtils.isEmpty(username)){
            LOGGER.debug("用户名为空!");
            return null;
        }
        if(StringUtils.isEmpty(password)){
            LOGGER.debug("密码为空!");
            return null;
        }

        //处理登录
        User user=userRepository.findUserByUsername(username);
        if(user==null){
            LOGGER.info("用户不存在!");
            return null;
        }
        //密码加密
        String md5Password = md5Password(password, user.getSalt());

        //校验密码
        if(!user.getPassword().equals(md5Password)){
            LOGGER.info("用户名密码错误!");
            return null;
        }
        //生成token
        String token=createToken();
        //处理登录
        redisService.set(LOGIN+token,JsonUtils.objectToJson(user));
        //设置过期时间
        redisService.expire(LOGIN+token,60*60*2);
        return token;
    }

    /**
     * 生成token
     * @return
     */
    private String createToken() {
        Date date=new Date();
        return DigestUtils.md5DigestAsHex((date.toString()+UUID.randomUUID().toString()).getBytes());
    }
}
