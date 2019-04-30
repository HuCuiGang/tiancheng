package com.yufan.enums;

public enum StatusCode {

    EXCEPTION(500000,"系统开小差了"),
    USERNAME_NOT_EMPTY(500001,"用户名不能为空"),
    IDS_NOT_EMPTY(500002,"ID不能为空"),
    PASSWROD_NOT_EMPTY(500003,"密码不能为空"),
    VALID_FAIL(500004,"校验失败"),
    USERNAME_AND_PASSWORD_ERROR(500005,"用户名密码错误"),
    PHONE_NOT_EMPTY(500006,"电话号码不能为空"),
    CODE_NOT_EMPTY(500007,"验证码不能为空"),
    CODE_FAIL(500008,"验证码错误,或者已经失效"),
    PHONE_TYPE_FAIL(500009,"电话号码格式不正确"),
    ;

    private Integer code;
    private String message;

    StatusCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
