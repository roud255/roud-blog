package top.roud.cms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import top.roud.cms.entity.Comment;

import java.util.List;

@Repository
public interface ArticleAndCommentMapper extends BaseMapper<Comment> {
    Integer insertComment(Comment comment);
    List<Comment> findAllCommentsByArticle(Long article_id);
    Long findArticleIdById(Long id);
    Integer delById(Long id);
    Integer delByArticleId(Long article_id);
}
