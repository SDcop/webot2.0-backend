package org.qqbot.webot.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zhipu.oapi.ClientV4;
import com.zhipu.oapi.Constants;
import com.zhipu.oapi.service.v4.model.ChatCompletionRequest;
import com.zhipu.oapi.service.v4.model.ChatMessage;
import com.zhipu.oapi.service.v4.model.ChatMessageRole;
import com.zhipu.oapi.service.v4.model.ModelApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.List;

public class BigModel {

    private static final Logger log = LoggerFactory.getLogger(BigModel.class);
    @Value("${jwt.secret}")
    private String apiKey;

    public void createClient(){
        ClientV4 client = new ClientV4.Builder(apiKey).build();
    }
}
