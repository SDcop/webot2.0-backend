package org.qqbot.webot.entity.common;

import lombok.Data;

/**
 * @author shq
 * @date 2024/8/7
 * @describe 心跳包数据结构
 */
@Data
public class Status {
    public boolean appInitialized;     //程序是否初始化完毕
    public boolean appEnabled;         //程序是否可用
    public boolean pluginsGood;        //插件正常(可能为 null)
    public boolean appGood;            //程序正常
    public boolean online;             //是否在线
    StatusStatistics stat;      //统计信息
}

/**
 * 心跳包统计信息
 */
@Data
class StatusStatistics{
    public long packetReceived;        //收包数
    public long packetSent;            //发包数
    public long packetLost;            //丢包数
    public long messageReceived;       //消息接收数
    public long messageSent;           //消息发送数
    public int disconnectTimes;        //连接断开次数
    public int lostTimes;              //连接丢失次数
    public long lastMessageTime;       //最后一次消息时间
}
