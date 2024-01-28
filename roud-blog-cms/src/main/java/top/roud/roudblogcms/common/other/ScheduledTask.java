package top.roud.roudblogcms.common.other;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import top.roud.roudblogcms.common.utils.RedisUtil;
import top.roud.roudblogcms.common.utils.ScheduledTaskCollection;
import top.roud.roudblogcms.service.ArticleAndTagService;

import javax.annotation.Resource;

/**
 * @description : TODO
 * @author: roud
 * @date: 2023/6/12
 * @version:
 */
@Component
public class ScheduledTask {
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private ArticleAndTagService articleAndTagService;

    @Async("myTaskAsyncPool")
    @Scheduled(cron = "0/5 * * * * ?")
    public void updateViewsnumAndCommentsnum(){
        if(ScheduledTaskCollection.viewCountSet.size()>0){
            ScheduledTaskCollection.viewCountSet.forEach(key->{
                if(redisUtil.hasKey(key)){
                    long count = Long.parseLong(redisUtil.get(key));
                    String[] arr = key.split("\\.");
                    String articleId = arr[arr.length - 1];
                    articleAndTagService.updateViewsnumAndCommentsnumByArticleId(Long.valueOf(articleId), (int) count);
                }
                ScheduledTaskCollection.viewCountSet.remove(key);
            });
        }
        if(ScheduledTaskCollection.commentCountSet.size()>0){
            ScheduledTaskCollection.commentCountSet.forEach(key->{
                if(redisUtil.hasKey(key)){
                    long count = Long.parseLong(redisUtil.get(key));
                    String[] arr = key.split("\\.");
                    String articleId = arr[arr.length - 1];
                    articleAndTagService.updateViewsnumAndCommentsnumByArticleId(Long.valueOf(articleId), 0, (int) count);
                }
                ScheduledTaskCollection.commentCountSet.remove(key);
            });
        }
    }
}
