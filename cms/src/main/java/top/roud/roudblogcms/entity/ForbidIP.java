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

/**
 * @description : TODO
 * @author: roud
 * @date: 2022/9/28
 * @version:
 */
@TableName("rb_forbidip")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ForbidIP implements Serializable {
    private static final long serialVersionUID = 1L;
    @JsonSerialize(using = CustomLongSerializer.class)
    private Long id;
    private Long seconds;
    private String ip;
    private String reason;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date time;
}
