package top.roud.cms.common.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description : TODO
 * @author: guangrui_hu
 * @date: 2023/3/2
 * @version:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HttpResult {
    private int status;
    private String body;
    private String error;
}
