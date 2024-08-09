package org.qqbot.webot.entity;

import lombok.Data;
import org.qqbot.webot.entity.common.Choices;
import org.qqbot.webot.entity.common.Usage;
import org.qqbot.webot.entity.common.WebSearch;

import java.util.List;

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
    public List<Choices> choices;    //当前对话的模型输出内容
    public Usage usage;        //结束时返回本次模型调用的 tokens 数量统计。
    public List<WebSearch> webSearch;      //返回网页搜索的相关信息。
}



