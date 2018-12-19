package com.shashiwang.shashiapp.util;

public class StringUtil {

    /**
     * 获取第一个汉字
     * @param str
     * @return
     */
    public static String getFirstChinese(String str){
        String w = "";
        for (int index = 0;index<=str.length()-1;index++) {
            //将字符串拆开成单个的字符
            w = str.substring(index, index + 1);
            if (w.compareTo("\u4e00") > 0 && w.compareTo("\u9fa5") < 0) {// \u4e00-\u9fa5 中文汉字的范围
                System.out.println("第一个中文的索引位置:" + index + ",值是：" + w);
                break;
            }
        }
        return w;
    }

}
