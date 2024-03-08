package top.roud.roudblogcms.common.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Map;

/**
 * @ClassName: JwtUtil
 * @Description:
 * @Author roud
 * @Date 2022/9/23
 * @Version 1.0
 */
@Component
public class JwtUtil {
    @Value("${token.inner.expiredtime}")
    private int EXPIRE_TIME;
    @Value("${token.inner.secret}")
    private  String SECRET ;

    public String sign(String userId, Map<String,Object> info) {
        try {
            Calendar instance = Calendar.getInstance();
            instance.add(Calendar.DATE, EXPIRE_TIME);
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            return JWT.create()
                    // 将 user id 保存到 token 里面
                    .withAudience(userId)
                    // 存放自定义数据
                    .withClaim("info", info)
                    // x天后token过期
                    .withExpiresAt(instance.getTime())
                    // token 的密钥
                    .sign(algorithm);
        } catch (Exception e) {
            return "";
        }
    }

    public String getUserId(String token) {
        try {
            String userId = JWT.decode(token).getAudience().get(0);
            return userId;
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    public Map<String, Object> getInfo(String token) {
        try {
            return JWT.decode(token).getClaim("info").asMap();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    public boolean checkSign(String token){
        Algorithm algorithm = Algorithm.HMAC256(SECRET);
        JWTVerifier verifier = JWT.require(algorithm).build();
        try{
            verifier.verify(token);
        }catch (Exception e){
            return false;
        }
        return true;
    }
}