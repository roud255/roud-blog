package top.roud.cms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import top.roud.cms.entity.UserInformation;

/**
 * @description : TODO
 * @author: roud
 * @date: 2023/5/24
 * @version:
 */
public interface UserInformationMapper extends BaseMapper<UserInformation> {
    UserInformation selectByUserId(Long id);
    Integer updateByUserId(UserInformation userInformation);
}
