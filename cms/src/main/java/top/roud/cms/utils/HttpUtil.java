package top.roud.cms.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import top.roud.cms.common.result.ResultCode;
import top.roud.cms.exception.HttpException;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @description : TODO
 * @author: guangrui_hu
 * @date: 2023/4/28
 * @version:
 */

@Component
public class HttpUtil {

    @Autowired
    private RestTemplate restTemplate;


    public  <T> T get(String url, ParameterizedTypeReference<T> reference) {
        ResponseEntity<T> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, reference);
        return responseEntity.getBody();
    }

    public  <T> T get(String url,Class<T> clazz) {
        return restTemplate.getForObject(url, clazz);
    }

    public  <T> T exchange(String url, Object object, ParameterizedTypeReference<T> reference) {
        HttpEntity<MultiValueMap<String, String>> request = getMultiValueMapHttpEntity(object);
        ResponseEntity<T> responseEntity = restTemplate.exchange(url, HttpMethod.POST, request, reference);
        if (responseEntity.getStatusCodeValue() == 200){
            return responseEntity.getBody();
        }
        throw new HttpException(ResultCode.SYSTEM_ERROR);
    }

    public  String postExchange(String url, Object object)  {
        HttpEntity<MultiValueMap<String, String>> request = getMultiValueMapHttpEntity(object);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, request, String.class);
        if (responseEntity.getStatusCodeValue() == 200){
            return responseEntity.getBody();
        }
        throw new HttpException(ResultCode.SYSTEM_ERROR);    }

    public  String postExchange(String url, Map<String,Object> map)  {
        HttpHeaders headers = new HttpHeaders();
        // 请求头
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, Object> multiValueMap= new LinkedMultiValueMap<String, Object>();
        map.forEach(multiValueMap::add);
        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<MultiValueMap<String, Object>>(multiValueMap, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, request, String.class);
        if (responseEntity.getStatusCodeValue() == 200){
            return responseEntity.getBody();
        }
        throw new HttpException(ResultCode.SYSTEM_ERROR);    }

    public  <T> T postJson(String url, String param, Class<T> tClass)  {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<String> formEntity = new HttpEntity<>( param ,headers);
        ResponseEntity<T> responseEntity = restTemplate.postForEntity(url, formEntity, tClass);
        if (responseEntity.getStatusCodeValue() == 200){
            return responseEntity.getBody();
        }
        throw new HttpException(ResultCode.SYSTEM_ERROR);
    }

    public  <T> T postJsonExchange(String url, String param,  ParameterizedTypeReference<T> reference) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<String> formEntity = new HttpEntity<>( param ,headers);
        ResponseEntity<T> responseEntity = restTemplate.exchange(url, HttpMethod.POST,formEntity, reference);
        if (responseEntity.getStatusCodeValue() == 200){
            return responseEntity.getBody();
        }
        throw new HttpException(ResultCode.SYSTEM_ERROR);
    }



    private HttpEntity<MultiValueMap<String, String>> getMultiValueMapHttpEntity(Object object) {
        HttpHeaders headers = new HttpHeaders();
        // 请求头
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map = objectToMap(object);
        return new HttpEntity<MultiValueMap<String, String>>(map, headers);
    }

    /**
     * 将Object对象里面的属性和值转化成Map对象
     *
     * @param obj
     * @return
     * @throws IllegalAccessException
     */
    public  MultiValueMap<String, String> objectToMap(Object obj)  {
        if (obj == null) {
            return null;
        }
        MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
        List<Field> allField = getAllField(obj);
        for (Field field : allField) {
            field.setAccessible(true);
            String fieldName = field.getName();
            String fieldValue = "";
            if (field.getType()== String.class || field.getType() == Integer.class || field.getType() == int.class||field.getType().isEnum()){
                try {
                    fieldValue = field.get(obj)==null?"": field.get(obj).toString();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            map.add(fieldName, fieldValue);
        }
        return map;
    }

    private static List<Field> getAllField(Object obj){
        List<Field> fieldList = new ArrayList<Field>() ;
        Class<?> clazz = obj.getClass();
        while (clazz != null){
            fieldList.addAll(Arrays.asList(clazz.getDeclaredFields()));
            clazz = clazz.getSuperclass();
        }
        return fieldList;

    }

}
