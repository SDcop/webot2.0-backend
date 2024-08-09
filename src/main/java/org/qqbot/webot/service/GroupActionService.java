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

    public JSONObject replyGroupMessage(GroupMessageEntity message) {
        GroupReplyEntity groupReplyEntity = new GroupReplyEntity();
        // 如果是at了boot
        if(isAt(message.getMessage())){
            String str = (String) message.getMessage().getLast().getData().get("text"); //获取文字内容
            long sender = message.getSender().getUserId();
            long groupID = message.getGroupId();

            str = str.trim(); //去除空格
            log.info("bot ======> get message {} from {}",str,sender);
            //调用ai接口
            BigModelResponseEntity bigModelResponseEntity = modelResponse(str);
            //构造字符串的返回内容
            StringBuilder builder = new StringBuilder();
            for(int i=0;i<bigModelResponseEntity.getChoices().size();i++){
                builder.append(bigModelResponseEntity.getChoices().get(i).getMessage().getContent());
            }

            //回复消息
            List<Chain> chains = buildChain(sender,builder.toString(),-1,null);
            groupReplyEntity.setReply(chains);

            log.info("bot ======> send message {} to {}",builder.toString(),sender);
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

        //http请求
        return httpRequestSend.POST(modelUrl,JSONObject.from(bigModelEntity), BigModelResponseEntity.class);
    }

    /**
     * @param at   是否@回复 无则传入-1;
     * @param text 是否有文本回复  无则为null
     * @param face 是否有表情回复  无则为 -1;
     * @param file 是否有文件回复  无则为null;
     * @return 消息链
     */
    public List<Chain> buildChain(long at,String text,int face,String file){
        List<Chain> list = new ArrayList<>();
        if(at !=-1){
            Chain chain = new Chain();
            chain.setType("at");
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("qq",at);
            chain.setData(jsonObject);
            list.add(chain);
        }
        if(text != null){
            Chain chain = new Chain();
            chain.setType("text");
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("text",text);
            chain.setData(jsonObject);
            list.add(chain);
        }
        if(face != -1){
            Chain chain = new Chain();
            chain.setType("face");
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("face",face);
            chain.setData(jsonObject);
            list.add(chain);
        }
        if(text != null){
            Chain chain = new Chain();
            chain.setType("image");
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("image",file);
            chain.setData(jsonObject);
            list.add(chain);
        }
        return list;
    }
}
