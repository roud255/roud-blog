package top.roud.roudblogcms.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import top.roud.roudblogcms.common.config.RandomCodeUtil;
import top.roud.roudblogcms.common.exception.ServiceException;
import top.roud.roudblogcms.common.result.ResultCode;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static top.roud.roudblogcms.common.utils.ConstUtil.USER_OUTTOKEN_CONTACT;


/**
 * @description:
 * @author: roud
 * @date: 2023/11/21
 * @version: 1.0.0
 */
@Component
public class TokenUtil {
    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private JwtUtil jwtUtil;

    @Value("${roudblog.cache.save.days}")
    private int cacheSaveDays;

    public String getLargeToken(String key){
        return redisUtil.get(key);
    }

    public String getOutToken(String realToekn){
        String randCode = RandomCodeUtil.getRandomCode(6);
        String uuid = UUID.randomUUID().toString().replace("-", "");
        String outToken = randCode+"_"+uuid;
        redisUtil.set(outToken, realToekn, cacheSaveDays, TimeUnit.DAYS);
        return outToken;
    }

    public void signOutToken(String key){
        redisUtil.delete(key);
    }

    public void relevanceAuthorAndOutToken(String aid, String outToken){
        String relationKey = USER_OUTTOKEN_CONTACT + aid;
        this.delRelevanceAuthorAndOutToken(relationKey);
        redisUtil.set(relationKey, outToken, cacheSaveDays, TimeUnit.DAYS);
    }

    public void delRelevanceAuthorAndOutToken(String key){
        String outToken = redisUtil.get(key);
        if(StringUtils.isBlank(outToken)){
            return;
        }
        redisUtil.delete(outToken);
        redisUtil.delete(key);
    }

    public String getOutTokenByRequest(HttpServletRequest request){
        String token = request.getHeader("token");
        return token;
    }

    public Map<String, Object> getUserInfoByOutToken(String outToken){
        String largeToken = getLargeToken(outToken);
        if(StringUtils.isBlank(largeToken)){
            throw new ServiceException(ResultCode.TOKEN_EXPIRED);
        }
        if(!jwtUtil.checkSign(largeToken)){
            throw new ServiceException(ResultCode.TOKEN_INVALID);
        }
        Map<String, Object> info = jwtUtil.getInfo(largeToken);
        return info;
    }

    public Map<String, Object> getUserInfoByRequest(HttpServletRequest request){
        String outToken = getOutTokenByRequest(request);
        if(StringUtils.isBlank(outToken)){
            throw new ServiceException(ResultCode.TOKEN_INVALID);
        }
        return getUserInfoByOutToken(outToken);
    }

    public boolean checkTokenByRequest(HttpServletRequest request){
        String outToken = getOutTokenByRequest(request);
        if(StringUtils.isBlank(outToken)){
            return false;
        }
        String largeToken = getLargeToken(outToken);
        if(StringUtils.isBlank(largeToken)){
            return false;
        }
        return jwtUtil.checkSign(largeToken);
    }

}
