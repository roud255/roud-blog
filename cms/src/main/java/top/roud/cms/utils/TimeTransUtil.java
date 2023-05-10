package top.roud.cms.utils;

import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
}
