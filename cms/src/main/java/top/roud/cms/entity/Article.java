package top.roud.cms.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @ClassName: Article
 * @Description:
 * @Author roud
 * @Date 2022/9/1
 * @Version 1.0
 */
@TableName("rb_article")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Article implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String title;
    private String author;
    private String description;
    private String cover;
    private String postbody;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date publishtime;
    @TableField(exist = false)
    private List<Tag> tags;
    @TableField(exist = false)
    private List<Comment> comments;
    private int viewsnum;
    private int commentsnum;

}
