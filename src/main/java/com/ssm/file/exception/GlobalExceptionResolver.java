package com.ssm.file.exception;

import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** 将上传超限等异常转为友好提示页，不向页面打印堆栈。 */
@Component
public class GlobalExceptionResolver implements HandlerExceptionResolver {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionResolver.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response,
                                         Object handler, Exception ex) {
        Throwable cause = ex;
        int depth = 0;
        boolean tooLarge = ex instanceof MaxUploadSizeExceededException
                || ex instanceof MultipartException
                || ex instanceof IllegalStateException;
        while (cause != null && depth++ < 8) {
            if (cause instanceof MaxUploadSizeExceededException) {
                tooLarge = true;
                break;
            }
            cause = cause.getCause();
        }
        log.warn("请求处理失败: {}", ex.toString());
        ModelAndView mv = new ModelAndView("error");
        if (tooLarge) {
            mv.addObject("message", "文件大小超过 10MB，上传已被拒绝。请压缩后再试，或分多次上传。");
        } else {
            mv.addObject("message", "操作未能完成，请返回后重试。");
        }
        return mv;
    }
}
