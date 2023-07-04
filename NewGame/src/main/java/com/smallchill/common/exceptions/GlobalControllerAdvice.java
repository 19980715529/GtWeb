package com.smallchill.common.exceptions;

import com.smallchill.core.base.controller.BladeController;
import com.smallchill.core.toolbox.ajax.AjaxResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.IOException;

//@ControllerAdvice
public class GlobalControllerAdvice extends BladeController {
    protected Logger LOGGER = LogManager.getLogger(GlobalControllerAdvice.class);
    /**
     * 处理NullPointerException，并返回错误信息字符串
     * @param ex
     * @return
     */
    @ExceptionHandler(CustomException.class)
    @ResponseBody
    public AjaxResult handleIOException(CustomException ex) {
        return  fail(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public AjaxResult handleIOException(Exception ex) {
        return  fail(ex.getMessage());
    }

    /**
     * 处理IOException，并返回502错误, 即"Bad Gateway"
     * @param ex
     * @return
     */
//    @ExceptionHandler(IOException.class)
//    @ResponseStatus(HttpStatus.BAD_GATEWAY)
//    public void handleIOException(IOException ex) {
//
//    }

    /**
     * 处理Custom3Exception异常，并返回带指定错误消息的界面aca
     * @param ex
     * @return
     */
//    @ExceptionHandler(Custom3Exception.class)
//    public ModelAndView handleCustom3Exception(Custom3Exception ex) {
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.addObject("errorMsg", "Custom3Exception=" + ex.getMessage());
//        modelAndView.setViewName("aca");
//        return modelAndView;
//    }

    /**
     * 处理所有Exception类型异常
     * 但请注意，如果该ControllerAdvice内已经有其他方法处理了指定异常，那就不会进入到该方法处理
     * 比如发生Custom3Exception异常，那么只会在@ExceptionHandler(Custom3Exception.class)的方法中处理
     * @param e
     * @return
     */
//    @ExceptionHandler(Exception.class)
//    public ModelAndView Exception(Exception e) {
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.addObject("errorMsg", "服务器处理发生错误，详细信息：" + e.getMessage());
//        modelAndView.setViewName("global-error");
//        return modelAndView;
//    }
}
