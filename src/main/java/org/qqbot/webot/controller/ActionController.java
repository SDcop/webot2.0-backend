package org.qqbot.webot.controller;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import jakarta.annotation.Resource;
import org.qqbot.webot.entity.GroupMessageEntity;
import org.qqbot.webot.service.GroupActionService;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

/**
 * @author shq
 * @date 2024/8/6
 * @describe 事件控制器
 */

@RestController
@RequestMapping("")
public class ActionController {

    @Resource
    GroupActionService groupActionService;

    @PostMapping("/oneBot")
    public CompletableFuture<JSONObject> action (@RequestBody JSONObject message) {
        //判断上报的消息类型
        String msgType = message.getString("post_type");

        switch (msgType){
            case "message":
                GroupMessageEntity groupMessage = JSON.to(GroupMessageEntity.class,message);
                return groupActionService.replyGroupMessage(groupMessage);
            case "notice":
                return groupActionService.replyGroupNotice(message);
        }
        return null;
    }
}
