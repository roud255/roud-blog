package top.roud.cms.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @ClassName: EmailUser
 * @Description:
 * @Author roud
 * @Date 2022/6/14
 * @Version 1.0
 */
@Data
public class EmailUser {
    private String email;
    private String userVertifyCode;
}
