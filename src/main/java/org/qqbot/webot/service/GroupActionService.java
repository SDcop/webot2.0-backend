package org.qqbot.webot.service;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import jakarta.annotation.Resource;
import org.qqbot.webot.entity.BigModelEntity;
import org.qqbot.webot.entity.BigModelResponseEntity;
import org.qqbot.webot.entity.GroupMessageEntity;
import org.qqbot.webot.entity.GroupReplyEntity;
import org.qqbot.webot.entity.common.Chain;
import org.qqbot.webot.entity.common.ModelMessage;
import org.qqbot.webot.utils.HttpRequestSend;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * @author shq
 * @date 2024/8/8
 * @describe 群事件处理，ps:偷懒了，没剥离接口（
 */

@Service
public class GroupActionService {

    Log log = LogFactory.get();

    @Value("${account.qq}")
    private String botAccount;//qq账号

    @Value("${server.qqUrl}")
    private String qqUrl;    //qq请求地址

    @Value("${server.modelUrl}")
    private String modelUrl; //模型请求地址

    @Value("${model.chat}")
    private String chatModel; //模型

    @Value("${model.video}")
    private String videoModel; //视频模型

    @Value("${model.pic}")
    private String picModel; //图片模型

    @Resource
    HttpRequestSend httpRequestSend;

    public JSONObject replyGroupMessage(JSONObject message) {
        GroupReplyEntity groupReplyEntity = new GroupReplyEntity();
        GroupMessageEntity groupMessage = JSON.to(GroupMessageEntity.class,message);
        // 如果是at了boot
        if(isAt(groupMessage.getMessage())){
            String str = (String) groupMessage.getMessage().getLast().getData().get("text");
            log.info("bot ======> get message {}",str);
            str = str.trim();
            if(str.equals("你好")){

            }
        }
        return (JSONObject) JSON.toJSON(groupReplyEntity);
    }

    /**
     * 是否at了boot
     */
    public boolean isAt(List<Chain> chains){
        Chain message = chains.getFirst();
        return message.getType().equals("at") && Objects.equals(message.getData().get("qq"), botAccount);
    }

    /**
     * 调用智谱清言接口
     * @param str "用户的发言"
     */
    public BigModelResponseEntity modelResponse(String str){

        // 构建请求体
        BigModelEntity bigModelEntity = new BigModelEntity();
        // message
        ArrayList<ModelMessage> list = new ArrayList<>();
        ModelMessage modelMessage = new ModelMessage();
        modelMessage.setRole("user");
        modelMessage.setContent(str);
        list.add(modelMessage);

        bigModelEntity.setModel(chatModel);
        bigModelEntity.setMessages(list);



        httpRequestSend.POST(modelUrl, BigModelResponseEntity.class);

        return null;
    }
}
