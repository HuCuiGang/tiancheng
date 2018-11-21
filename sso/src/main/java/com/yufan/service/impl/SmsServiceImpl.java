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
    static final String product = "Dysmsapi";
    //产品域名,开发者无需替换
    static final String domain = "dysmsapi.aliyuncs.com";

    static final String accessKeyId = "LTAIWbaBZJwzHrJ0";
    static final String accessKeySecret = "vby3WNJfMOi3igIMA18CcAiqWIZOTQ";

    public  static final String LOGIN_SMS="login_sms:";


    @Override
    public void sendLoginSms(String phone) throws CustomerException {
        if(StringUtils.isEmpty(phone)){
           LOGGER.debug("电话号码不能为空");
           return;
        }
        //1.查看用户是否已在注册，没有注册不能发送短信
        if(userRepository.findUserByPhone(phone)==null){
            LOGGER.info("手机号码没有注册{},",phone);
            throw  new CustomerException("手机号码没有注册!");
        }

        //2.TODO查看时间是否超过60秒

        //3.TODO超过多少次不能再次发送

        //生成随机验证码
        StringBuilder sb=new StringBuilder();
        for(int i=0;i<4;i++){
            sb.append(new Random().nextInt(10));
        }
        String code=sb.toString();

        LOGGER.debug("code:{}",code);

        //TODO为了避免扣钱
        //存储到redis服务器:
        redisService.set(LOGIN_SMS+phone,code);
        redisService.expire(LOGIN_SMS+phone,60*5);

        try {
            SendSmsResponse response=   sendSms(phone, code);

            if(!response.getCode().equals("OK")){
                LOGGER.error("短信发送失败{}",phone);
                LOGGER.error("短信接口返回的数据----------------");
                LOGGER.error("Code:{}", response.getCode());
                LOGGER.error("Message:{}" ,response.getMessage());
                LOGGER.error("RequestId:{}" ,response.getRequestId());
                LOGGER.error("BizId:{}" ,response.getBizId());
                throw  new RuntimeException("短信发送失败");
            }

        }catch (ClientException e){
            e.printStackTrace();
            throw  new RuntimeException("短信发送失败");
        }



    }


    private static SendSmsResponse sendSms(String phone,String code) throws ClientException {

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