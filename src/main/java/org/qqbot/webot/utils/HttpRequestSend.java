package org.qqbot.webot.utils;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

/**
 * @author shq
 * @date 2024/8/7
 * @describe 统一返回请求
 */
public class HttpRequestSend {

    String url = "127.0.0.1:3000";

    public <T> T POST(Class<T> c){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Content-Type", "application/json");
        HttpEntity<Class<T>> httpEntity = new HttpEntity<>(c,httpHeaders);
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForEntity(url,httpEntity,c).getBody();
    }
}
