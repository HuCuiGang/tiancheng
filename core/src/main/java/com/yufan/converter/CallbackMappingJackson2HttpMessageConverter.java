package com.yufan.converter;

import com.fasterxml.jackson.core.JsonEncoding;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class CallbackMappingJackson2HttpMessageConverter extends MappingJackson2HttpMessageConverter {

    private Logger LOGGER = LoggerFactory.getLogger(CallbackMappingJackson2HttpMessageConverter.class);

    /**
     * 回调函数的名字
     */
    private String callbackName;

    /**
     *
     * @param object 处理器返回的对象
     * @param outputMessage
     * @throws IOException
     * @throws HttpMessageNotWritableException
     */
    @Override
    protected void writeInternal(Object object, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {

        //1.获取request对象
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

        //获取当前线程上的request对象
        HttpServletRequest request= servletRequestAttributes.getRequest();

        //获取回调函数
        String callback = request.getParameter(callbackName);

        if(StringUtils.isEmpty(callback)){
            LOGGER.debug("直接返回json数据");

            //直接返回json数据
            super.writeInternal(object, outputMessage);
        }else{
            LOGGER.debug("返回jsonp,回调函数的名字为：{}",callbackName);

            //需要返回jsonp
            JsonEncoding encoding = getJsonEncoding(outputMessage.getHeaders().getContentType());

            //把目标对象转成jsonp格式
            String result = callback+"("+super.getObjectMapper().writeValueAsString(object)+")";

            //利用IO工具类操作response对象返回jsonp数据
            IOUtils.write(result,outputMessage.getBody(),encoding.getJavaName());

        }

    }

    public String getCallbackName() {
        return callbackName;
    }

    public void setCallbackName(String callbackName) {
        this.callbackName = callbackName;
    }
}
