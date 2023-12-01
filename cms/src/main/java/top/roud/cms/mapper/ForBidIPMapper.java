package top.roud.cms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import top.roud.cms.entity.ForbidIP;
@Repository
public interface ForBidIPMapper extends BaseMapper<ForbidIP> {
    ForbidIP selectForBidIPByIp(String ip);
}
