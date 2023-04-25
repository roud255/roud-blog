package top.roud.cms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import top.roud.cms.entity.ForbidIP;

public interface ForBidIPMapper extends BaseMapper<ForbidIP> {
    ForbidIP selectForBidIPByIp(String ip);
}
