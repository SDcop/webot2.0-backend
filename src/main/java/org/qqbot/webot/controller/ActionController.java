package org.qqbot.webot.controller;

import cn.hutool.json.JSONObject;
import org.qqbot.webot.entity.CommonEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author shq
 * @date 2024/8/6
 * @describe 事件控制器
 */

@RestController
@RequestMapping("")
public class ActionController {

    @PostMapping("/oneBot")
    public void msg (@RequestBody JSONObject jsonObject) {
        
    }
}
