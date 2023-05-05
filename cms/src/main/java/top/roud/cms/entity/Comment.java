package top.roud.cms.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @ClassName: Comment
 * @Description:
 * @Author roud
 * @Date 2022/9/7
 * @Version 1.0
 */

//{
//        name:'Taylor Swift',
//        id:19891221,
//        headImg:'https://ae01.alicdn.com/kf/H94c78935ffa64e7e977544d19ecebf06L.jpg',
//        comment:'我发行了我的新专辑Lover',
//        time:'2019年9月16日 18:43',
//        commentNum:1,
//        like:5,
//        inputShow:false,
//        reply:[
//        {
//        from:'Lana Del Rey',
//        fromId:19870621,
//        fromHeadImg:'https://ae01.alicdn.com/kf/Hd60a3f7c06fd47ae85624badd32ce54dv.jpg',
//        to:'Taylor Swift',
//        toId:19891221,
//        comment:'新专辑和speak now 一样棒！',
//        time:'2019年9月16日 18:43',
//        commentNum:25,
//        like:5,
//        inputShow:false
//        }
//        ]
//        }

@TableName("rb_comment")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String content;
    private String from;
    private String to;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date time;
    private Long parent_id;
    private List<Comment> child_comments;
    private Long article_id;
}
