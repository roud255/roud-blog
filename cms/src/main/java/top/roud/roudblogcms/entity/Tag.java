package top.roud.roudblogcms.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.roud.roudblogcms.common.config.CustomLongSerializer;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @ClassName: Tag
 * @Description:
 * @Author roud
 * @Date 2022/9/1
 * @Version 1.0
 */
@TableName("rb_tag")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tag implements Serializable {
    private static final long serialVersionUID = 1L;
    @JsonSerialize(using = CustomLongSerializer.class)
    private Long id;
    private String tagname;
    private String description;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date addtime;
    private List<Article> articles;
}
