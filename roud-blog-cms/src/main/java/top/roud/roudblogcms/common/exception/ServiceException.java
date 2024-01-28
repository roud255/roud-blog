package top.roud.roudblogcms.common.exception;


import top.roud.roudblogcms.common.result.ResultCode;

/**
 * @author roud
 * @version 1.0.0
 * @describe
 * @date 2023-12-02 4:12
 */
public class ServiceException extends RuntimeException {
    public ResultCode resultCode;
    public ServiceException(ResultCode resultCode, Throwable cause) {
        super(resultCode.message(), cause);
        this.resultCode = resultCode;
    }

    public ServiceException(ResultCode resultCode) {
        super(resultCode.message());
        this.resultCode = resultCode;
    }
}
