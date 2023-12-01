package top.roud.cms.controller;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.roud.cms.common.annotation.AccessIPRecord;
import top.roud.cms.common.result.Result;
import top.roud.cms.common.utils.*;
import top.roud.cms.entity.ImgFile;
import top.roud.cms.entity.UserInformation;
import top.roud.cms.service.UserInformationService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

import static top.roud.cms.common.result.ResultCode.*;

/**
 * @description : TODO
 * @author: roud
 * @date: 2023/5/23
 * @version:
 */
@RestController
@RequestMapping("/img")
public class ImgController {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private UserInformationService userInformationService;
    @Autowired
    private ThreeCacheUtil threeCacheUtil;
    @Autowired
    private TokenUtil tokenUtil;

    @AccessIPRecord
    @PostMapping("/upload")
    public Result upload(@RequestParam(value = "file")MultipartFile file, HttpServletRequest request){
        String token = tokenUtil.getToken(request);
        if(!JwtUtil.checkSign(token)){
            return Result.failure(TOKEN_INVALID);
        }
        Map<String, Object> info = JwtUtil.getInfo(token);
        UserInformation userInformation;
        Long u_id;
        try{
            u_id = (Long)info.get("id");
            userInformation = userInformationService.selectByUserId(u_id);
        }catch (Exception e){
            return Result.failure(SYSTEM_ERROR);
        }
        Integer count = (Integer) redisUtil.get(u_id + "-upload-img-count");
        Optional<Integer> op = Optional.ofNullable(count);
        if(op.isPresent()){
            return Result.failure(COUNT_LIMIT);
        }
        if(file.isEmpty()){
            return Result.failure(FILE_IS_EMPTY);
        }
        Long id = AutoIdUtil.getId();
        try {
            ImgFile imgFile = new ImgFile().setId(String.valueOf(id)).setFilename(file.getOriginalFilename()).setContent(new Binary(file.getBytes())).setCreateTime(LocalDateTime.now()).setContentType(file.getContentType()).setSize(file.getSize());
            mongoTemplate.save(imgFile);
            userInformation.setImg_id(id);
            userInformationService.updateByUserId(userInformation);
        } catch (IOException e) {
            return Result.failure(SYSTEM_ERROR);
        }
        redisUtil.set(u_id+"-upload-img-count",1,60*60*24);
        return Result.success(String.valueOf(id));
    }

    @AccessIPRecord
    @PostMapping("/upload/editor")
    public Result uploadEditor(@RequestParam(value = "file")MultipartFile file, HttpServletRequest request){
        String token = tokenUtil.getToken(request);
        if(!JwtUtil.checkSign(token)){
            return Result.failure(TOKEN_INVALID);
        }
        Map<String, Object> info = JwtUtil.getInfo(token);
        Integer type = (Integer) info.get("type");
        if(!Optional.ofNullable(type).isPresent() || type != 0){
            return Result.failure(PERMISSION_NO_ACCESS);
        }
        if(file.isEmpty()){
            return Result.failure(FILE_IS_EMPTY);
        }
        Long id = AutoIdUtil.getId();
        try {
            ImgFile imgFile = new ImgFile().setId(String.valueOf(id)).setFilename(file.getOriginalFilename()).setContent(new Binary(file.getBytes())).setCreateTime(LocalDateTime.now()).setContentType(file.getContentType()).setSize(file.getSize());
            mongoTemplate.save(imgFile);
        } catch (IOException e) {
            return Result.failure(SYSTEM_ERROR);
        }
        return Result.success(String.valueOf(id));
    }

    @AccessIPRecord
    @GetMapping(value = "/show/{id}",produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    public byte[] downloadImage(@PathVariable("id")String id) {
        byte[] data = null;
        String threeCacheKey = ConstUtil.CACHE_IMGFILE_PRE + id;
        String resStringbyThreeCache = threeCacheUtil.getByCache(threeCacheKey);
        if(StringUtils.isNotBlank(resStringbyThreeCache)){
            Binary binary = JSON.parseObject(resStringbyThreeCache, Binary.class);
            LoggerUtil.cacheLog.info("从缓存中获取图片|{}", id);
            return binary.getData();
        }
        ImgFile img = mongoTemplate.findById(id, ImgFile.class);
        if(img!=null){
            Binary content = img.getContent();
            threeCacheUtil.putToCache(threeCacheKey, content);
            data = content.getData();
        }
        return data;
    }
}
