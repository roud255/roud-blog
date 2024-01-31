package top.roud.roudblogcms.controller.manage;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.roud.roudblogcms.common.annotation.AccessIPRecord;
import top.roud.roudblogcms.common.annotation.OperationAuth;
import top.roud.roudblogcms.common.result.Result;
import top.roud.roudblogcms.common.utils.VisitControlUtil;
import top.roud.roudblogcms.entity.ArticleWithValidateCode;
import top.roud.roudblogcms.entity.ForbidIP;
import top.roud.roudblogcms.entity.User;
import top.roud.roudblogcms.service.ManageService;


import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;


/**
 * @ClassName: ManageController
 * @Description:
 * @Author roud
 * @Date 2022/9/25
 * @Version 1.0
 */
@Api(tags = "管理后台接口")
@RestController
@RequestMapping("/manage")
public class ManageController {
    @Autowired
    private ManageService manageService;

    @Autowired
    private VisitControlUtil visitControlUtil;

    @ApiOperation("保存用户")
    @OperationAuth
    @AccessIPRecord
    @PostMapping("/user/add")
    public Result save(@RequestBody User user, HttpServletRequest request){
        return manageService.addUser(user, request);
    }

    @ApiOperation("分页查找用户")
    @AccessIPRecord
    @GetMapping("/user/select")
    public Result findpages(HttpServletRequest req, @RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10")Integer pageSize, @RequestParam(defaultValue = "")String search){
        return manageService.selectUser(req, pageNum, pageSize, search);
    }

    @ApiOperation("更新用户")
    @OperationAuth
    @AccessIPRecord
    @PutMapping("/user/update")
    public Result update(@RequestBody User user){
        return manageService.updateUser(user);
    }

    @ApiOperation("根据id删除用户")
    @OperationAuth
    @AccessIPRecord
    @DeleteMapping("/user/del/{id}")
    public Result delById(@PathVariable Long id){
        return manageService.delUser(id);
    }

    @ApiOperation("根据token查询用户信息")
    @AccessIPRecord
    @GetMapping("/user/info")
    public Result getUserInfo(@RequestParam String token){
        return manageService.getUserInfoByToken(token);
    }

    @ApiOperation("分页查找文章")
    @AccessIPRecord
    @GetMapping("/article/fps")
    public Result fps(HttpServletRequest req, @RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10")Integer pageSize, @RequestParam(defaultValue = "")String search){
        return manageService.selectArticle(req, pageNum, pageSize, search);
    }

    @ApiOperation("文章更新")
    @OperationAuth
    @AccessIPRecord
    @PutMapping("/article/update")
    public Result updateArticle(@RequestBody ArticleWithValidateCode articleWithValidateCode){
        return manageService.updateArticle(articleWithValidateCode);
    }

    @ApiOperation("根据id删除文章")
    @OperationAuth
    @AccessIPRecord
    @DeleteMapping("/article/del/{id}")
    public Result delArticle(@PathVariable Long id){
        return manageService.delArticle(id);
    }

    @ApiOperation("添加文章")
    @OperationAuth
    @AccessIPRecord
    @PostMapping("/article/add")
    public Result addArticle(@RequestBody String info) {
        return manageService.addArticle(info);
    }

    @ApiOperation("查找所有文章标签")
    @AccessIPRecord
    @GetMapping("/tags/select")
    public Result getAllTags(){
        return manageService.selectAllTags();
    }

    @ApiOperation("分页查找封禁ip")
    @AccessIPRecord
    @GetMapping("/ip/select")
    public Result selectIps(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10")Integer pageSize, @RequestParam(defaultValue = "")String search){
        return manageService.selectIPs(pageNum, pageSize, search);
    }
    
    @ApiOperation("根据id删除封禁ip")
    @OperationAuth
    @AccessIPRecord
    @DeleteMapping("/ip/del/{id}")
    public Result delIp(@PathVariable Long id){
        return manageService.delIP(id);
    }

    @ApiOperation("添加封禁ip")
    @AccessIPRecord
    @OperationAuth
    //时间传入重构
    @PostMapping("/ip/add")
    public Result addip(@RequestBody String info){
        return manageService.addIP(info);
    }

    @ApiOperation("更新封禁ip")
    @OperationAuth
    @AccessIPRecord
    @PutMapping("/ip/update")
    public Result update(@RequestBody ForbidIP ip){
        return manageService.updateIP(ip);
    }

    @ApiOperation("根据id删除文章评论")
    @OperationAuth
    @AccessIPRecord
    @DeleteMapping("/comment/del/{id}")
    public Result delCommentById(@PathVariable(value = "id") Long id){
        return manageService.delComment(id);
    }

    @ApiOperation("分页查找封禁评论")
    @AccessIPRecord
    @GetMapping("/comment/findall")
    public Result findAll(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10")Integer pageSize, @RequestParam(defaultValue = "")String search){
        return manageService.selectComment(pageNum, pageSize, search);
    }

    @ApiOperation("根据日期获取封禁IP日志, 返回map")
    @OperationAuth
    @GetMapping("/ip/blockinglog")
    public Result getBlockinglog(){
        List<Map> list = visitControlUtil.getBlockingIPLog();
        return Result.success(list);
    }

}
