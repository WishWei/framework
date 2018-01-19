package com.wish.common.exception;

/**
 * Created by wish on 2018/1/19.
 */
public class CommonException extends RuntimeException{
    private Integer errCode = 500;

    public CommonException(String message, Throwable cause){
        super(message, cause);
    }

    public CommonException(String message){
        super(message);
    }

    public CommonException(Integer errCode,String message){

        this(message);

        this.errCode = errCode;

    }

    public Integer getErrCode() {
        return errCode;
    }

    public void setErrCode(Integer errCode) {
        this.errCode = errCode;
    }

}
