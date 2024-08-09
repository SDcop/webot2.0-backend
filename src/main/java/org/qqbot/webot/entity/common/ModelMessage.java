package org.qqbot.webot.entity.common;

import lombok.Data;

@Data
public class ModelMessage {

    public String role;     //消息的角色信息，此时应为system
    public String content;  //消息内容
}
