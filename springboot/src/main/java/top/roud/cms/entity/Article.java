package top.roud.cms.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @ClassName: Article
 * @Description:
 * @Author roud
 * @Date 2022/9/1
 * @Version 1.0
 */
@TableName("sv_article")
@Data
public class Article {
    private Long id;
    private String title;
    private String author;
    private String description;
    private String cover;
    private String postbody;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date publishtime;
    private List<Tag> tags;


}
