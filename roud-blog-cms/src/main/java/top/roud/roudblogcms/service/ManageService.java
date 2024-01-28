package top.roud.roudblogcms.service;

import org.springframework.web.bind.annotation.RequestParam;
import top.roud.roudblogcms.common.result.Result;
import top.roud.roudblogcms.entity.Article;
import top.roud.roudblogcms.entity.ArticleWithValidateCode;
import top.roud.roudblogcms.entity.ForbidIP;
import top.roud.roudblogcms.entity.User;

import javax.servlet.http.HttpServletRequest;

/**
 * @description:
 * @author: guangrui_hu
 * @date: 2024/1/26
 * @version: 1.0.0
 */
public interface ManageService {
    public Result addUser(User user, HttpServletRequest request);
    public Result selectUser(HttpServletRequest request, Integer pageNum, Integer pageSize, String search);
    public Result updateUser(User user);
    public Result delUser(Long id);
    public Result getUserInfoByToken(String token);
    public Result selectArticle(HttpServletRequest req, Integer pageNum, Integer pageSize, String search);
    public Result updateArticle(ArticleWithValidateCode articleWithValidateCode);
    public Result delArticle(Long id);
    public Result addArticle(String articleInfo);
    public Result selectAllTags();
    public Result selectIPs(Integer pageNum, Integer pageSize, String search);
    public Result delIP(Long id);
    public Result updateIP(ForbidIP ip);
    public Result addIP(String ipInfo);
    public Result delComment(Long id);
    public Result selectComment(Integer pageNum, Integer pageSize, String search);

}
