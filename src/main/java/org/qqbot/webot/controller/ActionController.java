package org.qqbot.webot.controller;

import org.qqbot.webot.entity.CommonEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author shq
 * @date 2024/8/6
 * @describe 事件控制器
 */

@RestController
@RequestMapping("/oneBot")
public class ActionController {

    @PostMapping("/msg")
    public CommonEntity msg (@RequestBody CommonEntity commonEntity) {
        System.out.println(commonEntity.getSelfId());
        return commonEntity;
    }
}
