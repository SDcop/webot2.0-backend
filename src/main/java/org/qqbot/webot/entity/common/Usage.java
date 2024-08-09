package org.qqbot.webot.entity.common;

import lombok.Data;

/**
 * @author shq
 * @date 2024/8/9
 * @describe 结束时返回本次模型调用的 tokens 数量统计。
 */

@Data
public class Usage {
    public int promptTokens;    //用户输入的 tokens 数量
    public int completionTokens;    //模型输出的 tokens 数量
    public int totalTokens;     //总 tokens 数量
}