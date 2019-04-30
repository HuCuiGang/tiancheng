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

    private static final Integer USER_LOGIN_EXPIRE=60*60*12;

    private Logger LOGGER=LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RedisService redisService;

    @Override
    public void register(User user) throws CustomerException {
        //判空
        checkEmpty(user);
        //判断用户信息是否已经存在
        checkUse(user);
        //生成盐
        String salt= crateSalt();
        //密码加密
        String md5Password=md5Password(user.getPassword(),salt);
         //设置密码
        user.setPassword(md5Password);
        user.setSalt(salt);
        user.setUpdated(new Date());
        user.setCreated(user.getUpdated());
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

    private void checkEmpty(User user) throws CustomerException {
        if(user==null){
            LOGGER.debug("用户信息为为空");
            throw  new CustomerException("用户信息为为空");
        }
        if(StringUtils.isEmpty(user.getUsername())){
            LOGGER.debug("用户名为空");
            throw  new CustomerException("用户名为空");
        }
        if(StringUtils.isEmpty(user.getPhone())){
            LOGGER.debug("电话号码为空");
            throw  new CustomerException("电话号码为空");
        }
        if(StringUtils.isEmpty(user.getPassword())){
            LOGGER.debug("密码为空");
            throw  new CustomerException("密码为空");
        }
        if(StringUtils.isEmpty(user.getEmail())){
            LOGGER.debug("邮箱为空");
            throw  new CustomerException("邮箱为空");
        }

        LOGGER.debug("判空完成!");
    }

    @Override
    public String login(String username, String password) throws CustomerException {
        if(StringUtils.isEmpty(username)){
            LOGGER.debug("用户名为空!");
            throw  new CustomerException("用户名为空");
        }
        if(StringUtils.isEmpty(password)){
            LOGGER.debug("密码为空!");
            throw  new CustomerException("密码为空");
        }

        //处理登录
        User user=userRepository.findUserByUsername(username);

        if(user==null){
            LOGGER.info("用户不存在!");
            throw  new CustomerException("用户不存在");
        }
        //密码加密
        String md5Password = md5Password(password, user.getSalt());

        //校验密码
        if(!user.getPassword().equals(md5Password)){
            LOGGER.info("用户名密码错误!");
            throw  new CustomerException("用户名密码错误");
        }

        //生成token、处理登录、更新上一次登录时间、设置过期时间
        //返回token
        return dealLogin(user);
    }

    @Override
    public String loginByPhone(String phone, String code) throws CustomerException {
        if (StringUtils.isEmpty(phone)){
            LOGGER.debug("电话号码不能为空！");
            throw  new CustomerException("电话号码不能为空");
        }
        if(StringUtils.isEmpty(code)){
            LOGGER.debug("验证码不能为空！");
            throw  new CustomerException("验证码不能为空");
        }

        User user =userRepository.findUserByPhone(phone);

        if (user==null){
            LOGGER.info("手机号码未注册{}",phone);
            throw  new CustomerException("该手机号未注册");
        }

        //校验验证码
        String serverCode=redisService.get(SmsServiceImpl.LOGIN_SMS+phone);

        if (StringUtils.isEmpty(serverCode)){
            LOGGER.info("该验证码已失效{}",phone);
            throw new CustomerException("该验证码已失效");
        }

        if(!code.equals(serverCode)){
            LOGGER.info("验证码错误");
            throw new CustomerException("验证码错误");
        }

        //登录成功后删除验证码
        redisService.del(SmsServiceImpl.LOGIN_SMS+phone);

        //处理登录请求
        return dealLogin(user);
    }

    private String dealLogin(User user) {
        //生成最后登录的时间
        user.setLastLoginTime(new Date());
        userRepository.save(user);
        //生成token
        String token=createToken();
        //处理登录
        redisService.set(LOGIN+token,JsonUtils.objectToJson(user));
        //设置过期时间
        redisService.expire(LOGIN+token,60*60*2);

        LOGGER.info("登录成功，token为：{}",token);
        return token;
    }

    @Override
    public User queryUserByToken(String token) throws CustomerException {
       if (StringUtils.isEmpty(token)){
           LOGGER.debug("token不能为空！");
           throw  new CustomerException("token不能为空");
       }
       String json = redisService.get(LOGIN+token);

       if(StringUtils.isEmpty(json)){
           LOGGER.info("登录失效，token为{}",token);
           throw  new CustomerException("登录失效");
       }

       LOGGER.info("查询登录用户的信息为：{}",json);

       //更新过期时间
        redisService.expire(LOGIN+token,60*60*2);

        return JsonUtils.jsonToPojo(json,User.class);
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
