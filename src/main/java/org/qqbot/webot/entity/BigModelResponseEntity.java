package org.qqbot.webot.entity;

import lombok.Data;
import org.qqbot.webot.entity.common.ModelMessage;

/**
 * @author shq
 * @date 2024/8/9
 * @describe 模型同步调用响应内容
 */

@Data
public class BigModelResponseEntity {

    public String id;   //任务ID
    public long create; //请求创建时间，是以秒为单位的 Unix 时间戳
    public String model;//模型名称
    Choices choices;    //当前对话的模型输出内容
    Usage usage;        //结束时返回本次模型调用的 tokens 数量统计。
    Web webSearch;      //返回网页搜索的相关信息。
}

class Choices{
    public int index; //结果下标
    public String finishReason;     //模型推理终止的原因。stop代表推理自然结束或触发停止词。tool_calls 代表模型命中函数。length代表到达 tokens 长度上限。sensitive 代表模型推理内容被安全审核接口拦截。请注意，针对此类内容，请用户自行判断并决定是否撤回已公开的内容。
    public ModelMessage message;    //模型返回的文本信息
}

class Usage {
    public int promptTokens;    //用户输入的 tokens 数量
    public int completionTokens;    //模型输出的 tokens 数量
    public int totalTokens;     //总 tokens 数量
}

class Web{
    public String icon; //来源网站的icon
    public String title;//搜索结果的标题
    public String link; //搜索结果的网页链接
    public String media;//搜索结果网页来源的名称
    public String content; //从搜索结果网页中引用的文本内容
}

