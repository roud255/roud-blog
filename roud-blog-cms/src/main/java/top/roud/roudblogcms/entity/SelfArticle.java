package top.roud.roudblogcms.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import top.roud.roudblogcms.common.config.CustomLongSerializer;

/**
 * @author roud
 * @version 1.0.0
 * @describe
 * @date 2023-11-06 0:56
 */
@TableName("rb_selfarticle")
@Data
public class SelfArticle {
    private static final long serialVersionUID = 1L;
    @JsonSerialize(using = CustomLongSerializer.class)
    private Long id;
    @JsonSerialize(using = CustomLongSerializer.class)
    private Long articleId;
    private String validateCode;
}
