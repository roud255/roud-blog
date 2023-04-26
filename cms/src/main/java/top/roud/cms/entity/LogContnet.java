package top.roud.cms.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @description : TODO
 * @author: guangrui_hu
 * @date: 2023/4/26
 * @version:
 */
@Component
@NoArgsConstructor
@AllArgsConstructor
@Data
public class LogContnet {
    private String content;
}
