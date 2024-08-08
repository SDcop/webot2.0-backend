package org.qqbot.webot.service;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import org.qqbot.webot.entity.GroupMessageEntity;
import org.qqbot.webot.entity.GroupReplyEntity;
import org.springframework.stereotype.Service;

/**
 * @author shq
 * @date 2024/8/8
 * @describe 群事件处理，ps:偷懒了，没剥离接口（
 */

@Service
public class GroupActionService {

    public JSONObject replyGroupMessage(JSONObject message) {
        GroupReplyEntity groupReplyEntity = new GroupReplyEntity();
        GroupMessageEntity groupMessage = JSON.to(GroupMessageEntity.class,message);
        if(groupMessage.getMessage().getLast().getData().get("text").equals("你好")){
            groupReplyEntity.setReply(groupMessage.getMessage());
            groupReplyEntity.setAtSender(true);
        }
        return (JSONObject) JSON.toJSON(groupReplyEntity);
    }
}
