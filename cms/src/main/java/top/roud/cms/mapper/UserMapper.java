package top.roud.cms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import top.roud.cms.entity.User;

/**
 * @ClassName: UserMapper
 * @Description:
 * @Author roud
 * @Date 2022/6/8
 * @Version 1.0
 */
@Repository
public interface UserMapper extends BaseMapper<User> {
    User selectUserByPhonenumberAndPassword(String phonenumber, String password);
    User selectUserByPhonenumber(String phonenumber);
}
