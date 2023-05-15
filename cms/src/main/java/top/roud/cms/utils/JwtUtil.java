package top.roud.cms.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;

import java.util.Calendar;
import java.util.Map;

/**
 * @ClassName: JwtUtil
 * @Description:
 * @Author roud
 * @Date 2022/9/23
 * @Version 1.0
 */
public class JwtUtil {
    /**
     * 过期时间3天
     */
    private static final int EXPIRE_TIME = 3;
    /**
     * jwt 密钥
     */
    private static final String SECRET = "JWT+Y=hs-ak*%Roud$";

    /**
     * 生成签名，过期时间
     * @param userId
     * @param info，Map的value只能存放的值的类型为：Map, List, Boolean, Integer, Long, Double, String and Date
     * @return
     */
    public static String sign(String userId, Map<String,Object> info) {
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
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据token获取userId
     * @param token
     * @return
     */
    public static String getUserId(String token) {
        try {
            String userId = JWT.decode(token).getAudience().get(0);
            return userId;
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 根据token获取自定义数据info
     * @param token
     * @return
     */
    public static Map<String,Object> getInfo(String token) {
        try {
            return JWT.decode(token).getClaim("info").asMap();
        } catch (JWTDecodeException e) {
            return null;
        }
    }


    /**
     * 校验token
     * @param token
     * @return
     */
    public static boolean checkSign(String token){
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