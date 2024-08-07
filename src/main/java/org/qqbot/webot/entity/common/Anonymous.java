package org.qqbot.webot.entity.common;

import lombok.Data;

/**
 * @author shq
 * @date 2024/8/7
 * @describe 匿名消息
 */
@Data
public class Anonymous {
    Long id;            //匿名用户 ID
    String name;        //匿名用户名称
    String flag;        //匿名用户 flag, 在调用禁言 API 时需要传入
}
