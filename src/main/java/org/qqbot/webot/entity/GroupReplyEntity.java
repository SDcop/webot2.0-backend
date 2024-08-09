package org.qqbot.webot.entity;

import lombok.Data;
import org.qqbot.webot.entity.common.Chain;

import java.util.List;

/**
 * @author shq
 * @date 2024/8/5
 * @describe 群聊快速回复
 */

@Data
public class GroupReplyEntity {
    public List<Chain> reply;    //要回复的内容
    public boolean autoEscape;     //消息内容是否作为纯文本发送 ( 即不解析 CQ 码 ) , 只在 reply 字段是字符串时有效
    public boolean atSender;       //是否要在回复开头 at 发送者 ( 自动添加 ) , 发送者是匿名用户时无效
    public boolean delete;         //撤回该条消息
    public boolean kick;           //把发送者踢出群组 ( 需要登录号权限足够 ) , 不拒绝此人后续加群请求, 发送者是匿名用户时无效
    public boolean ban;            //禁言该消息发送者, 对匿名用户也有效
    public Number banDuration;     //若要执行禁言操作时的禁言时长
}
