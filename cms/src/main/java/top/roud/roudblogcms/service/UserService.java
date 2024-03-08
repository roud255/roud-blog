package top.roud.roudblogcms.service;
import top.roud.roudblogcms.common.result.Result;
import top.roud.roudblogcms.entity.User;

import javax.servlet.http.HttpServletRequest;

public interface UserService {
    public Result save(User user);
    public Result findPage(Integer pageNum, Integer pageSize, String search);
    public Result updateById(User user);
    public Result delById(Long id);
    public User findUserByPhonenumberAndPassword(String phonenumber, String password);
    public User findUserByPhonenumber(String phonenumber);
    public Result saveUser(User user, HttpServletRequest request);
}
