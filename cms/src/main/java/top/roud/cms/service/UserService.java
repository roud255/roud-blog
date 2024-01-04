package top.roud.cms.service;

import top.roud.cms.common.result.Result;
import top.roud.cms.entity.User;

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
