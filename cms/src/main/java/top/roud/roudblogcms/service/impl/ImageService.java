package top.roud.roudblogcms.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import top.roud.roudblogcms.common.config.RandomCodeUtil;
import top.roud.roudblogcms.common.result.Result;
import top.roud.roudblogcms.common.utils.*;
import top.roud.roudblogcms.entity.ImgFile;
import top.roud.roudblogcms.entity.UserInformation;
import top.roud.roudblogcms.service.UserInformationService;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static top.roud.roudblogcms.common.result.ResultCode.*;
import static top.roud.roudblogcms.common.utils.CaptchaUtil.doDraw;
import static top.roud.roudblogcms.common.utils.ConstUtil.CAPTCHA_IMAGE_CONTACT;
import static top.roud.roudblogcms.common.utils.ConstUtil.USER_UPLOADIMG_FLAG_CACHE;

/**
 * @author roud
 * @version 1.0.0
 * @describe
 * @date 2024-01-26 21:26
 */
@CacheConfig(cacheNames = "caffeineCache")
@Service
public class ImageService {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private UserInformationService userInformationService;
    @Autowired
    private TokenUtil tokenUtil;
    @Autowired
    private IPUtil ipUtil;

    public Result upload(@RequestParam(value = "file") MultipartFile file, HttpServletRequest request){
        String token = request.getParameter("token");
        Map<String, Object> info;
        if(StringUtils.isBlank(token)){
            info = tokenUtil.getUserInfoByRequest(request);
        }else {
            info = tokenUtil.getUserInfoByOutToken(token);
        }
        UserInformation userInformation;
        Long userId;
        try{
            userId = (Long)info.get("id");
            userInformation = userInformationService.selectByUserId(userId);
        }catch (Exception e){
            return Result.failure(SYSTEM_ERROR);
        }
        String key = USER_UPLOADIMG_FLAG_CACHE + userId;
        if(redisUtil.hasKey(key)){
            return Result.failure(COUNT_LIMIT);
        }
        if(file.isEmpty()){
            return Result.failure(FILE_IS_EMPTY);
        }
        Long id = AutoIdUtil.getId();
        try {
            ImgFile imgFile = new ImgFile().setId(String.valueOf(id)).setFilename(file.getOriginalFilename()).setContent(new Binary(file.getBytes())).setCreateTime(LocalDateTime.now()).setContentType(file.getContentType()).setSize(file.getSize());
            mongoTemplate.save(imgFile);
            userInformation.setImgId(id);
            userInformationService.updateByUserId(userInformation);
        } catch (IOException e) {
            return Result.failure(SYSTEM_ERROR);
        }
        redisUtil.set(key,String.valueOf(1), 1, TimeUnit.DAYS);
        return Result.success(String.valueOf(id));
    }


    public Result uploadEditor(@RequestParam(value = "file")MultipartFile file, HttpServletRequest request){
        Map<String, Object> info = tokenUtil.getUserInfoByRequest(request);
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

    @Cacheable(key = "'roudblog.img.'+#id")
    public byte[] downloadImage(@PathVariable("id")String id) {
        byte[] data = null;
        ImgFile img = mongoTemplate.findById(id, ImgFile.class);
        if(img!=null){
            Binary content = img.getContent();
            data = content.getData();
        }
        return data;
    }

    public Result getCaptchaImage(HttpServletRequest request, HttpServletResponse response, String flagCode){
        try{
            int imgWidth = 108;
            int imgHeight = 36;
            int interferenceLineCount = 6;

            response.setContentType("image/jpg");//设置相应类型,告诉浏览器输出的内容为图片
            response.setHeader("Pragma", "No-cache");//设置响应头信息，告诉浏览器不要缓存此内容
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expire", new Date().getTime());
            String str = RandomCodeUtil.getRandomCodeCaptchaComstomized(4);
            String key;
            if(StringUtils.isBlank(flagCode)){
                String ip = ipUtil.getIpAddr(request);
                key = CAPTCHA_IMAGE_CONTACT + ip;
            }else {
                key = CAPTCHA_IMAGE_CONTACT + flagCode;
            }
            redisUtil.set(key, str,60, TimeUnit.SECONDS);
            BufferedImage img = doDraw(str,imgWidth, imgHeight, interferenceLineCount);
            ServletOutputStream out = response.getOutputStream();
            ImageIO.write(img, "PNG", out);
            out.flush();
            out.close();
            return null;
        }catch (Exception e){
            return Result.failure(e.getMessage());
        }
    }
}
