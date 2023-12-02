package top.roud.cms.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

@Component
public class IPUtil {

    @Autowired
    private HttpUtil httpUtil;

    public String getIpAddr(HttpServletRequest request) {
        String ipAddress;
        try {
            ipAddress = request.getHeader("x-forwarded-for");
            if (ipAddress == null || ipAddress.length() == 0
                    || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0
                    || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0
                    || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getRemoteAddr();
                if (ipAddress.equals("127.0.0.1")) {
                    // 根据网卡取本机配置的IP
                    InetAddress inet = null;
                    try {
                        inet = InetAddress.getLocalHost();
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    }
                    ipAddress = inet.getHostAddress();
                }
            }
            // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
            if (ipAddress != null && ipAddress.length() > 15) { // "***.***.***.***".length()
                // = 15
                if (ipAddress.indexOf(",") > 0) {
                    ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
                }
            }
        } catch (Exception e) {
            ipAddress = "";
        }
        // ipAddress = this.getRequest().getRemoteAddr();

        return ipAddress;
    }
    public String getRealAddr(String ip) {
        try {
            Map<String, String> map = new HashMap<>();
            map.put("Host","whois.pconline.com.cn");
            map.put("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/112.0.0.0 Safari/537.36");
            String json;
            try {
                byte[] bytes = httpUtil.get("http://whois.pconline.com.cn/ipJson.jsp?ip=" + ip + "&json=true", byte[].class);
                json = new String(bytes, "GBK");
            } catch (Exception e) {
                return "未知地区";
            }
            JSONObject jsonObject = JSON.parseObject(json);
            String pro = jsonObject.getString("pro");
            String city = jsonObject.getString("city");
            if(StringUtils.isBlank(pro+city)){
                return "未知地区";
            }
            return pro+city;
        }catch (Exception e){
            return "未知地区";
        }
    }

    public String getRealAddr(HttpServletRequest request) {
        String ip = getIpAddr(request);
        return getRealAddr(ip);
    }

}

