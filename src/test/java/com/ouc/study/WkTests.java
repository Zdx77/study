package com.ouc.study;/*
 *文件名: WkTests
 *创建者: zdx
 *创建时间:2021/7/28 19:55
 *描述: TODO
 */

import java.io.IOException;

public class WkTests {
    public static void main(String[] args) {
        //D:\wkhtmltox\bin
        String cmd = "d:/wkhtmltox/bin/wkhtmltoimage --quality 75  https://www.nowcoder.com d:/tools/data/wk_images/3.png";
        try {
            Runtime.getRuntime().exec(cmd);  // 异步
            System.out.println("ok");  //先执行
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
