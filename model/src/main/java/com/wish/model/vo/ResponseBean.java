package com.wish.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by wish on 2018/1/19.
 */
@ApiModel("响应")
public class ResponseBean <T> {

    //成功
    public static Integer CODE_SUCCESS = 200;
    //失败
    public static Integer CODE_ERROR = 201;
    //无权限
    public static Integer CODE_UNAUTH = 202;

    @ApiModelProperty("code，200：成功 201失败")
    private int code;
    @ApiModelProperty("内容")
    private T content;
    @ApiModelProperty("消息")
    private String message;

    public static ResponseBean responseSuccess(Object content) {
        ResponseBean responseBean = new ResponseBean();
        responseBean.code = CODE_SUCCESS;
        responseBean.content = content;
        return responseBean;
    }

    public static ResponseBean responseSuccess(Object content, String message) {
        ResponseBean responseBean = new ResponseBean();
        responseBean.code = CODE_SUCCESS;
        responseBean.content = content;
        responseBean.message = message;
        return responseBean;
    }

    public static ResponseBean responseSuccess(String message) {
        ResponseBean responseBean = new ResponseBean();
        responseBean.code = CODE_SUCCESS;
        responseBean.message = message;
        return responseBean;
    }

    public static ResponseBean errorResponse(int code, String message, Object content) {
        ResponseBean responseBean = new ResponseBean();
        responseBean.code = code;
        responseBean.message = message;
        responseBean.content = content;
        return responseBean;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
