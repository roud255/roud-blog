package top.roud.roudblogcms.controller.manage;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import top.roud.roudblogcms.common.annotation.OperationAuth;
import top.roud.roudblogcms.common.utils.VisitControlUtil;


/**
 * @author roud
 * @version 1.0.0
 * @describe
 * @date 2024-01-30 0:59
 */
@Api(tags = "管理后台获取封禁IP日志")
@Controller
public class ManageController2 {
    @Autowired
    private VisitControlUtil visitControlUtil;

    @ApiOperation("根据日期获取封禁IP日志, 返回HTML文件")
    @OperationAuth
    @GetMapping("manage2/ip/blockinglog")
    public String getBlockinglog(Model model){
        String msg = visitControlUtil.getBlockingIPLog2();
        model.addAttribute("title", "封禁IP日志");
        model.addAttribute("message", msg);
        return "blockingIP";
    }
}
