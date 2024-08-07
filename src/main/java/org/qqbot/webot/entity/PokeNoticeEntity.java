package org.qqbot.webot.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author shq
 * @date 2024/8/7
 * @describe 戳一戳
 */

@EqualsAndHashCode(callSuper = true)
@Data
public class PokeNoticeEntity extends CommonEntity{
    String noticeType;      //消息类型
    String subType;         //提示类型
    Long groupId;           //群号
    Long userId;            //发送者 QQ 号
    Long targetId;          //被戳者 QQ 号
}
