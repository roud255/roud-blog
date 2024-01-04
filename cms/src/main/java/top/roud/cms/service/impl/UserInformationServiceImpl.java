package top.roud.cms.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.roud.cms.common.result.Result;
import top.roud.cms.common.utils.AutoIdUtil;
import top.roud.cms.common.utils.JwtUtil;
import top.roud.cms.common.utils.RedisUtil;
import top.roud.cms.common.utils.TokenUtil;
import top.roud.cms.entity.User;
import top.roud.cms.entity.UserInformation;
import top.roud.cms.mapper.UserInformationMapper;
import top.roud.cms.service.UserInformationService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Optional;

import static top.roud.cms.common.result.ResultCode.COUNT_LIMIT;
import static top.roud.cms.common.result.ResultCode.EMAIL_HAS_EXISTED;
import static top.roud.cms.common.result.ResultCode.SYSTEM_ERROR;
import static top.roud.cms.common.result.ResultCode.TOKEN_INVALID;

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
    public Result updateUserinfo(String info, HttpServletRequest request) {
        try {
            String token = tokenUtil.getToken(request);
            if (StringUtils.isBlank(token) || !JwtUtil.checkSign(token)) {
                return Result.failure(TOKEN_INVALID);
            }
            Map<String, Object> tokeninfo = JwtUtil.getInfo(token);
            Long u_id = (Long) tokeninfo.get("id");
            Integer count = (Integer) redisUtil.get(u_id + "-updateinfo");
            Optional<Integer> op = Optional.ofNullable(count);
            if(op.isPresent()){
                return Result.failure(COUNT_LIMIT);
            }
            UserInformation userInformation = userInformationMapper.selectByUserId(u_id);
            JSONObject jsonObject = JSON.parseObject(info);
            String sex = jsonObject.getString("sex");
            String motto = jsonObject.getString("motto");
            int s = StringUtils.equals("女",sex)?1:0;
            userInformation.setSex(s);
            userInformation.setMotto(motto);
            userInformationMapper.updateByUserId(userInformation);
            redisUtil.set(u_id+"-updateinfo",1,60*60*24);
            return Result.success();
        }catch (Exception e){
            return Result.failure(SYSTEM_ERROR);
        }
    }
}
