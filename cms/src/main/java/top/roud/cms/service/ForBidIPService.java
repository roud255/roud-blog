package top.roud.cms.service;

import top.roud.cms.common.result.Result;
import top.roud.cms.entity.ForbidIP;

public interface ForBidIPService {
    public Result save(ForbidIP forbidIP);
    public ForbidIP selectForBidIPByIp(String ip);
    public Result del(Long id);
    public Result findPages(Integer pageNum, Integer pageSize, String search);
    public Result update(ForbidIP forbidIP);
}
