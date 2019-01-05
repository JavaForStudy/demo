package com.example.demo.utils.sysurl;

/**
 * Created by stan on 2017/11/6.
 */
public class SysUrlUtil {
    public static String fileNameToClassName(String fileName){
        return fileName.replaceAll(".+src\\\\main\\\\java\\\\","")
                .replaceAll("\\.java|\\.groovy","")
                .replaceAll("\\\\",".");
    }

}
