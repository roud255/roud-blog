package top.roud.cms.service;

import top.roud.cms.common.result.Result;
import top.roud.cms.entity.UserInformation;

import javax.servlet.http.HttpServletRequest;

/**
 * @description : TODO
 * @author: guangrui_hu
 * @date: 2023/5/24
 * @version:
 */
public interface UserInformationService {
    public Integer save(UserInformation userInformation);
    public Integer updateByUserId(UserInformation userInformation);
    public UserInformation selectByUserId(Long id);
    public UserInformation selectById(Long id);
    public Integer deleteByUserId(Long id);
    public Result updateUserinfo(String info, HttpServletRequest request);
}
