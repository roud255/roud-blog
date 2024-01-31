package top.roud.roudblogcms.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.experimental.Accessors;
import top.roud.roudblogcms.common.config.CustomLongSerializer;

import java.io.Serializable;

/**
 * @description : TODO
 * @author: roud
 * @date: 2023/5/24
 * @version:
 */
@TableName("rb_user_extends")
@Data
@Accessors(chain = true)
public class UserInformation implements Serializable {
    private static final long serialVersionUID = 1L;
    @JsonSerialize(using = CustomLongSerializer.class)
    private Long id;
    private User user;
    @JsonSerialize(using = CustomLongSerializer.class)
    @TableField("img_id")
    private Long imgId;
    private int sex;
    private String motto;
    private String recentlyip;
}
