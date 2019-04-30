package com.yufan.service.impl;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import com.yufan.exception.CustomerException;
import com.yufan.redis.RedisService;
import com.yufan.repository.UserRepository;
import com.yufan.service.SmsService;

import java.util.Random;

@Service
public class SmsServiceImpl implements SmsService {

    private Logger LOGGER=LoggerFactory.getLogger(SmsServiceImpl.class);

    @Autowired
    private RedisService redisService;

    @Autowired
    private UserRepository userRepository;

    //产品名称:云通信短信API产品,开发者无需替换
    final String product = "Dysmsapi";
    //产品域名,开发者无需替换
    final String domain = "dysmsapi.aliyuncs.com";

    @Value("${sms.accessKeyId}")
    private String accessKeyId;
    @Value("${sms.accessKeySecret}")
    private String accessKeySecret;
    //存储验证码的key
    public static final String LOGIN_SMS="login_sms:";
    public static final Integer CODE_EXPIRE=60*5;
    //存储验证码发送次数的key
    public static final String LOGIN_SMS_NUM="login_sms_num:";
    public static final Integer LOGIN_SMS_NUM_EXPIRE=60*60*12;
    public static final Integer LOGIN_SMS_NUM_MAX=5;


    @Override
    public void sendLoginSms(String phone) throws CustomerException {
        if(StringUtils.isEmpty(phone)){
           LOGGER.info("电话号码不能为空");
           throw new CustomerException("电话号码不能为空!");
        }
        //1.查看用户是否已在注册，没有注册不能发送短信
        if(userRepository.findUserByPhone(phone)==null){
            LOGGER.info("手机号码没有注册{},",phone);
            throw  new CustomerException("手机号码没有注册!");
        }
        //2.TODO查看时间是否超过60秒
        if(redisService.exists(LOGIN_SMS + phone)){
            Long ttl = redisService.ttl(LOGIN_SMS + phone);
            Long ttlTime = CODE_EXPIRE-ttl;
            LOGGER.debug("距离上一次发送验证码已经过去{}秒",ttlTime);
            if(ttlTime<=60L){
                throw  new CustomerException("请"+(60-ttlTime)+"秒后再发送验证码");
            }
        }

        //3.TODO超过多少次不能再次发送
        //限制发送短信次数
        String loginSmsNum = setLoginSmsNum();
        LOGGER.debug("验证码已经发送{}次",loginSmsNum);

        if(Integer.valueOf(loginSmsNum)>=LOGIN_SMS_NUM_MAX){
            Long ttl = redisService.ttl(LOGIN_SMS_NUM);
            Double ttlNumMaxTime=(Double.valueOf(LOGIN_SMS_NUM_EXPIRE-ttl)/60D/12D);
            throw new CustomerException("验证码发送次数已经达到"+LOGIN_SMS_NUM_MAX+"次上限，"+ttlNumMaxTime+"小时候才能再次发送验证码");
        }

        //生成随机验证码
        StringBuilder sb=new StringBuilder();
        for(int i=0;i<6;i++){
            sb.append(new Random().nextInt(10));
        }
        String code=sb.toString();

        LOGGER.debug("随机生成的验证码为:{}",code);

        try {
            //发送短信
            SendSmsResponse response= sendSms(phone, code);

            if(!response.getCode().equals("OK")){
                LOGGER.error("短信发送失败{}",phone);
                LOGGER.error("短信接口返回的数据----------------");
                LOGGER.error("Code:{}", response.getCode());
                LOGGER.error("Message:{}" ,response.getMessage());
                LOGGER.error("RequestId:{}" ,response.getRequestId());
                LOGGER.error("BizId:{}" ,response.getBizId());
                throw  new CustomerException(response.getMessage());
            }

        }catch (ClientException e){
            e.printStackTrace();
            throw  new CustomerException("短信发送失败");
        }

        //自增验证码发送次数
        redisService.incr(LOGIN_SMS_NUM);
        //存储验证码到redis服务器:
        redisService.set(LOGIN_SMS+phone,code);

        //设置验证码过期时间
        redisService.expire(LOGIN_SMS+phone,CODE_EXPIRE);
    }

    private String setLoginSmsNum() throws CustomerException {
        if (!redisService.exists(LOGIN_SMS_NUM)){
            redisService.set(LOGIN_SMS_NUM, "0");
            //设置验证码发送次数key的过期时间
            redisService.expire(LOGIN_SMS_NUM,LOGIN_SMS_NUM_EXPIRE);
        }
        String loginSmsNum = redisService.get(LOGIN_SMS_NUM);
        return loginSmsNum;
    }


    private  SendSmsResponse sendSms(String phone,String code) throws ClientException {

        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);

        //组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest request = new SendSmsRequest();
        //必填:待发送手机号
        request.setPhoneNumbers(phone);
        //必填:短信签名-可在短信控制台中找到
        request.setSignName("tiancheng");
        //必填:短信模板-可在短信控制台中找到
        request.setTemplateCode("SMS_151577495");


        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        request.setTemplateParam("{\"code\":\""+code+"\"}");


        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        request.setOutId("login:"+phone);

        //hint 此处可能会抛出异常，注意catch
        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);

        return sendSmsResponse;
    }

}
