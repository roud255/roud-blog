package top.roud.roudblogcms.entity;

/**
 * @description : TODO
 * @author: roud
 * @date: 2023/5/23
 * @version:
 */

import lombok.Data;
import lombok.experimental.Accessors;
import org.bson.types.Binary;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class ImgFile {
    //id类型要为字符串
    private String id;
    private String filename;
    private LocalDateTime createTime;
    private Binary content;
    private String contentType;
    private long size;
}
