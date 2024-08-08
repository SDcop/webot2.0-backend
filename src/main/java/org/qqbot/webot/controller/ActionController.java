package org.qqbot.webot.controller;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import jakarta.annotation.Resource;
import org.qqbot.webot.service.GroupActionService;
import org.springframework.web.bind.annotation.*;

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
    public JSONObject action (@RequestBody JSONObject jsonObject) {
        //判断上报的消息类型
        String msgType = jsonObject.getString("post_type");

        switch (msgType){
            case "message":
                return groupActionService.replyGroupMessage(jsonObject);
        }
        return null;
    }
}
