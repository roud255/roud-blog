package top.roud.cms.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.roud.cms.common.exception.ServiceException;
import top.roud.cms.common.result.ResultCode;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * @description:
 * @author: roud
 * @date: 2023/11/21
 * @version: 1.0.0
 */
@Component
public class TokenUtil {
    @Autowired
    private ThreeCacheUtil threeCacheUtil;


    public String getLargeToken(String outToken){
        return threeCacheUtil.getByCache(outToken);
    }

    public String getToken(HttpServletRequest request){
        try{
            String token = request.getHeader("token");
            String largeToken = getLargeToken(token);
            if(StringUtils.isBlank(largeToken)){
                throw new NullPointerException();
            }
            return largeToken;
        }catch (NullPointerException e){
            throw new ServiceException(ResultCode.TOKEN_INVALID);
        }
    }

    public String getOutToken(String realToekn){
        String randCode = RandomCodeUtil.getRandomCode(6);
        String uuid = UUID.randomUUID().toString().replace("-", "");
        String outToken = randCode+"_"+uuid;
        threeCacheUtil.putToCache(outToken, realToekn, 3);
        return outToken;
    }

    public void signOutToken(String largeToken){
        threeCacheUtil.delCache(largeToken);
    }
}
