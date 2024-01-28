package top.roud.roudblogcms.common.config;

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

    public static String getRandomCodeCaptchaComstomized(int len){
        StringBuilder sb = new StringBuilder("");
        for(int i=0;i<len;i++){
            sb.append(getRandCharComstomized());
        }
        return sb.toString();
    }

    private static String getRandCharComstomized(){
        String s1 = "1";String s2 = "i";String s3 = "I";String s4 = "l";String s5 = "0";String s6 = "o";String s7 = "O";
        boolean flag = true;
        int rand = 0;
        while (flag){
            rand = (int) (arr.length * Math.random());
            if(!(arr[rand].equals(s1) || arr[rand].equals(s2) || arr[rand].equals(s3) || arr[rand].equals(s4) || arr[rand].equals(s5) || arr[rand].equals(s6) || arr[rand].equals(s7))){
                flag = false;
            }
        }
        return arr[rand];
    }
}