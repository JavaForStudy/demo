package com.example.demo.common;

/**
 * Created by Administrator on 2015/3/17.
 */
public class DataBean<T> {
    private int total;
    private T data;
    private String desc = CD_200[1];
    private String code = CD_200[0];

    public static final String CD_000[] = {"000", "失败"};
    public static final String CD_200[] = {"200", "成功"};
    public static final String CD_400[] = {"400", "坏的请求"};
    public static final String CD_401[] = {"401", "未授权"};
    public static final String CD_411[] = {"411", "该账号已在其它设备登录，该设备ID为: %s"};
    public static final String CD_455[] = {"455", "版本不兼容，当前版本: %s，最新版本: %s"};
    public static final String CD_403[] = {"403", "你没有此权限"};
    public static final String CD_500[] = {"500", "服务器内部错误"};
    public static final String CD_501[] = {"501", "用户信息或状态非法"};
    public static final String CD_502[] = {"502", "导入数据为空"};
    public static final String CD_503[] = {"503", "没有导入文件"};
    public static final String CD_504[] = {"504", "只支持导入2007版以上Excel文件"};
    public static final String CD_505[] = {"505", "导入Excel模板错误"};
    public static final String CD_506[] = {"506", "Excel数据验证失败，请下载验证结果"};
    public static final String CD_510[] = {"510", "下游服务调用失败，请稍后重试"};
    public static final String CD_511[] = {"511", "RF端请求header信息非法: %s"};
    public static final String CD_512[] = {"512", "RF全局配置丢失"};
    public static final String CD_513[] = {"513", "账号不存在"};
    public static final String CD_515[] = {"515", "参数错误--%s：%s"};
    public static final String CD_516[] = {"516", "当前所属仓库 [%s] 与操作仓库 [%s] 不符，禁止操作！"};


    public static final String CD_601[] = {"601", "账号或密码错误"};
    public static final String CD_602[] = {"602", "用户信息错误-用户已停用"};
    public static final String CD_603[] = {"603", "当前仓库未关闭自动排车"};
    public static final String CD_604[] = {"604", "当前仓库坐标未维护"};
    public static final String CD_605[] = {"605", "用户信息错误-无法查询到有效用户"};

    // 司机
    public static final String CD_701[] = {"701", "空闲状态的司机方可停用"};
    public static final String CD_702[] = {"702", "停用状态的司机可启用"};
    public static final String CD_703[] = {"703", "司机驾驶证已存在"};
    public static final String CD_777[] = {"777", "司机非停用"};

    // 车辆
    public static final String CD_801[] = {"801", "空闲状态的车辆方可停用"};
    public static final String CD_802[] = {"802", "停用状态的车辆可启用"};
    public static final String CD_803[] = {"803", "车牌号码已经存在"};
    public static final String CD_804[] = {"804", "发动机号已经存在"};
    public static final String CD_805[] = {"805", "车架号已经存在"};
    public static final String CD_888[] = {"888", "车辆非停用"};

    // 角色
    public static final String CD_901[] = {"901", "角色名称已经存在"};
    public static final String CD_902[] = {"902", "停用状态、未启用状态并保证上级角色有效的角色方可启用"};
    public static final String CD_903[] = {"903", "不能删除超级管理员角色"};
    public static final String CD_904[] = {"904", "非启用状态的角色不能停用"};
    public static final String CD_905[] = {"905", "该角色下无账户才能停用"};
    public static final String CD_906[] = {"906", "所有下级角色停用才能停用角色"};
    public static final String CD_907[] = {"907", "角色名称已经存在"};
    public static final String CD_908[] = {"908", "角色不存在"};
    public static final String CD_909[] = {"909", "不能操作tms超级管理员角色"};
    public static final String CD_910[] = {"910", "不能修改自身角色"};
    public static final String CD_911[] = {"911", "仅超管可删除系统角色"};
    public static final String CD_912[] = {"912", "已经被管理员移除该仓库的权限"};
    public static final String CD_913[] = {"913", "只有超级管理员才可以编辑系统类型的角色"};
    public static final String CD_914[] = {"914", "仅超管可启用系统角色"};
    public static final String CD_915[] = {"915", "只有 '未启用的角色可以直接删除' 或 '已停用并无下级角色的角色可由超级管理员删除'"};
    public static final String CD_916[] = {"916", "不能编辑自己的角色"};
    public static final String CD_917[] = {"917", "仅超管可停用系统角色"};
    public static final String CD_918[] = {"918", "不能编辑超级管理员角色"};

    // 计划
    public static final String CD_950[] = {"950", "至少选择两个计划"};
    public static final String CD_951[] = {"951", "未确认的计划才能合并"};

    //线路
    public static final String CD_960[] = {"960", "该车辆已存在于该线路,不可重复添加"};

    // 库存
    public static final String CD_970[] = {"970", "库存不足"};
    public static final String CD_971[] = {"971", "库存待补货"};
    public static final String CD_972[] = {"972", "待补货单无需补货"};
    public static final String CD_973[] = {"973", "库存待上架"};

    //WMS
    public static final String CD_1000[] = {"1000", "收货单,入库商品种类超过通知商品种类"};
    public static final String CD_1001[] = {"1001", "收货单,入库商品数量超过通知商品数量"};
    public static final String CD_1002[] = {"1002", "收货单,任务入库数量不能大于剩余可入库数量"};
    public static final String CD_1003[] = {"1003", "收货单,更新失败，请重试"};
    public static final String CD_1004[] = {"1004", "收货单,单据入库种类不能小于零"};
    public static final String CD_1005[] = {"1005", "收货单,单据入库数量不能小于零"};
    public static final String CD_1006[] = {"1006", "收货单,商品明细入库数量不能小于零"};
    public static final String CD_1007[] = {"1007", "收货单,商品明细正品入库数量不能小于零"};
    public static final String CD_1008[] = {"1008", "收货单,商品明细残品入库数量不能小于零"};
    public static final String CD_1009[] = {"1009", "收货单,商品明细剩余入库数量不能大于通知数量"};
    public static final String CD_1010[] = {"1010", "还有未完成的任务，不能结束单据"};
    public static final String CD_1011[] = {"1011", "采购单,入库数量不能大于通知数量"};
    public static final String CD_1012[] = {"1012", "入库商品种类大于通知商品种类"};
    public static final String CD_1013[] = {"1013", "入库商品数量必须等于通知商品数量"};
    public static final String CD_1014[] = {"1014", "收货单,部分入库才能完成收货"};
    public static final String CD_1015[] = {"1015", "收货单,只能创建一个任务"};
    public static final String CD_1016[] = {"1016", "退供单,通知状态下的退工单才能下架"};
    public static final String CD_1017[] = {"1017", "退供单,预分配的单据才允许创建任务"};
    public static final String CD_1018[] = {"1018", "已下架的单据才允许完全"};
    public static final String CD_1019[] = {"1019", "移位单,移位预分配失败"};
    public static final String CD_1020[] = {"1020", "移位单,当前移位单不能取消"};
    public static final String CD_1021[] = {"1021", "移位单,当前移位单不存在"};
    public static final String CD_1022[] = {"1022", "移位单,导入excel规定的列有空值"};
    public static final String CD_1023[] = {"1023", "盘点单,当前盘点单不存在"};
    public static final String CD_1024[] = {"1024", "盘点单,账面数与实物库存不一致"};
    public static final String CD_1025[] = {"1025", "盘点单,创建通知类型异常,请检查参数"};
    public static final String CD_1026[] = {"1026", "盘点单,当前盘点单不能取消"};
    public static final String CD_1027[] = {"1027", "盘点单,创建通知商品异常,出现重复的商品,请先处理再提交"};
    public static final String CD_1028[] = {"1028", "盘点单,商品库位没有填写"};
    public static final String CD_1029[] = {"1029", "盘点单,商品编码没有填写"};
    public static final String CD_1030[] = {"1030", "盘点单,商品条码没有填写"};
    public static final String CD_1031[] = {"1031", "盘点单,商品名称没有填写"};
    public static final String CD_1032[] = {"1032", "盘点单,创建异常"};
    public static final String CD_1033[] = {"1033", "销退入库,合并上架失败,请检查收货状态，上架状态等信息"};
    public static final String CD_1034[] = {"1034", "调拨类型的收货入库,商品入库数量与通知数量必须一致"};

    //商品
    public static final String CD_1226[] = {"1226", "副条码之间不可重复"};
    public static final String CD_1227[] = {"1227", "副条码不能与主条码重复"};
    //库工
    public static final String CD_1314[] = {"1314", "库工编号已经存在"};
    public static final String CD_1315[] = {"1315", "停用状态才可以编辑"};
    public static final String CD_1316[] = {"1316", "正常状态的库工才可以停用"};
    public static final String CD_1317[] = {"1317", "停用状态的车辆才可以启用"};
    public static final String CD_1318[] = {"1318", "库工非停用,无法调遣"};
    public static final String CD_1319[] = {"1319", "工号不存在"};
    public static final String CD_1320[] = {"1320", "库工已停用"};
    public static final String CD_1321[] = {"1321", "工号不属于当前仓库"};

    public static final String CD_1322[] = {"1322", "库工账号不能为空"};
    public static final String CD_1323[] = {"1323", "库工账号已被其他库工关联"};

    public static final String CD_2001[] = {"2001", "以下单据状态不允许预分配"};
    public static final String CD_2088[] = {"2088", "通知单状态不是预分配"};
    public static final String CD_2089[] = {"2089", "发货单已出库无法拦截"};
    public static final String CD_2090[] = {"2090", "以下单据不是待补货单据"};

    public static final String CD_3001[] = {"3001", "预分配成功的单据才允许提交任务"};
    public static final String CD_3002[] = {"3002", "没有符合预分配条件的单据"};
    public static final String CD_3003[] = {"3003", "没有待补货的单据"};
    public static final String CD_3004[] = {"3004", "拦截调用库存失败，请重试"};
    public static final String CD_3005[] = {"3005", "单据已开始处理，无法拦截"};
    public static final String CD_3006[] = {"3006", "拦截取消对应任务失败"};
    public static final String CD_3007[] = {"3007", "单据不存在"};
    public static final String CD_3008[] = {"3008", "通知状态的通知单才能登记到货"};
    public static final String CD_3009[] = {"3009", "通知单已登记到货,请勿重复登记"};
    public static final String CD_3010[] = {"3010", "送货人名称不能为空且仅支持中文和26个英文字母"};
    public static final String CD_3011[] = {"3011", "送货人联系方式不能为空且仅支持中文和26个英文字母"};
    public static final String CD_3012[] = {"3012", "到货时间不能为空"};
    public static final String CD_3013[] = {"3013", "送货车牌号不能为空且仅支持中文,26个英文字母,0-9数字"};

    //任务
    public static final String CD_3190[] = {"3190", "任务进行中，请不要重复作业"};
    public static final String CD_3191[] = {"3191", "任务已完成，无法作业"};
    public static final String CD_3192[] = {"3192", "任务已取消，无法作业"};
    public static final String CD_3193[] = {"3193", "任务不存在，无法作业"};
    public static final String CD_3194[] = {"3194", "任务尚未拣选，无法质检"};
    public static final String CD_3195[] = {"3195", "任务已完成，无法质检"};
    public static final String CD_3196[] = {"3196", "任务已取消，无法质检"};
    public static final String CD_3197[] = {"3197", "任务不存在，无法质检"};


    //预约单
    public static final String CD_3200[] = {"3200", "预约单必须关联一张收货通知单"};
    public static final String CD_3201[] = {"3201", "预约人名称仅支持中文和26个英文字母"};
    public static final String CD_3202[] = {"3202", "预约人联系方式仅支持数字"};
    public static final String CD_3203[] = {"3203", "仓库不存在该通知单"};
    public static final String CD_3204[] = {"3204", "通知单的到货登记状态必须为未到货才能预约"};
    public static final String CD_3205[] = {"3205", "通知单状态必须为已通知才能预约"};
    public static final String CD_3206[] = {"3206", "该通知单已预约,请勿重复预约"};
    public static final String CD_3207[] = {"3207", "状态为预约和超期的预约单才可以编辑"};


    // 绩效
    public static final String CD_3300[] = {"3300", "仓库 [%s] %s绩效参数未配置！"};


    //质检打包
    public static final String CD_4000[] = {"4000", "任务已取消，质检失败"};
    public static final String CD_4001[] = {"4001", "任务已完成，请勿重复质检"};
    public static final String CD_4002[] = {"4002", "任务尚未拣选，质检失败"};
    public static final String CD_4003[] = {"4003", "质检打包仅适用于波次策略二中的拆零商品 ，质检失败"};
    public static final String CD_4004[] = {"4004", "任务正在质检，质检失败"};

    public static final String CD_4005[] = {"4005", "用户id为空"};
    public static final String CD_4006[] = {"4006", "任务id和任务编号不能为空"};
    public static final String CD_4007[] = {"4007", "该用户暂存任务不能超过5个"};
    public static final String CD_4008[] = {"4008", "此任务编号不存在，请核实任务编号"};
    public static final String CD_4009[] = {"4009", "任务必须为发货任务，请核实任务编号"};
    public static final String CD_4010[] = {"4010", "此扩展任务编号不存在，请核实任务编号"};

    //RF收货入库
    public static final String CD_5001[] = {"5001", "该通知单状态已取消或已完成"};
    public static final String CD_5002[] = {"5002", "该通知单不存在有效预约"};
    public static final String CD_5003[] = {"5003", "该通知单收货任务配置的执行方式不是RF或自动"};
    public static final String CD_5004[] = {"5004", "任务编号不能为空"};

    //RF补货移位
    public static final String CD_6001[] = {"6001", "该任务移位数量不能为空"};

    public DataBean() {

    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public DataBean(T data) {
        this.data = data;
    }

    public DataBean(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public DataBean(String[] codeAndDesc) {
        if (codeAndDesc != null && codeAndDesc.length >= 2) {
            this.code = codeAndDesc[0];
            this.desc = codeAndDesc[1];
        }
    }
    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


    public static <T> DataBean<T> ofSuccess(T data) {
        DataBean<T> dataBean = new DataBean<>();
        dataBean.setData(data);
        return dataBean;
    }


    /**
     * 判断方法是否执行成功
     * @return
     */
    public boolean success(){
        return DataBean.CD_200[0].equals(code);
    }
    public boolean error(){
        return !success();
    }

    public static DataBean ofFail(String desc) {
        DataBean dataBean = new DataBean<>();
        dataBean.setCode(CD_000[0]);
        dataBean.setDesc(desc);
        return dataBean;
    }

}
