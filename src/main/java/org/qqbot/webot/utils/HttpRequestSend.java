package org.qqbot.webot.utils;

import jakarta.annotation.Resource;
import org.qqbot.webot.config.JWTConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

/**
 * @author shq
 * @date 2024/8/7
 * @describe 统一返回请求
 */
public class HttpRequestSend {

    private String token;

    @Resource
    JWTConfig jwtConfig;

    public <T> T POST(String url,Class<T> c){

        //如果没有token则生成token
        if(token == null || jwtConfig.isNeedUpdate(token)){
            this.token = jwtConfig.createToken();
        }

        //写入头
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Content-Type", "application/json");
        httpHeaders.set("Authorization", "Bearer " + token);
        HttpEntity<Class<T>> httpEntity = new HttpEntity<>(c,httpHeaders);
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForEntity(url,httpEntity,c).getBody();
    }
}
