package top.roud.roudblogcms.common.exception;


import top.roud.roudblogcms.common.result.Result;
import top.roud.roudblogcms.common.result.ResultCode;

/**
 * @description : TODO
 * @author: guangrui_hu
 * @date: 2023/4/28
 * @version:
 */
public class HttpException extends RuntimeException {
    public top.roud.roudblogcms.common.result.ResultCode ResultCode;

    public HttpException() {
        this.ResultCode = ResultCode.SYSTEM_ERROR;
    }

    public HttpException(String message) {
        super(message);
        this.ResultCode = ResultCode.SYSTEM_ERROR;
    }

    public HttpException(String message, Throwable cause) {
        super(message, cause);
        this.ResultCode = ResultCode.SYSTEM_ERROR;
    }

    public HttpException(ResultCode ResultCode) {
        super(ResultCode.message());
        this.ResultCode = ResultCode.SYSTEM_ERROR;
        this.ResultCode = ResultCode;
    }

    public HttpException(ResultCode ResultCode, String msg) {
        super(msg);
        this.ResultCode = ResultCode.SYSTEM_ERROR;
        this.ResultCode = ResultCode;
    }

    public HttpException(Result result) {
        super(result.getMsg());
        this.ResultCode = ResultCode.SYSTEM_ERROR;
    }
}
