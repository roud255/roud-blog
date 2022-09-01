package top.roud.cms.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @ClassName: ArticleAndTag
 * @Description:
 * @Author roud
 * @Date 2022/9/1
 * @Version 1.0
 */
@TableName("sv_article_tag")
@Data
public class ArticleAndTag {
    private Long id;
    private Article article;
    private Tag tag;
}
