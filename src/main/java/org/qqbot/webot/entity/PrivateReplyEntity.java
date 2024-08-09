package org.qqbot.webot.entity;

import lombok.Data;
import org.qqbot.webot.entity.common.Chain;

import java.util.List;

/**
 * @author shq
 * @date 2024/8/7
 * @describe 私聊快速回复
 */

@Data
public class PrivateReplyEntity {
    public List<Chain> message;        //要回复的内容
    public boolean autoEscape;         //消息内容是否作为纯文本发送 ( 即不解析 CQ 码 ) , 只在 reply 字段是字符串时有效
}
