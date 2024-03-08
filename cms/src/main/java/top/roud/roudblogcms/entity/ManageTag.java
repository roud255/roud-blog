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
 * @description:
 * @author: guangrui_hu
 * @date: 2024/1/30
 * @version: 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ManageTag implements Serializable {
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
