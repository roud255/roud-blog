package top.roud.cms.common.utils;

/**
 * @author roud
 * @version 1.0.0
 * @describe
 * @date 2023-11-06 23:32
 */
public class AutoIdUtil {
    public static long getId(){
        long nowTemp = System.currentTimeMillis();
        int randNum = (int) (Math.random()*900+100);
        return Long.valueOf(nowTemp + "" + randNum);
    }
}
