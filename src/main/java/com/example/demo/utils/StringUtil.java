package com.example.demo.utils;

/**
 * Created by admin on 16/2/26.
 */

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.io.*;
import java.security.MessageDigest;
import java.util.function.BiFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2015-06-17.
 */
public final class StringUtil {
    private static final Logger log = LoggerFactory.getLogger(StringUtil.class);
    private static final String UTF8 = "UTF-8";
    private static final String SHA256 = "SHA-256";
    private static final String HMAC_SHA256 = "HmacSHA256";

    /*转换byte[]为十六进制字符串，1个byte使用2个十六进制数表示0-9,A-F*/
    public static String toHex(byte[] bytes) {
        return DatatypeConverter.printHexBinary(bytes);
    }

    /*获取msg摘要值，使用SHA-256算法*/
    public static String digest(String msg) {
        try {
            MessageDigest digest = MessageDigest.getInstance(SHA256);
            digest.reset();
            return toHex(digest.digest(msg.getBytes(UTF8)));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /*获取msg签名摘要值，使用HmacSHA256算法*/
    public static String sign(String msg, String hexKey) {
        try {
            SecretKey secretKey = new SecretKeySpec(hexKey.getBytes(UTF8), HMAC_SHA256);
            Mac mac = Mac.getInstance(HMAC_SHA256);
            mac.init(secretKey);
            return toHex(mac.doFinal(msg.getBytes(UTF8)));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 计算文件的内容摘要值，上传文件保存使用
     *
     * @param file
     * @return
     */
    public static String sha1Hex(File file) {
        String sha1Hex = null;
        //读完文件记得关闭输入流
        try (InputStream fileInputStream = new BufferedInputStream(new FileInputStream(file))) {
            sha1Hex = DigestUtils.sha1Hex(fileInputStream);
        } catch (IOException e) {
            log.error("计算文件sha1Hex出错", e);
        }
        return sha1Hex;
    }

    /**
     * 判断字符串是否为空
     */
    public static boolean isEmpty(String value) {
        return value == null || "".equals(value.trim());
    }

    public static boolean isNotEmpty(String value) {
        return !isEmpty(value);
    }

    /**
     * 根据明文密码生成密文密码
     *
     * @param pwd  明文密码
     * @param salt
     * @return
     */
    public static String createPassWord(String pwd, String salt) {
        return StringUtil.sign(StringUtil.digest(pwd).toLowerCase(), salt).toLowerCase();
    }

    /**
     * 如果为空,则返回空字符串
     *
     * @param o
     * @return
     */
    public static String nullToString(Object o) {
        return o == null ? "" : o.toString();
    }

    /*将HelloWorld转换为hello_world*/
    public static String underscored(String v) {
        return transform(v, (c, i) -> {
            String sc = String.valueOf(c);

            if (Character.isUpperCase(c)) {
                sc = sc.toLowerCase();
                if (i != 0) sc = "_" + sc;
            }
            return sc;
        });
    }

    /*转换字符串*/
    public static String transform(String v, BiFunction<Character, Integer, String> charMapper) {
        if (isEmpty(v)) return v;
        StringBuilder builder = new StringBuilder();
        for (int i = 0, len = v.length(); i < len; i++) {
            builder.append(charMapper.apply(v.charAt(i), i));
        }
        return builder.toString();
    }

    /**下划线转驼峰*/
    private static Pattern linePattern = Pattern.compile("_(\\w)");
    public static String lineToHump(String str){
        // str = str.toLowerCase();
        Matcher matcher = linePattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while(matcher.find()){
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**驼峰转下划线*/
    private static Pattern humpPattern = Pattern.compile("[A-Z]");
    public static String humpToLine(String str){
        Matcher matcher = humpPattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while(matcher.find()){
            matcher.appendReplacement(sb, "_"+matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * 仅匹配中文及26个英文字母
     * 匹配中文:[\u4e00-\u9fa5]  英文字母:[a-zA-Z]   数字:[0-9]
     * 匹配中文，英文字母和数字及下划线_:  ^[\u4e00-\u9fa5_a-zA-Z0-9]+$
     * 同时判断输入长度：[\u4e00-\u9fa5_a-zA-Z0-9_]{4,10}
     * @param str
     * @return
     */
    public static boolean isMatchLetterOrChinese(String str) {
        String regex = "^[a-zA-Z\u4e00-\u9fa5]+$";
        return str.matches(regex);
    }

    public static boolean isMatchLetterDigitOrChinese(String str) {
        String regex = "^[a-z0-9A-Z\u4e00-\u9fa5]+$";
        return str.matches(regex);
    }
    public static boolean isMatchLetterDigitOrChineseOrUnderline(String str) {
        String regex = "^[-a-zA-Z0-9\u4e00-\u9fa5_]+$";
        return str.matches(regex);
    }
    public static boolean isMatchLetterDigitOrUnderline(String str) {
        String regex = "^[-a-zA-Z0-9_]+$";
        return str.matches(regex);
    }

    public static boolean isMatchDigit(String str) {
        String regex = "^[0-9]+$";
        return str.matches(regex);
    }

    public static boolean isMatchDigitOrLetter(String str) {
        String regex = "^[L][0-9]{3}";
        return str.matches(regex);
    }
}