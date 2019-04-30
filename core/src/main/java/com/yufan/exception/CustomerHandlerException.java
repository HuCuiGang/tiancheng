package com.yufan.exception;

import com.yufan.result.ResultUtils;
import com.yufan.utils.JsonUtils;
import com.yufan.utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class CustomerHandlerException implements HandlerExceptionResolver {
    
    @Autowired
    private MappingJackson2JsonView jackson2JsonView;
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exception) {
        String msg;
        if (exception instanceof CustomerException){
            //自定义异常
            msg=exception.getMessage();
        }else{
            exception.printStackTrace();
            msg="对不起，系统开小差了!";
        }

        //TODO 不能处理返回json的情况
        //解决返回json的情况
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        //获取到处理器具体的方法
        Method method = handlerMethod.getMethod();
        Class clazz=handlerMethod.getBean().getClass();
        //1.先判断类上面有没有ResponseBody注解，如果没有在判断方法上
        ResponseBody responseBody = (ResponseBody) clazz.getAnnotation(ResponseBody.class);
        if(responseBody==null){
            //类上没有就去方法上面找
            responseBody=method.getAnnotation(ResponseBody.class);
        }

        //2.判断方法的返回值类型是不是ResponseEntity
        if (responseBody!=null||method.getReturnType().getName().equals(ResponseEntity.class.getName())){
            //返回json数据
	        //输出流的方式无法被jsonp数据类型接收
            //ResponseUtils.responseJson(response, JsonUtils.objectToJson(ResultUtils.buildFail(500,msg)));
            ModelAndView modelAndView = new ModelAndView(jackson2JsonView);

            modelAndView.addObject(ResultUtils.buildFail(500,msg));

            return modelAndView;
        }

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("msg",msg);
        modelAndView.setViewName("error");
        return modelAndView;

    }
}
