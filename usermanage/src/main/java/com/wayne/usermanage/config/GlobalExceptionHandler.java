package com.wayne.usermanage.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * 统一异常处理
 *
 * @ControllerAdvice 是一个控制器增强的工具类，可以在项目处理请求的时候去做一些额外的操作，
 * @ControllerAdvice 注解内部使用 @ExceptionHandler、@InitBinder、@ModelAttribute 注解的方法应用到所有的 @RequestMapping 注解方法。
 * @ExceptionHandler 注解即可监控 Contoller 层代码的相关异常信息。
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    protected Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    public static final String DEFAULT_ERROR_VIEW = "error";

    @ExceptionHandler(value = Exception.class)
    public ModelAndView defaultErrorHandler(Exception e, HttpServletRequest request) throws Exception {
        logger.info("request url: " + request.getRequestURL());
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", e);
        mav.addObject("url", request.getRequestURL());
        logger.error("exception", e);
        mav.setViewName(DEFAULT_ERROR_VIEW);
        return mav;
    }
}
