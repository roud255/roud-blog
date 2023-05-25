package top.roud.cms.common.utils;

import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Date;

/**
 * @ClassName: CaptchaUtil
 * @Description:
 * @Author roud
 * @Date 2022/6/15
 * @Version 1.0
 */
public class CaptchaUtil {
    private static final long serialVersionUID = 1L;
    public static final char[] ARR = new char[62];
    static {
        for(int i=48;i<58;i++) {
            ARR[i-48]=(char)i;
        }
        for(int i=65;i<91;i++) {
            ARR[i-55]=(char)i;
        }
        for(int i=97;i<123;i++) {
            ARR[i-61]=(char)i;
        }
    }

    public static String getCode() {
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<4;i++) {
            int rand=(int) (Math.random()*62);
            sb.append(ARR[rand]);
        }
        return sb.toString();
    }
    public static BufferedImage doDraw(String str, int w, int h, int amout){
        return doDraw(str, w, h, amout,"Fixedsys", 12, Color.darkGray, new Color[]{Color.white, new Color(128,206,235), Color.green, Color.CYAN, Color.yellow});
    }

    public static BufferedImage doDraw(String str, int w, int h, int amout, String fontFamily, int fontSize, Color bg, Color[] colorArr){
        int imgWidth = w;
        int imgHeight = h;
        int interferenceLineCount = amout;
        //创建图片缓冲区
        BufferedImage img = new BufferedImage(imgWidth,imgHeight, BufferedImage.TYPE_INT_RGB);
        //获取画笔
        Graphics g = img.getGraphics();
        //设置画笔颜色为darkgray
        g.setColor(bg);
        //从0，0坐标开始宽为图片宽高为图片高填充整个图片（即将图片全部填充为某某色）
        g.fillRect(0, 0, imgWidth, imgHeight);

        //设置画笔颜色、字体名称类型（BOLD加粗PLAIN正常）大小
        g.setColor(colorArr[0]);
        g.setFont(new Font(fontFamily,Font.BOLD,(imgWidth/5)));


        String[] arr=str.split("");
        //设置开始坐标x
        int x=imgWidth/10;
        //for(String s:arr)
        for(int i=0;i<arr.length;i++){
            //坐标y为区间随机，即让四个码不在同一水平线上
            int y=(int) (Math.random()*(imgWidth/5))+(imgHeight-(imgWidth/5));
            //开始作画字符串，开始坐标（x,y）
            g.drawString(arr[i], x, y);
            //x坐标递增，不然都在同一x坐标处
            x+=(imgWidth/5);
            //让每个字符串显示不同颜色
            if(i==0) {
                g.setColor(colorArr[1]);
            }else if(i==1){
                g.setColor(colorArr[2]);
            }else if(i==2){
                g.setColor(colorArr[3]);
            }}

        //画干扰线
        //设置颜色字体
        g.setColor(colorArr[4]);
        g.setFont(new Font(fontFamily,Font.PLAIN,fontSize));
        for(int i=0;i<interferenceLineCount;i++) {//interferenceLineCount条干扰线
            int rand1=(int)(Math.random()*imgWidth);
            int rand2=(int)(Math.random()*imgHeight);

            int rand3=(int)(Math.random()*imgWidth);
            int rand4=(int)(Math.random()*imgHeight);

            g.drawLine(rand1, rand2, rand3, rand4);//干扰线从坐标1开始到坐标2结束
        }
        g.dispose();
        return img;
    }


}
