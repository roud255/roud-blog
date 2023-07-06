package top.roud.cms.common.other;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import top.roud.cms.common.utils.CacheUtil;
import top.roud.cms.common.utils.ConstUtil;
import top.roud.cms.common.utils.RedisUtil;
import top.roud.cms.common.utils.StaticVarUtil;
import top.roud.cms.mapper.ArticleAndTagMapper;

import javax.annotation.Resource;
import java.util.Optional;
import java.util.Set;

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
    private ArticleAndTagMapper articleAndTagMapper;
    @Scheduled(cron = "0 0/1 * * * ?")
    public void updateVCFlag(){
        if(StaticVarUtil.updateViewsnumAndCommentsnumFlag.get()){
            StaticVarUtil.updateViewsnumAndCommentsnumFlag.set(false);
        }
    }

    @Scheduled(cron = "0/5 * * * * ?")
    public void updateViewsnumAndCommentsnum(){
        if(StaticVarUtil.updateViewsnumAndCommentsnumFlag.get()){
            Set<String> keys = redisUtil.getKeysForPrefix(ConstUtil.REDIS_ARTICLE_VIEWSNUM_KEY);
            keys.forEach(id-> {
                if(Optional.ofNullable(redisUtil.get(id)).isPresent()){
                    articleAndTagMapper.updateViewsnumAndCommentsnumByArticleId(Long.valueOf(id.split(ConstUtil.REDIS_ARTICLE_VIEWSNUM_KEY)[1]),
                            (Integer) redisUtil.get(id), CacheUtil.intConMap.get(ConstUtil.REDIS_COMMENTS_COUNT_KEY+id.split(ConstUtil.REDIS_ARTICLE_VIEWSNUM_KEY)[1]));
                }
            });
        }
    }
}
