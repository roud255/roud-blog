package top.roud.cms.service.impl;

import org.springframework.stereotype.Service;
import top.roud.cms.entity.UserInformation;
import top.roud.cms.mapper.UserInformationMapper;
import top.roud.cms.service.UserInformationService;

import javax.annotation.Resource;

/**
 * @description : TODO
 * @author: guangrui_hu
 * @date: 2023/5/24
 * @version:
 */
@Service
public class UserInformationServiceImpl implements UserInformationService {
    @Resource
    private UserInformationMapper userInformationMapper;

    @Override
    public Integer save(UserInformation userInformation) {
        return userInformationMapper.insert(userInformation);
    }

    @Override
    public Integer updateByUserId(UserInformation userInformation) {
        return userInformationMapper.updateByUserId(userInformation);
    }

    @Override
    public UserInformation selectByUserId(Long id) {
        return userInformationMapper.selectByUserId(id);
    }

    @Override
    public UserInformation selectById(Long id) {
        return userInformationMapper.selectById(id);
    }

    @Override
    public Integer deleteByUserId(Long id) {
        return null;
    }
}
