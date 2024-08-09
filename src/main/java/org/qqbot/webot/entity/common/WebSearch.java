package org.qqbot.webot.entity.common;

import lombok.Data;

/**
 * @author shq
 * @date 2024/8/9
 * @describe 返回网页搜索的相关信息。
 */

@Data
public class WebSearch{
    public String icon; //来源网站的icon
    public String title;//搜索结果的标题
    public String link; //搜索结果的网页链接
    public String media;//搜索结果网页来源的名称
    public String content; //从搜索结果网页中引用的文本内容
}
