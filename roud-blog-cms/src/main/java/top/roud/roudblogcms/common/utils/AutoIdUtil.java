package top.roud.roudblogcms.common.utils;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author roud
 * @version 1.0.0
 * @describe
 * @date 2023-11-06 23:32
 */
public class AutoIdUtil {
    private static final AtomicInteger seriesNum = new AtomicInteger(1);

    private static String getSeriesNumStr(){
        int length = 4;
        if(seriesNum.get() > 9999){
            seriesNum.set(1);
        }
        int number = seriesNum.getAndIncrement();
        return String.format("%0" + length + "d", number);
    }
    public static long getId(){
        long nowTemp = System.currentTimeMillis();
        //int randNum = (int) (Math.random()*900+100);
        return Long.parseLong(nowTemp + getSeriesNumStr());
    }
}
