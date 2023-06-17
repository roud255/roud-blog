package top.roud.cms.controller;

import org.apache.commons.lang3.StringUtils;
import org.bson.types.Binary;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.roud.cms.common.annotation.NoRepeatRequest;
import top.roud.cms.common.result.Result;
import top.roud.cms.common.utils.JwtUtil;
import top.roud.cms.common.utils.RedisUtil;
import top.roud.cms.entity.ImgFile;
import top.roud.cms.entity.UserInformation;
import top.roud.cms.service.UserInformationService;

import javax.annotation.Resource;
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
    @Resource
    private MongoTemplate mongoTemplate;
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private UserInformationService userInformationService;

    @PostMapping("/upload")
    public Result upload(@RequestParam(value = "file")MultipartFile file, HttpServletRequest request){
        String token = request.getHeader("token");
        if(StringUtils.isBlank(token) || !JwtUtil.checkSign(token)){
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
        Long id = System.currentTimeMillis();
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

    @PostMapping("/upload/editor")
    public Result uploadEditor(@RequestParam(value = "file")MultipartFile file, HttpServletRequest request){
        String token = request.getHeader("token");
        if(StringUtils.isBlank(token) || !JwtUtil.checkSign(token)){
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
        Long id = System.currentTimeMillis();
        try {
            ImgFile imgFile = new ImgFile().setId(String.valueOf(id)).setFilename(file.getOriginalFilename()).setContent(new Binary(file.getBytes())).setCreateTime(LocalDateTime.now()).setContentType(file.getContentType()).setSize(file.getSize());
            mongoTemplate.save(imgFile);
        } catch (IOException e) {
            return Result.failure(SYSTEM_ERROR);
        }
        return Result.success(String.valueOf(id));
    }

    @GetMapping(value = "/show/{id}",produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    public byte[] downloadImage(@PathVariable("id")String id) {
        byte[] data = null;
        ImgFile img = mongoTemplate.findById(id, ImgFile.class);
        if(img!=null){
            data = img.getContent().getData();
        }
        return data;
    }
}
