package top.roud.cms.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author roud
 * @version 1.0.0
 * @describe
 * @date 2023-11-07 0:26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleWithValidateCode extends Article{
    private static final long serialVersionUID = 1L;
    private String validateCode;
}
