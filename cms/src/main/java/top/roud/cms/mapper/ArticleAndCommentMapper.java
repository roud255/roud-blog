package top.roud.cms.mapper;

import org.springframework.stereotype.Repository;
import top.roud.cms.entity.Comment;

@Repository
public interface ArticleAndCommentMapper {
    Integer insertComment(Comment comment);
    Comment findAllCommentsByArticle(Long article_id);
}
