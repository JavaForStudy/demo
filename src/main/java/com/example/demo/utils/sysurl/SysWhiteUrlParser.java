package com.example.demo.utils.sysurl;

import java.lang.annotation.Annotation;

/**
 * Created by stan on 2017/9/18.
 */
public class SysWhiteUrlParser extends AbstractSysUrlParser {

    /**
     * 第一步初始化
     */
    public SysWhiteUrlParser(){
        // 要解析的版本号
        /*apiVersion.add(SysUrlVersion.WMS_1);
        apiVersion.add(SysUrlVersion.WMS_2);
        apiVersion.add(SysUrlVersion.WMS_3);*/
//        apiVersion.add(SysUrlVersion.V2_8_0);
        apiVersion.add(SysUrlVersion.RF_2);


        // 要忽略的 Class
        // excludeClasses.add(WmsShiftTaskController.class);

        // 要忽略的 URL
//        excludeUrls.add("/api/area/listLocation");
//        excludeUrls.add("/api/notify/in/list");
//        excludeUrls.add("/api/wmsInTask/getTaskList");
//        excludeUrls.add("/api/notify/in/notify");
//        excludeUrls.add("/api/notify/in/timebase");
//        excludeUrls.add("/api/notify/in/itemTotal");
//        excludeUrls.add("/api/notify/in/notifyItems");
//        excludeUrls.add("/api/wmsInTask/itemTasks");
//        excludeUrls.add("/api/notify/in/record");
//        excludeUrls.add("/api/wmsInTask/getTaskDetail/{taskId}");
//        excludeUrls.add("/api/notify/in/items");
//        excludeUrls.add("/api/notify/refund/list");
//        excludeUrls.add("/api/wmsRefundTask/getTaskList");
//        excludeUrls.add("/api/notify/refund/notify");
//        excludeUrls.add("/api/notify/refund/timebase");
//        excludeUrls.add("/api/wmsRefundTask/itemTasks");
//        excludeUrls.add("/api/notify/refund/record");
//        excludeUrls.add("/api/wmsRefundTask/getTaskDetail/{taskId}");
//        excludeUrls.add("/api/area/listAll");
//        excludeUrls.add("/api/stockRecord/list");
//        excludeUrls.add("/api/stock/list");

    }



    @Override
    public Class<? extends Annotation> getSupportedAnnotation(){
        return SysWhiteUrl.class;
    }

    @Override
    public String parseAnnotationVersion(Annotation annotation) {
        SysWhiteUrl sysUrl = (SysWhiteUrl)getSupportedAnnotation().cast(annotation);
        return sysUrl.version();
    }

    @Override
    public SysApi parseAnnotation(String url, Annotation annotation){
        SysWhiteUrl sysUrl = (SysWhiteUrl)getSupportedAnnotation().cast(annotation);
        SysApi sysApi = new SysApi();
        sysApi.setUrl(url);
        sysApi.setName(sysUrl.name());
        sysApi.setDesc(sysUrl.desc() != null & sysUrl.desc().trim().length() > 0 ?
                sysUrl.desc() : sysUrl.name());
        return sysApi;
    }



    /**
     * 第三步, 生成 SQL
     */
    public void generateSql(){
        System.out.println();
        System.out.println("========================= 开始输出 sql ==============================");
        System.out.println();


        for (SysApi api : apis) {
            String insertSysWhiteUrl = "INSERT INTO `t_sys_resource_white_list` (`system_code`, `name`, `description`, `url`) \n" +
                    "\tVALUES ('%s', '%s', '%s', '%s');";
            System.out.println(String.format(insertSysWhiteUrl, SysUrlVersion.SYS_CODE,api.getName(),api.getDesc(),api.getUrl()));
        }
    }

    public static void main(String[] args) throws Exception{
        SysWhiteUrlParser urlParser = new SysWhiteUrlParser();
        urlParser.parse();
    }

}




