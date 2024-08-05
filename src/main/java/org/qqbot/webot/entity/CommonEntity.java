package org.qqbot.webot.entity;

import lombok.Data;

@Data
public class CommonEntity {

    Long time;          //事件发生的unix时间戳
    Long selfId;       //收到事件的机器人的 QQ 号
    String postType;   //表示该上报的类型, 消息, 消息发送, 请求, 通知, 或元事件
}
