package org.qqbot.webot.entity;

import lombok.Data;
import org.qqbot.webot.entity.common.ModelMessage;

import java.util.List;

/**
 * @author shq
 * @date 2024/8/9
 * @describe 大语言模型
 */
@Data
public class BigModelEntity {

    /**
     * 所要调用的模型编码
     */
    public String model;

    /**
     * 调用语言模型时，将当前对话信息列表作为提示输入给模型， 按照 {"role": "user", "content": "你好"} 的json 数组形式进行传参；
     * 可能的消息类型包括 System message、User message、Assistant message 和 Tool message。
     */
    public List<ModelMessage> messages;
    /**
     * 由用户端传参，需保证唯一性；用于区分每次请求的唯一标识，用户端不传时平台会默认生成。
     */
    public String requestId;
    /**
     * do_sample 为 true 时启用采样策略，do_sample 为 false 时采样策略 temperature、top_p 将不生效。默认值为 true。
     */
    public boolean doSample;
    /**
     * 使用同步调用时，此参数应当设置为 fasle 或者省略。表示模型生成完所有内容后一次性返回所有内容。默认值为 false。
     */
    public boolean stream;
    /**
     * 采样温度，控制输出的随机性，必须为正数 取值范围是：[0.0, 1.0]，默认值为 0.95，值越大，
     * 会使输出更随机，更具创造性；值越小，输出会更加稳定或确定
     */
    public float temperature;
    /**
     * 用温度取样的另一种方法，称为核取样 取值范围是：[0.0, 1.0] ，默认值为 0.7
     */
    public float topP;
    /**
     * 模型输出最大 tokens，最大输出为4095，默认值为1024
     */
    public int maxToken;
    /**
     * 模型在遇到stop所制定的字符时将停止生成，目前仅支持单个停止词，格式为["stop_word1"]
     */
    public List<String> stop;
    /**
     * 可供模型调用的工具。默认开启web_search ，
     * 调用成功后作为参考信息提供给模型。注意：返回结果作为输入也会进行计量计费，每次调用大约会增加1000 tokens的消耗。
     */
    public List<Object> tools;
    /**
     * 用于控制模型是如何选择要调用的函数，仅当工具类型为function时补充。默认为auto，当前仅支持auto
     */
    public String toolChoice;
    /**
     * 终端用户的唯一ID，协助平台对终端用户的违规行为、生成违法及不良信息或其他滥用行为进行干预。ID长度要求：最少6个字符，最多128个字符
     */
    public String userId;

}
