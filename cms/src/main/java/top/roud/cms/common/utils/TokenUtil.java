package top.roud.cms.common.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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


    public String getLargeToken(String key){
        return threeCacheUtil.getByCache(key);
    }

    public String getToken(HttpServletRequest request){
        String token = request.getHeader("token");
        String largeToken = getLargeToken(token);
        return largeToken;
    }

    public String getOutToken(String realToekn){
        String randCode = RandomCodeUtil.getRandomCode(6);
        String uuid = UUID.randomUUID().toString().replace("-", "");
        String outToken = randCode+"_"+uuid;
        threeCacheUtil.putToCache(outToken, realToekn, 3);
        return outToken;
    }

    public void signOutToken(String key){
        threeCacheUtil.delCache(key);
    }
}
