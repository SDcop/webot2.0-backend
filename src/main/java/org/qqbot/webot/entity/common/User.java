package org.qqbot.webot.entity.common;

import lombok.Data;

/**
 * @author shq
 * @date 2024/8/5
 * @describe 消息发送者消息
 */

@Data
public class User {
    Long userId;       //发送者 QQ 号
    String nickname;    //昵称
    String sex;         //性别, male 或 female 或 unknown
    int age;            //年龄
    String card;        //群名片／备注
    String area;        //地区
    String level;       //成员等级
    String role;        //角色, owner 或 admin 或 member
    String title;       //专属头衔
    Long groupId;      //临时群消息来源群号
}
