package com.yufan.exception;

public class CustomerException extends Exception {
    private String message;

    public CustomerException(String message){
        super(message);
        this.message=message;
    }
    @Override
    public String getMessage(){
        return message;
    }

    public void  setMessage(String message){
        this.message=message;
    }

}
