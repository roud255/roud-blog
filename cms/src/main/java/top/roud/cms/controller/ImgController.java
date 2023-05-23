package top.roud.cms.controller;

import org.bson.types.Binary;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.roud.cms.common.result.Result;
import top.roud.cms.entity.ImgFile;

import javax.annotation.Resource;

import java.io.IOException;
import java.time.LocalDateTime;

import static top.roud.cms.common.result.ResultCode.FILE_IS_EMPTY;
import static top.roud.cms.common.result.ResultCode.SYSTEM_ERROR;

/**
 * @description : TODO
 * @author: guangrui_hu
 * @date: 2023/5/23
 * @version:
 */
@RestController
@RequestMapping("/img")
public class ImgController {
    @Resource
    private MongoTemplate mongoTemplate;
    @PostMapping("/upload")
    public Result upload(@RequestParam(value = "file")MultipartFile file){
        if(file.isEmpty()){
            return Result.failure(FILE_IS_EMPTY);
        }
        String id = String.valueOf(System.currentTimeMillis());
        try {
            ImgFile imgFile = new ImgFile().setId(id).setFilename(file.getOriginalFilename()).setContent(new Binary(file.getBytes())).setCreateTime(LocalDateTime.now()).setContentType(file.getContentType()).setSize(file.getSize());
            mongoTemplate.save(imgFile);
        } catch (IOException e) {
            return Result.failure(SYSTEM_ERROR);
        }
        String uri = "/img/show/"+id;
        return Result.success(uri);
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
