package org.qqbot.webot.entity.common;

import lombok.Data;

/**
 * @author shq
 * @date 2024/8/7
 * @describe 心跳包数据结构
 */
@Data
public class Status {
    boolean appInitialized;     //程序是否初始化完毕
    boolean appEnabled;         //程序是否可用
    boolean pluginsGood;        //插件正常(可能为 null)
    boolean appGood;            //程序正常
    boolean online;             //是否在线
    StatusStatistics stat;      //统计信息
}

/**
 * 心跳包统计信息
 */
class StatusStatistics{
    long packetReceived;        //收包数
    long packetSent;            //发包数
    long packetLost;            //丢包数
    long messageReceived;       //消息接收数
    long messageSent;           //消息发送数
    int disconnectTimes;        //连接断开次数
    int lostTimes;              //连接丢失次数
    long lastMessageTime;       //最后一次消息时间
}
