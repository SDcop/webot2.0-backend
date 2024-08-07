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
    String messageType;         //消息类型
    String subType;             //表示消息的子类型
    int messageId;              //消息 ID
    Long userId;                //发送者 QQ 号
    List<Chain> message;        //一个消息链
    String rawMessage;          //CQ 码格式的消息
    int font;                   //字体
    User sender;                //发送者信息
    Long groupId;               //群号
    Anonymous anonymous;        //匿名信息, 如果不是匿名消息则为 null
}
