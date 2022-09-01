package top.roud.cms.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @ClassName: Tag
 * @Description:
 * @Author roud
 * @Date 2022/9/1
 * @Version 1.0
 */
@TableName("sv_tag")
@Data
public class Tag {
    private Long id;
    private String tagname;
    private String description;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date addtime;
    private List<Article> articles;
}
