package org.qqbot.webot.entity;

import lombok.Data;

/**
 * @author shq
 * @date 2024/8/6
 * @describe 基础实体类
 */

@Data
public class CommonEntity {
    public long time;          //事件发生的unix时间戳
    public long selfId;       //收到事件的机器人的 QQ 号
    public String postType;   //表示该上报的类型, 消息, 消息发送, 请求, 通知, 或元事件
}
