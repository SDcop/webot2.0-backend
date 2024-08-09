package org.qqbot.webot.entity.common;

import lombok.Data;

/**
 * @author shq
 * @date 2024/8/9
 * @describe 当前对话的模型输出内容
 */

@Data
public class Choices{
    public int index; //结果下标
    public String finishReason;     //模型推理终止的原因。stop代表推理自然结束或触发停止词。tool_calls 代表模型命中函数。length代表到达 tokens 长度上限。sensitive 代表模型推理内容被安全审核接口拦截。请注意，针对此类内容，请用户自行判断并决定是否撤回已公开的内容。
    public ModelMessage message;    //模型返回的文本信息
}

