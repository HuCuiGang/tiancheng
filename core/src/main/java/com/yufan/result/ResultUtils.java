package com.yufan.result;


public class ResultUtils {

    /**
     * 1.成功不带返回数据
     * @return
     */
    public static Result buildSuccess(){
        Result result = new Result();
        result.setStatus("success");
        return result;

    }

    /**
     * 2.成功带数据
     * @param data
     * @return
     */
    public static Result buildSuccess(Object data){
        Result result = new Result();
        result.setStatus("success");
        result.setData(data);
        return result;
    }

    /**
     * 构建失败信息
     * @param code
     * @param message
     * @return
     */
    public static Result buildFail(int code,String message){
        Result result = new Result();
        result.setCode(code);
        result.setMessage(message);
        result.setStatus("fail");
        return result;
    }

}
