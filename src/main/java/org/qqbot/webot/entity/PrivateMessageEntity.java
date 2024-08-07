package org.qqbot.webot.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.qqbot.webot.entity.common.Chain;
import org.qqbot.webot.entity.common.User;

import java.util.List;

/**
 * @author shq
 * @date 2024/8/5
 * @describe 消息类
 */

@EqualsAndHashCode(callSuper = true)
@Data
public class PrivateMessageEntity extends CommonEntity {
    String messageType;         //消息类型
    String subType;             //表示消息的子类型
    int messageId;              //消息 ID
    Long userId;                //发送者 QQ 号
    List<Chain> message;        //一个消息链
    String rawMessage;          //CQ 码格式的消息
    int font;                   //字体
    User sender;                //发送者信息
    Long targetId;              //接收者 QQ 号
    int tempSource;             //临时会话来源
}
