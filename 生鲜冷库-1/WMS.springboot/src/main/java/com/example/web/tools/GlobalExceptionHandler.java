package com.example.web.tools;

import com.example.web.SysConst;
import com.example.web.tools.dto.ResponseData;
import com.example.web.tools.exception.CustomException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
/**
 * 全局异常处理切面
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 处理自定义异常
     */
    @ExceptionHandler(value = CustomException.class)
    @ResponseBody
    public ResponseData bizExceptionHandler(CustomException e) {
        ResponseData responseData = new ResponseData();
        responseData.setCode(SysConst.STATUS_500);
        responseData.setMsg(e.getErrorMsg());
        responseData.setSuccess(false);
        return responseData;
    }

    /**
     * 处理其他异常
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseData exceptionHandler(Exception e) {
        ResponseData responseData = new ResponseData();
        responseData.setCode(SysConst.STATUS_500);
        e.printStackTrace();

        System.out.println(e.getMessage());
        responseData.setMsg("系统异常,请联系管理员");
        responseData.setSuccess(false);
        return responseData;
    }
}
