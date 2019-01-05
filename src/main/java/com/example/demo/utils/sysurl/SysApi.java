package com.example.demo.utils.sysurl;

/**
 * Created by stan on 2017/11/6.
 */
public class SysApi {
    private String name;
    private String desc;
    private String code;
    private String parentCode;
    private String url;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    @Override
    public String toString() {
        return "SysApi{" +
                "name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", code='" + code + '\'' +
                ", parentCode='" + parentCode + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
