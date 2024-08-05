package org.qqbot.webot.controller;

import org.qqbot.webot.entity.CommonEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/oneBot")
public class ActionController {

    @PostMapping("/msg")
    public CommonEntity msg (@RequestBody CommonEntity commonEntity) {
        System.out.println(commonEntity.getSelfId());
        return commonEntity;
    }
}
