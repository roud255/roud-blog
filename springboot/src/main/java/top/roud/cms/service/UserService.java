package top.roud.cms.service;

import top.roud.cms.common.Result;
import top.roud.cms.entity.User;

public interface UserService {
    public Result save(User user);
    public Result findPage(Integer pageNum, Integer pageSize, String search);
    public Result updateById(User user);
    public Result delById(Long id);
}
