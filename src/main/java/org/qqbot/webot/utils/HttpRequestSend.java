package org.qqbot.webot.utils;

import com.alibaba.fastjson2.JSONObject;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.qqbot.webot.config.JWTConfig;
import org.qqbot.webot.config.RestTemplateConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @author shq
 * @date 2024/8/7
 * @describe 统一返回请求
 */

@Slf4j
@Component
public class HttpRequestSend {

    private String token;

    @Value("${jwt.secret}")
    private String apiKey;

    @Autowired
    RestTemplate restTemplate;

    @Resource
    JWTConfig jwtConfig;

    public <T> T POST(String url, JSONObject jsonObject,Class<T> c){

        //如果没有token则生成token
        if(token == null || jwtConfig.isNeedUpdate(token)){
            this.token = jwtConfig.createToken();
        }

        //写入头
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Content-Type", "application/json");
        httpHeaders.set("Authorization", "Bearer " + apiKey);
        HttpEntity<JSONObject> httpEntity = new HttpEntity<>(jsonObject,httpHeaders);

        return restTemplate.postForEntity(url,httpEntity,c).getBody();
    }
}
