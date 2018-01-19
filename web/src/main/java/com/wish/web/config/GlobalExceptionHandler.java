package com.wish.web.config;

import com.wish.common.exception.CommonException;
import com.wish.model.vo.ResponseBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 异常处理
 * Created by wish on 2018/1/19.
 */
@ControllerAdvice({"com.wish.web.controller"})
public class GlobalExceptionHandler {

    private static Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);



    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Object exception(MissingServletRequestParameterException ex) {

        String detail = getDetail(ex);

        StringBuilder msg = new StringBuilder("参数 ");
        msg.append(ex.getParameterName());
        msg.append(" 为必传参数,类型为 ");
        msg.append(ex.getParameterType());

        return ResponseBean.errorResponse(400,msg.toString(),detail);

    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Object exception(MethodArgumentTypeMismatchException ex) {

        String detail = getDetail(ex);

        StringBuilder msg = new StringBuilder("参数类型不匹配: 参数 ");
        msg.append(ex.getName());
        msg.append(" 类型为 ");
        msg.append(ex.getRequiredType().getSimpleName());

        return ResponseBean.errorResponse(400,msg.toString(),detail);

    }





    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public Object exception(Exception ex) {

        String detail = getDetail(ex);

        if(ex instanceof CommonException){
            return ResponseBean.errorResponse(((CommonException) ex).getErrCode(),ex.getMessage(),detail);
        }

        if(ex.getClass().getName().startsWith("com.bbd") || ex.getClass().getName().startsWith("com.components")){

            return ResponseBean.errorResponse(500,ex.getMessage(),detail);

        }else{

            return ResponseBean.errorResponse(500,"数据请求出现故障.",detail);

        }
    }

    private String getDetail(Exception ex){
        LOGGER.error(ex.getMessage(),ex);
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);

        return sw.toString();
    }

}
