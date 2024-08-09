package org.qqbot.webot.entity.common;

import lombok.Data;

/**
 * @author shq
 * @date 2024/8/5
 * @describe 消息发送者消息
 */

@Data
public class User {
    public long userId;       //发送者 QQ 号
    public String nickname;    //昵称
    public String sex;         //性别, male 或 female 或 unknown
    public int age;            //年龄
    public String card;        //群名片／备注
    public String area;        //地区
    public String level;       //成员等级
    public String role;        //角色, owner 或 admin 或 member
    public String title;       //专属头衔
    public long groupId;      //临时群消息来源群号
}
