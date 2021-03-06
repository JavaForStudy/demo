package com.example.demo.utils.sysurl;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by stan on 2017/11/6.
 */

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SysUrl {

    /**
     * 接口的版本
     * 解析的时候可以根据指定的版本来解析
     * 使用这里的值 com.laimi.tms.utils.sysurl.SysUrlVersion
     */
    String version();

    /**
     * 接口的名称
     */
    String name();

    /**
     * 接口的注释
     */
    String desc();

    /**
     * 接口的 url, 可以手动生成, 所有不用再配置了
     */
    // 可以手动生成,不用在配置了
    // String url();


    /**
     * 这个 code 必须唯一
     * @return
     */
    String code();
    /**
     * 这个 url 放到哪个二级菜单那里
     * 注意： 这个值必须准确
     */
    String parentCode();

}
