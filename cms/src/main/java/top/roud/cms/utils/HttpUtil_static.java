package top.roud.cms.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import top.roud.cms.common.HttpResult;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @description : TODO
 * @author: guangrui_hu
 * @date: 2023/3/2
 * @version:
 */
public class HttpUtil_static {
    public static HttpResult doPost(String url, JSON json, int connectTimeout, int socketTimeout){
        HttpPost httpPost = new HttpPost(url);
        CloseableHttpClient client = HttpClients.createDefault();
        //请求参数转JOSN字符串
        StringEntity entity = new StringEntity(json.toString(), "UTF-8");
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(connectTimeout).setSocketTimeout(socketTimeout).build();
        httpPost.setConfig(requestConfig);
        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/json");
        httpPost.setEntity(entity);
        HttpResult result = new HttpResult();
        try {
            HttpResponse resp = client.execute(httpPost);
            String body = EntityUtils.toString(resp.getEntity(), "UTF-8");
            int statusCode = resp.getStatusLine().getStatusCode();
            result.setStatus(statusCode);
            result.setBody(body);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


    public static <T> HttpResult get(String url, Map<String, Object> dataMap, Map<String, String> header, TypeReference<T> typeReference) throws IOException {
        CloseableHttpResponse resp = null;
        CloseableHttpClient client = HttpClients.createDefault();
        if (header == null) header = Collections.emptyMap();
        if (dataMap != null && !dataMap.isEmpty()) {
            url += "?" + dataMap.entrySet().stream().map(e -> e.getKey() + "=" + e.getValue()).collect(Collectors.joining("&"));
        }
        HttpGet get = new HttpGet(url);
        header.entrySet().stream().forEach(entry -> get.setHeader(entry.getKey(), entry.getValue()));
        HttpResult result = new HttpResult();
        try {
            resp = client.execute(get);
            String body = EntityUtils.toString(resp.getEntity(), "UTF-8");
            int statusCode = resp.getStatusLine().getStatusCode();
            result.setStatus(statusCode);
            result.setBody(body);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
