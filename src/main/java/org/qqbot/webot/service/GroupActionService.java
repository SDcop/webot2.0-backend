package org.qqbot.webot.service;

import cn.hutool.json.JSONUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import jakarta.annotation.Resource;
import org.qqbot.webot.entity.*;
import org.qqbot.webot.entity.common.Chain;
import org.qqbot.webot.entity.common.ModelMessage;
import org.qqbot.webot.utils.HttpRequestSend;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CompletableFuture;

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

    @Value("${model.chatLong}")
    private String chatModelLong; //模型

    @Value("${model.rolePlay}")
    private String rolePlayModel;

    @Value("${bot.cat}")
    private String rolePersonality;

    @Value("${model.video}")
    private String videoModel; //视频模型

    @Value("${model.pic}")
    private String picModel; //图片模型

    private int replyLength = 0;

    @Resource
    HttpRequestSend httpRequestSend;

    //上下文处理
    private HashMap<Long, Queue<ModelMessage>> context;

    /**
     * 群消息类型数据处理
     * @param message 消息
     * @return 快速回复内容（未启用快速回复）
     */
    @Async("getAsyncExecutor")
    public CompletableFuture<JSONObject> replyGroupMessage(GroupMessageEntity message) {
        GroupReplyEntity groupReplyEntity = new GroupReplyEntity();
        long groupID = message.getGroupId();

        // 无需@回复的群
        boolean needAt = groupID != 772944742;

        // 如果是at了boot
        if(isAt(message.getMessage()) || !needAt) {
            String str = (String) message.getMessage().getLast().getData().get("text"); //获取文字内容
            str = str.trim(); //去除空格
            long sender = message.getSender().getUserId();

            log.info("bot ======> get message {} from {}",str,sender);

            //调用ai接口
            BigModelResponseEntity bigModelResponseEntity = modelResponse(sender,str);
            //构造字符串的返回内容
            StringBuilder builder = new StringBuilder();
            for(int i=0;i<bigModelResponseEntity.getChoices().size();i++){
                builder.append(bigModelResponseEntity.getChoices().get(i).getMessage().getContent());
            }

            //回复
            setReply(sender,builder.toString(),groupID,needAt);
        }
        return CompletableFuture.completedFuture((JSONObject) JSON.toJSON(groupReplyEntity));
    }


    /**
     * 群通知处理
     *
     * @param notice 通知
     */
    public void replyGroupNotice(JSONObject notice){
        String noticeType = notice.getString("type");
        switch (noticeType) {
            case "notify":  //群提示
                if(notice.getString("sub_type").equals("poke")){
                    //如果戳了戳bot
                    if(notice.getString("target_id").equals(botAccount)){
                        PokeNoticeEntity pokeNotice = JSONUtil.toBean(JSON.toJSONString(notice), PokeNoticeEntity.class);
                        long groupId = pokeNotice.getGroupId();
                        String reply = "喵~";
                        setReply(-1,reply,groupId,false);
                    }
                }
            case "group_ban": //ban 事件
        }
    }

    /**
     * 是否at了boot
     */
    private boolean isAt(List<Chain> chains){
        Chain message = chains.getFirst();
        return message.getType().equals("at") && Objects.equals(message.getData().get("qq"), botAccount);
    }

    /**
     * 调用智谱清言接口
     * @param str "用户的发言"
     */
    private BigModelResponseEntity modelResponse(long requestId,String str){

        // 构建请求体
        BigModelEntity bigModelEntity = new BigModelEntity();
        ArrayList<ModelMessage> list = new ArrayList<>();
        //消息角色消息
        ModelMessage systemMessage = new ModelMessage();
        systemMessage.setRole("system");
        systemMessage.setContent(rolePersonality);
        list.add(systemMessage);
        //用户消息
        ModelMessage userMessage = new ModelMessage();
        userMessage.setRole("user");
        userMessage.setContent(str);
        //上下文管理
        if(this.context == null){
            this.context = new HashMap<>();
        }
        if(context.containsKey(requestId)){
            Queue<ModelMessage> queue = context.get(requestId);
            queue.add(userMessage);
            if(queue.size()>=10){
                //如果上下文超出10条，则除去最前端的两条
                queue.poll();
                queue.poll();
            }
            list.addAll(queue);
        }else {
            Queue<ModelMessage> queue = new LinkedList<>();
            queue.add(userMessage);
            context.put(requestId,queue);
            list.add(userMessage);
        }

        bigModelEntity.setRequestId(String.valueOf(requestId));
        //设定模型
        bigModelEntity.setModel(chatModel);
        bigModelEntity.setMessages(list);

        //最大token
        bigModelEntity.setMaxToken(4095);

        //工具配置
        List<Object> toolObject = new ArrayList<>();

        JSONObject object = new JSONObject();   //type
        object.put("type","web_search");

        JSONObject webSearch = new JSONObject(); //启用网络搜索
        webSearch.put("enable",true);
        webSearch.put("search_result",true);
        object.put("web_search",webSearch);

        toolObject.add(object);

        bigModelEntity.setTools(toolObject);

        //http请求
        BigModelResponseEntity bigModelResponseEntity = httpRequestSend.POST(modelUrl,JSONObject.from(bigModelEntity), BigModelResponseEntity.class);

        //上下文管理,写入回复
        ModelMessage assistantModel =  new ModelMessage();
        assistantModel.setRole("assistant");
        assistantModel.setContent(bigModelResponseEntity.getChoices().getLast().getMessage().getContent());
        context.get(requestId).add(assistantModel);

        return bigModelResponseEntity;
    }

    /**
     * 构建回复消息
     */
    private void setReply(long sender,String str,long groupID,boolean needAt){

        List<Chain> chains;
        //回复消息
        if(needAt){
            chains = buildChain(sender,str,-1,null);
        }else {
            chains = buildChain(-1,str,-1,null);
        }

        //构建回复消息
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("group_id", groupID);
        jsonObject.put("message", chains);
        httpRequestSend.POST(qqUrl+"/send_group_msg",jsonObject,JSONObject.class);

        log.info("bot ======> send message {} from {}",str,sender);
    }

    /**
     * @param at   是否@回复 无则传入-1;
     * @param text 是否有文本回复  无则为null
     * @param face 是否有表情回复  无则为 -1;
     * @param file 是否有文件回复  无则为null;
     * @return 构建消息链
     */
    private List<Chain> buildChain(long at,String text,int face,String file){
        //添加总文本长度
        this.replyLength += text.length();
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
            jsonObject.put("text"," "+text);
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
        if(file != null){
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

