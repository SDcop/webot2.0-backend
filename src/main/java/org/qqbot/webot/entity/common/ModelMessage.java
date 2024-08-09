package org.qqbot.webot.entity.common;

import lombok.Data;

/**
 * @author shq
 * @date 2024/8/9
 * @describe qq消息模板
 */

@Data
public class ModelMessage {

    public String role;     //消息的角色信息，此时应为system
    public String content;  //消息内容
}
