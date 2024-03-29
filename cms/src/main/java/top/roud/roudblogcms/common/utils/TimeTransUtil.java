package top.roud.roudblogcms.common.utils;

import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @ClassName: TimeTransUtil
 * @Description:
 * @Author roud
 * @Date 2022/9/30
 * @Version 1.0
 */
@Component
public class TimeTransUtil {

    public Date transUTCToZ(String time) throws ParseException {
        String dateTime = time.replace("Z", " UTC"); //2019-06-27T16:00:00.000 UTC
        SimpleDateFormat format_z = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");//转换时区格式
        Date t= format_z.parse(dateTime);
        return t;
    }

    public String transCSTToGeneral(String time) throws ParseException {
        DateFormat cst = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DateFormat gmt = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
        Date dateTime = gmt.parse(time);
        String t = cst.format(dateTime);
        return t;
    }
}
