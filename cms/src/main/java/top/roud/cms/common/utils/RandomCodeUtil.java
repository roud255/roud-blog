package top.roud.cms.common.utils;

/**
 * @description:
 * @author: roud
 * @date: 2023/11/21
 * @version: 1.0.0
 */
public class RandomCodeUtil {
    public static String[] arr = new String[62];
    static {
        int i = 0;
        for(int a = 48;  a < 58; a++){
            arr[i] = String.valueOf((char) a);
            i++;
        }
        for(int a = 65;  a < 91; a++){
            arr[i] = String.valueOf((char) a);
            i++;
        }
        for(int a = 97;  a < 123; a++){
            arr[i] = String.valueOf((char) a);
            i++;
        }
    }
    public static String getRandomCode(int len){
        StringBuilder sb = new StringBuilder("");
        for(int i=0;i<len;i++){
            int rand = (int) (arr.length * Math.random());
            sb.append(arr[rand]);
        }
        return sb.toString();
    }
}