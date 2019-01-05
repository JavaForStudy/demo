package com.example.demo.utils.sysurl;

import com.example.demo.utils.CollectionUtil;
import com.example.demo.utils.OpUtil;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by stan on 2017/9/18.
 */
public class SysUrlParser extends AbstractSysUrlParser{

    /**
     * 第一步初始化
     */
    public SysUrlParser(){
        // 要解析的版本号
        // apiVersion.add(SysUrlVersion.WMS_1);
        // apiVersion.add(SysUrlVersion.WMS_2);
        apiVersion.add(SysUrlVersion.RF_1);

        // 要忽略的 Class
//         excludeClasses.add(WmsShiftTaskController.class);

        // 要忽略的 URL
        /*excludeUrls.add("/api/stockRecord/export");
        excludeUrls.add("/api/permission/role/{roleId}/stop");
        excludeUrls.add("/api/permission/deleteRole/{roleId}");
        excludeUrls.add("/api/order/export/{id}");
        excludeUrls.add("/api/plan/switch");
        excludeUrls.add("/api/plan/plan/import");
        excludeUrls.add("/api/plan/planedOrder");
        excludeUrls.add("/api/plan/mergePlan");
        excludeUrls.add("/api/orderPrint/view");
        excludeUrls.add("/api/orderPrint/saveHistory/{id}");
        excludeUrls.add("/api/orderPrint/printSelected");
        excludeUrls.add("/api/printConfig/update/{id}");
        excludeUrls.add("/api/line/importList");
        excludeUrls.add("/api/line/exportList");
        excludeUrls.add("/api/lineDetail/updateCars/{lineId}");
        excludeUrls.add("/api/lineAdjust/saveLines");
        excludeUrls.add("/api/car/import");
        excludeUrls.add("/api/plan/manualTruncate/{planId}");
        excludeUrls.add("/api/vehicleRoute/planRoute/{planId}");
        excludeUrls.add("/api/notify/in/complete");
        excludeUrls.add("/api/print/notifyIn/{id}");
        excludeUrls.add("/api/wmsInTask/save");
        excludeUrls.add("/api/wmsInTask/saveAndFinish");
        excludeUrls.add("/api/wmsInTask/finish/{taskId}");
        excludeUrls.add("/api/wmsInTask/cancel/{taskId}");
        excludeUrls.add("/api/notify/in/export");
        excludeUrls.add("/api/wmsRefundTask/save");
        excludeUrls.add("/api/wmsRefundTask/saveAndFinish");
        excludeUrls.add("/api/wmsRefundTask/finish/{taskId}");
        excludeUrls.add("/api/wmsRefundTask/cancel/{taskId}");
        excludeUrls.add("/api/notify/refund/export");
        excludeUrls.add("/api/stockRecord/export");
        excludeUrls.add("/api/stock/exportLocation");*/

    }

    @Override
    public Class<? extends Annotation> getSupportedAnnotation(){
        return SysUrl.class;
    }

    @Override
    public String parseAnnotationVersion(Annotation annotation) {
        SysUrl sysUrl = (SysUrl)getSupportedAnnotation().cast(annotation);
        return sysUrl.version();
    }

    @Override
    public SysApi parseAnnotation(String url, Annotation annotation){
        SysUrl sysUrl = (SysUrl)getSupportedAnnotation().cast(annotation);
        SysApi sysApi = new SysApi();
        sysApi.setUrl(url);
        sysApi.setName(sysUrl.name());
        sysApi.setDesc(sysUrl.desc());
        sysApi.setCode(sysUrl.code());
        sysApi.setParentCode(sysUrl.parentCode());
        return sysApi;
    }


    /**
     * 第三步 生成 SQL
     */
    @Override
    public void generateSql(){

        /*
         * 校验 code 是否唯一
         */
        List<String> duplicateCodes = CollectionUtil.getDuplicateElements(apis.stream().map(SysApi::getCode).collect(Collectors.toList()));
        if(OpUtil.isNotEmpty(duplicateCodes)){
            throw new RuntimeException(String.format("重复的 codes！[%s]",duplicateCodes));
        }


        /*
         * 合并 parentCode
         */
        Map<String,List<SysApi>> parentMaps = apis.stream().collect(Collectors.groupingBy( SysApi::getParentCode));


        /*
         * 生成 SQL
         */
        System.out.println();
        System.out.println("========================= 开始输出 sql ==============================");
        System.out.println();


        for (String parent : parentMaps.keySet()) {
            String parentSql = "SELECT `resource_id` INTO @pId FROM `t_sys_resource` WHERE `code` = '%s' AND `system_code`='%s';";
            System.out.println(String.format(parentSql,parent, SysUrlVersion.SYS_CODE));
            for(SysApi api : parentMaps.get(parent)){
                String apiSql = "INSERT INTO `t_sys_resource` (`resource_id`, `system_code`, `code`, `name`, `description`, `url`, `type`, `menu_layout`, `p_id`, `order_sort`, `all`, `menu_router_url`, `menu_icon`, `status`, `created_by`,  `last_updated_by`)\n" +
                        "\tVALUES (null, 'tms', '%s', '%s', '%s', '%s', 'api', NULL, @pId, '0', '0', '', '', 'enable', '0', '0');";
                System.out.println(String.format(apiSql,api.getCode(),api.getName(),api.getDesc(),api.getUrl()));
            }
            System.out.println();
        }


    }


    /**
     * 执行入口
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception{
        SysUrlParser urlParser = new SysUrlParser();
        urlParser.parse();
    }





}
