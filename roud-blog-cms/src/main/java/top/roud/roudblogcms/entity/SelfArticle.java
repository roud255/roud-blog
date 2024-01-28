package top.roud.roudblogcms.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

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
    private Long id;
    private Long articleId;
    private String validateCode;
}
