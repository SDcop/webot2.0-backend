package org.qqbot.webot.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.qqbot.webot.entity.common.Anonymous;
import org.qqbot.webot.entity.common.Chain;
import org.qqbot.webot.entity.common.User;

import java.util.List;

/**
 * @author shq
 * @date 2024/8/7
 * @describe 群消息类
 */

@EqualsAndHashCode(callSuper = true)
@Data
public class GroupMessageEntity extends CommonEntity {
    public String messageType;         //消息类型
    public String subType;             //表示消息的子类型
    public int messageId;              //消息 ID
    public long userId;                //发送者 QQ 号
    public List<Chain> message;        //一个消息链
    public String rawMessage;          //CQ 码格式的消息
    public int font;                   //字体
    public User sender;                //发送者信息
    public boolean group;              //是否是群消息
    public long groupId;               //群号
    public Anonymous anonymous;        //匿名信息, 如果不是匿名消息则为 null
}
