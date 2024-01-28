package top.roud.roudblogcms.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

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
    private Long id;
    private User user;
    @TableField("img_id")
    private Long imgId;
    private int sex;
    private String motto;
    private String recentlyip;
}
