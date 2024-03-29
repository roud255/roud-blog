package top.roud.roudblogcms.service;


import top.roud.roudblogcms.common.result.Result;
import top.roud.roudblogcms.entity.ForbidIP;

public interface ForBidIPService {
    public Result save(ForbidIP forbidIP);
    public ForbidIP selectForBidIPByIp(String ip);
    public Result del(Long id);
    public Result findPages(Integer pageNum, Integer pageSize, String search);
    public Result update(ForbidIP forbidIP);
    public boolean exists(String ip);
}
