package top.roud.cms.utils;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * @ClassName: MailUtil
 * @Description:
 * @Author roud
 * @Date 2022/5/29
 * @Version 1.0
 */
public class MailUtil {
    public static final String CA = "-request-roud-mail-";
    /**
     * 发送验证码邮件（html格式）
     * @param receiver
     * @param content
     */
    public static void sendVertify (String receiver, String content) throws Exception{
        cn.hutool.extra.mail.MailUtil.send(receiver,"验证码", content,true, null);
    }

    /**
     * 获取邮箱的html内容格式
     * @param code
     * @return
     */
    public static String getMailContent(String code){
        String content = "<!DOCTYPE html>\n" +
                "<html lang=\"en\" xmlns:th=\"http://www.thymeleaf.org\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>邮箱验证码</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<table style=\"width: 700px;margin: 0 auto;\">\n" +
                "    <tbody>\n" +
                "    <tr>\n" +
                "        <td>\n" +
                "            <div id=\"top\" style=\"width: 700px;border-bottom: 1px solid #ccc;margin: 0 auto 30px;\">\n" +
                "                <table style=\"font: 12px Tahoma, Arial, 宋体;height: 40px;\">\n" +
                "                    <tbody><tr><td>\n" +
                "                        <h1>邮箱验证码</h>\n" +
                "                    </td></tr></tbody>\n" +
                "                </table>\n" +
                "            </div>\n" +
                "\n" +
                "            <div id=\"content\" style=\"width: 680px;padding: 0 10px;margin: 0 auto;\">\n" +
                "                <div id=\"content_top\" style=\"line-height: 1.5;font-size: 14px;margin-bottom: 25px;color: #4d4d4d;\">\n" +
                "                    <strong style=\"display: block;margin-bottom: 15px;\">尊敬的用户：您好！</strong>\n" +
                "                    <strong style=\"display: block;margin-bottom: 15px;\">\n" +
                "                        您正在进行<span style=\"color: #f60;font-size: 16px;\">注册账号</span>操作，请在验证码中输入以下验证码完成操作：\n" +
                "                    </strong>\n" +
                "                    <div id=\"verificationCode\" style=\"color: #f60;font-size: 48px;height: 100px;width: 680px;text-align: center;margin: 30px 0;\">\n" +
                "                        <p id=\"codetext\" style=\"line-height: 100px\">" +
                code +
                "</p>\n" +
                "                    </div>\n" +
                "                </div>\n" +
                "                <div id=\"content_bottom\" style=\"margin-bottom: 30px;\">\n" +
                "                    <small style=\"display: block;margin-bottom: 20px;font-size: 12px;color: #747474;\">\n" +
                "                        注意：此操作可能会修改您的密码、登录邮箱或绑定手机。如非本人操作，请及时登录并修改密码以保证帐户安全\n" +
                "                        <br>（工作人员不会向你索取此验证码，请勿泄漏！)\n" +
                "                    </small>\n" +
                "                </div>\n" +
                "            </div>\n" +
                "            <div id=\"bottom\" style=\"width: 700px;margin: 0 auto;\">\n" +
                "                <div style=\"padding: 10px 10px 0;border-top: 1px solid #ccc;color: #747474;margin-bottom: 20px;line-height: 1.3em;font-size: 12px;\">\n" +
                "                    <p>此为系统邮件，请勿回复<br>\n" +
                "                        请保管好您的邮箱，避免账号被他人盗用\n" +
                "                    </p>\n" +
                "                    <p id=\"sign\" style=\"text-align: right;font-size: 16px;color: #FE4F70;\">roud.top</p>\n" +
                "                </div>\n" +
                "            </div>\n" +
                "        </td>\n" +
                "    </tr>\n" +
                "    </tbody>\n" +
                "</table>\n" +
                "</body>";
        return content;
    }

    /**
     * 获取随机验证码
     * @param n
     * @return
     */
    public static String getMailRandVertifyCode(int n){
        String randStr = "";
        for(int i = 0;i<n;i++){
            randStr += String.valueOf((int) (Math.random() * 10));
        }
        return randStr;
    }

    /**
     * 获取系统校验码，防止恶意请求
     * @param email
     * @return
     */
    public static String getServerVertifyCode(String email){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH");
        String date = dateFormat.format(new Date());
        String str = email + CA + date;
        String vertify = MD5Util.md5(str);
        return vertify;
    }
}
