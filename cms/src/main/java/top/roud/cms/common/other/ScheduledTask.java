package top.roud.cms.common.other;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import top.roud.cms.common.utils.CacheUtil;
import top.roud.cms.common.utils.ConstUtil;
import top.roud.cms.common.utils.RedisUtil;
import top.roud.cms.common.utils.StaticVarUtil;
import top.roud.cms.mapper.ArticleAndTagMapper;

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
    private ArticleAndTagMapper articleAndTagMapper;
    @Async("myTaskAsyncPool")
    @Scheduled(cron = "0 0/1 * * * ?")
    public void updateVCFlag(){
        if(StaticVarUtil.updateViewsnumAndCommentsnumFlag.get()){
            StaticVarUtil.updateViewsnumAndCommentsnumFlag.set(false);
        }
    }

    @Async("myTaskAsyncPool")
    @Scheduled(cron = "0/5 * * * * ?")
    public void updateViewsnumAndCommentsnum(){
        if(StaticVarUtil.updateViewsnumAndCommentsnumFlag.get()){
            CacheUtil.booleanConMap.entrySet().forEach(o->{
                if(o.getValue()){
                    if(o.getKey().contains(ConstUtil.ARTICLE_COMMENTS_COUNT_ISNEED_UPDATE_KEY)){
                        Long realId = Long.valueOf(ConstUtil.getRealId(o.getKey(), ConstUtil.ARTICLE_COMMENTS_COUNT_ISNEED_UPDATE_KEY));
                        articleAndTagMapper.updateCommentsnumByArticleId(realId, CacheUtil.intConMap.get(ConstUtil.REDIS_COMMENTS_COUNT_KEY+realId));
                    }else if(o.getValue() && o.getKey().contains(ConstUtil.ARTICLE_VIEWSUM_ISNEED_UPDATE_KEY)){
                        Long realId = Long.valueOf(ConstUtil.getRealId(o.getKey(), ConstUtil.ARTICLE_VIEWSUM_ISNEED_UPDATE_KEY));
                        articleAndTagMapper.updateViewsnumByArticleId(realId, (Integer) redisUtil.get(ConstUtil.REDIS_ARTICLE_VIEWSNUM_KEY+realId));
                    }
                    o.setValue(false);
                }
            });
        }
    }
}
