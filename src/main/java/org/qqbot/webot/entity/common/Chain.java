package org.qqbot.webot.entity.common;

import cn.hutool.json.JSONObject;
import lombok.Data;

/**
 * @author shq
 * @date 2024/8/5
 * @describe 消息链实体
 */

@Data
public class Chain {
    public String type;        //消息类型
    public JSONObject data;    //消息内容
}
