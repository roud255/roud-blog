package top.roud.cms.utils;

import top.roud.cms.common.Result;

import java.math.BigInteger;
import java.security.MessageDigest;

import static top.roud.cms.common.ResultCode.SYSTEM_INNER_ERROR;


/**
 * @ClassName: MD5Util
 * @Description:
 * @Author roud
 * @Date 2022/5/29
 * @Version 1.0
 */
public class MD5Util {
    public static String md5(String str) {
        byte[] digest = null;
        String string = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            digest = md.digest(str.getBytes("UTF-8"));
            string = new BigInteger(1,digest).toString(16);
        } catch (Exception e) {
            return Result.failure(SYSTEM_INNER_ERROR).getMsg();
        }
        return string.toUpperCase();
    }
}
