package top.roud.roudblogcms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import top.roud.roudblogcms.entity.Comment;

import java.util.List;

@Repository
public interface ArticleAndCommentMapper extends BaseMapper<Comment> {
    Integer insertComment(Comment comment);
    List<Comment> findAllCommentsByArticle(Long articleId);
    Long findArticleIdById(Long id);
    Integer delById(Long id);
    Integer delByArticleId(Long articleId);
    Integer selectCommentsnumByArticleId(Long id);

}

