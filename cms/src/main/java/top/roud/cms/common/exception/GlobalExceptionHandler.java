package top.roud.cms.common.exception;

import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import top.roud.cms.common.result.Result;

import java.sql.SQLSyntaxErrorException;

import static top.roud.cms.common.result.ResultCode.*;

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
        return Result.failure(METHOD_NOT_SUPPORTED);
    }
    @ExceptionHandler(SQLSyntaxErrorException.class)
    public Result handleSQLSyntaxErrorException(SQLSyntaxErrorException ex){
        return Result.failure(DATA_WRONG);
    }
    @ExceptionHandler(Exception.class)
    public Result handleException(Exception ex){
        return Result.failure(SYSTEM_INNER_ERROR);
    }
}
