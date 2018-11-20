package com.yufan.result;

public class Result<T> {
    //商品信息没有传递的Code值
    public static final int NO_ITEM = 100;
    public static final int NO_DESC = 101;

    /**
     * 1.success 成功  2.fail  失败
     */
    private String status;
    /**
     * 状态码
     */
    private int code;
    /**
     * 失败信息
     */
    private String message;
    /**
     *
     * 成功数据
     */
    private T data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
