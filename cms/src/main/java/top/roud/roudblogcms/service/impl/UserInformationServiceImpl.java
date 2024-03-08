package top.roud.roudblogcms.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.roud.roudblogcms.common.result.Result;
import top.roud.roudblogcms.common.utils.RedisUtil;
import top.roud.roudblogcms.common.utils.TokenUtil;
import top.roud.roudblogcms.entity.UserInformation;
import top.roud.roudblogcms.mapper.UserInformationMapper;
import top.roud.roudblogcms.service.UserInformationService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static top.roud.roudblogcms.common.result.ResultCode.COUNT_LIMIT;
import static top.roud.roudblogcms.common.result.ResultCode.SYSTEM_ERROR;
import static top.roud.roudblogcms.common.utils.ConstUtil.USERINFO_FLAG_CACHE;

/**
 * @description : TODO
 * @author: guangrui_hu
 * @date: 2023/5/24
 * @version:
 */
@Transactional
@Service
public class UserInformationServiceImpl implements UserInformationService {
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private TokenUtil tokenUtil;
    @Autowired
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

    @Override
    public Result updateUserInfo(String info, HttpServletRequest request) {
        try {
            Map<String, Object> tokeninfo = tokenUtil.getUserInfoByRequest(request);
            Long userId = (Long) tokeninfo.get("id");
            String key = USERINFO_FLAG_CACHE + userId;
            if(redisUtil.hasKey(key)){
                return Result.failure(COUNT_LIMIT);
            }
            UserInformation userInformation = userInformationMapper.selectByUserId(userId);
            JSONObject jsonObject = JSON.parseObject(info);
            String sex = jsonObject.getString("sex");
            String motto = jsonObject.getString("motto");
            int sexInt = StringUtils.equals("女",sex) ? 1:0;
            userInformation.setSex(sexInt);
            userInformation.setMotto(motto);
            userInformationMapper.updateByUserId(userInformation);
            redisUtil.set(key,String.valueOf(1), 1, TimeUnit.DAYS);
            return Result.success();
        }catch (Exception e){
            return Result.failure(SYSTEM_ERROR);
        }
    }
}
