package top.roud.roudblogcms.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import top.roud.roudblogcms.common.config.CustomLongSerializer;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName: User
 * @Description:
 * @Author roud
 * @Date 2022/6/8
 * @Version 1.0
 */
@TableName("rb_user")
@Data
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    @JsonSerialize(using = CustomLongSerializer.class)
    private Long id;
    private String nickname;
    private String phonenumber;
    private String password;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date registertime;
    private int type;
    private String power;
}
