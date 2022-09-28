package top.roud.cms.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @description : TODO
 * @author: roud
 * @date: 2022/9/28
 * @version:
 */
@TableName("sv_forbidip")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ForbidIP {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String ip;
    private String reason;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date time;
}
