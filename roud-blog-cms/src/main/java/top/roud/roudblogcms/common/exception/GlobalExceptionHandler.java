//package top.roud.roudblogcms.common.exception;
//
//import org.springframework.web.HttpRequestMethodNotSupportedException;
//import org.springframework.web.bind.MissingServletRequestParameterException;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.multipart.MaxUploadSizeExceededException;
//import top.roud.roudblogcms.common.result.Result;
//import top.roud.roudblogcms.common.utils.LoggerUtil;
//
//import java.sql.SQLSyntaxErrorException;
//
//import static top.roud.roudblogcms.common.result.ResultCode.DATA_WRONG;
//import static top.roud.roudblogcms.common.result.ResultCode.FILE_IS_OVERFLOW;
//import static top.roud.roudblogcms.common.result.ResultCode.METHOD_NOT_SUPPORTED;
//import static top.roud.roudblogcms.common.result.ResultCode.PARAM_NOT_COMPLETE;
//import static top.roud.roudblogcms.common.result.ResultCode.SYSTEM_INNER_ERROR;
//
//
///**
// * @description : TODO
// * @author: roud
// * @date: 2023/5/10
// * @version:
// */
//@ControllerAdvice
//@ResponseBody
//public class GlobalExceptionHandler {
//    @ExceptionHandler(MissingServletRequestParameterException.class)
//    public Result handleHttpMessageNotReadableException(MissingServletRequestParameterException ex){
//        LoggerUtil.ex.error(String.valueOf(ex));
//        return Result.failure(PARAM_NOT_COMPLETE);
//    }
//    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
//    public Result handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex){
//        LoggerUtil.ex.error(String.valueOf(ex));
//        return Result.failure(METHOD_NOT_SUPPORTED);
//    }
//    @ExceptionHandler(SQLSyntaxErrorException.class)
//    public Result handleSQLSyntaxErrorException(SQLSyntaxErrorException ex){
//        LoggerUtil.ex.error(String.valueOf(ex));
//        return Result.failure(DATA_WRONG);
//    }
//
//    @ExceptionHandler(MaxUploadSizeExceededException.class)
//    public Result handleMaxUploadSizeExceededException(MaxUploadSizeExceededException ex){
//        LoggerUtil.ex.error(String.valueOf(ex));
//        return Result.failure(FILE_IS_OVERFLOW);
//    }
//
//    @ExceptionHandler(ServiceException.class)
//    public Result handleServiceException(ServiceException ex){
//        LoggerUtil.ex.error(String.valueOf(ex));
//        return Result.failure(ex.resultCode);
//    }
//
//    @ExceptionHandler(Exception.class)
//    public Result handleException(Exception ex){
//        LoggerUtil.ex.error(String.valueOf(ex));
//        return Result.failure(SYSTEM_INNER_ERROR);
//    }
//}
