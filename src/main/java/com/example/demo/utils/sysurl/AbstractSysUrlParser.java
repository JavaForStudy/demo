package com.example.demo.utils.sysurl;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by stan on 2017/9/18.
 */
public abstract class AbstractSysUrlParser {

    // 待解析的路径
    protected String[] dir = {"src/main/java/com/laimi/wms/controller","src/main/java/com/laimi/tms/controller","src/main/java/com/laimi/rf/controller"};

    // 跳过的类
    protected List<Class> excludeClasses = new ArrayList<>();

    // 跳过的方法, 避免多次执行
    protected List<String> excludeUrls = new ArrayList<>();

    // 要解析的 api 版本号
    protected List<String> apiVersion = new ArrayList<>();


    // 解析后的所有 url 都放到这里
    protected List<SysApi> apis = new ArrayList<>();
    // 用来生成 json 数据
    protected ObjectMapper objectMapper;
    // 用来打印日志
    protected Logger logger = LoggerFactory.getLogger(this.getClass());


    /**
     * 第一步初始化
     */
    public AbstractSysUrlParser(){
        objectMapper = new ObjectMapper();
        // objectMapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }





    /**
     * 第二步解析生成对应的 URL 实体
     */
    public void parse() throws Exception{

        /*
         * 解析 class
         */
        List<File> fs = new ArrayList<>();
        for(String d : dir){
            File file = new File(d);
            File[] controllers = file.listFiles( (dir,name) -> {
                return name.endsWith("Controller.java") || name.endsWith("Controller.groovy");
            });
            if(controllers != null && controllers.length > 0){
                fs.addAll(Arrays.asList(controllers));
            }
        }
        if(fs.isEmpty()){
            logger.info("文件路径 {} 不存在 Controller！", Arrays.toString(dir));
            return;
        }

        /*
         * 解析文件, 生成 class
         */
        for (File f : fs) {
            /*System.out.println(f);
            System.out.println(fileNameToClassName(f.getAbsolutePath()));*/

            // C:\ZSKXWorkspace\laimi-tms\src\main\java\com\laimi\wms\controller\AreaController.java
            // C:\ZSKXWorkspace\laimi-tms\src\main\java\com\laimi\wms\controller\NotifyInController.groovy

            String className = SysUrlUtil.fileNameToClassName(f.getAbsolutePath());
            Class c = Class.forName(className);
            // System.out.println(c);


            if(!c.isAnnotationPresent(RequestMapping.class)
                    || excludeClasses.contains(c)) continue;


            /*
             * 解析方法
             * 并解析方法上的每个注解, 生成对应的 URL
             */
            for(Method m : c.getDeclaredMethods()){

                if(!m.isAnnotationPresent(this.getSupportedAnnotation())
                        /*&& !m.isAnnotationPresent(SysWhiteUrl.class)*/){
                    logger.info("没有注解忽略！ {}", m);
                    continue;
                }

                Annotation anno = m.getAnnotation(this.getSupportedAnnotation());
                if(!apiVersion.contains(this.parseAnnotationVersion(anno))){
                    logger.info("版本不对，忽略！ {}", m);
                    continue;
                }

                String name = "";
                if(m.isAnnotationPresent(GetMapping.class)){
                    name = m.getAnnotation(GetMapping.class).value()[0];
                } else if(m.isAnnotationPresent(PostMapping.class)){
                    name = m.getAnnotation(PostMapping.class).value()[0];
                } else if(m.isAnnotationPresent(PutMapping.class)){
                    name = m.getAnnotation(PutMapping.class).value()[0];
                } else if(m.isAnnotationPresent(PatchMapping.class)){
                    name = m.getAnnotation(PatchMapping.class).value()[0];
                } else if(m.isAnnotationPresent(DeleteMapping.class)){
                    name = m.getAnnotation(DeleteMapping.class).value()[0];
                } else if(m.isAnnotationPresent(RequestMapping.class)){
                    name = m.getAnnotation(RequestMapping.class).value()[0];
                } else {
                    logger.info("没有找到对应URL，忽略！ {}", m);
                    continue;
                }


                String path = ((RequestMapping)c.getAnnotation(RequestMapping.class)).value()[0];
                String url = path + name;

                if(excludeUrls.contains(url)){
                    logger.info("忽略的URL，忽略！ {}", m);
                    continue;
                }


                SysApi sysApi = this.parseAnnotation(url,anno);
                apis.add(sysApi);
            }

        }




        /*
         * 输出 json
         */
        /*for (SysApi url : apis) {
            System.out.println(url);
        }*/
        this.generateSql();

    }


    /**
     * 获取 URL 解析器支持的的注解
     * @return
     */
    public abstract Class<? extends Annotation> getSupportedAnnotation();

    /**
     * 解析注解对应的版本
     * @param annotation
     * @return
     */
    public abstract String parseAnnotationVersion(Annotation annotation);

    /**
     * 解析注解
     * @param url
     * @param annotation
     * @return
     */
    public abstract SysApi parseAnnotation(String url, Annotation annotation);


    /**
     * 第三步 根据 List apis 生成对应的 SQL
     */
    public abstract void generateSql();






}
