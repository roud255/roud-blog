package top.roud.cms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import top.roud.cms.entity.User;

/**
 * @ClassName: UserMapper
 * @Description:
 * @Author roud
 * @Date 2022/6/8
 * @Version 1.0
 */
public interface UserMapper extends BaseMapper<User> {
    User selectUserByPhonenumberAndPassword(String phonenumber, String password);
}
