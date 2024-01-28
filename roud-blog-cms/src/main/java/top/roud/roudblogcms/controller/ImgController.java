package top.roud.roudblogcms.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import top.roud.roudblogcms.common.annotation.AccessIPRecord;
import top.roud.roudblogcms.common.result.Result;
import top.roud.roudblogcms.service.impl.ImageService;

import javax.servlet.http.HttpServletRequest;

/**
 * @description : TODO
 * @author: roud
 * @date: 2023/5/23
 * @version:
 */
@Api(tags = "图片相关接口")
@RestController
@RequestMapping("/img")
public class ImgController {
    @Autowired
    private ImageService imageService;
    @ApiOperation("图片上传")
    @AccessIPRecord
    @PostMapping("/upload")
    public Result upload(@RequestParam(value = "file")MultipartFile file, HttpServletRequest request){
        return imageService.upload(file, request);
    }

    @ApiOperation("图片上传-文章编辑器专用")
    @AccessIPRecord
    @PostMapping("/upload/editor")
    public Result uploadEditor(@RequestParam(value = "file")MultipartFile file, HttpServletRequest request){
        return imageService.uploadEditor(file, request);
    }

    @ApiOperation("获取图片及展示")
    @AccessIPRecord
    @GetMapping(value = "/show/{id}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    public byte[] downloadImage(@PathVariable("id")String id) {
        return imageService.downloadImage(id);
    }
}
