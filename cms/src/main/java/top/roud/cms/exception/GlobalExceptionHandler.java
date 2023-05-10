package top.roud.cms.exception;

import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import top.roud.cms.common.Result;

import static top.roud.cms.common.ResultCode.METHOD_NOT_COMPLETE;
import static top.roud.cms.common.ResultCode.PARAM_NOT_COMPLETE;

/**
 * @description : TODO
 * @author: roud
 * @date: 2023/5/10
 * @version:
 */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Result handleHttpMessageNotReadableException(MissingServletRequestParameterException ex){
        return Result.failure(PARAM_NOT_COMPLETE);
    }
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Result handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex){
        return Result.failure(METHOD_NOT_COMPLETE);
    }
}
