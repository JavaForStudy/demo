package com.example.demo.common;

/**
 * Created by stan on 2018/4/27.
 */
public class BusinessException extends RuntimeException{

    private String code = DataBean.CD_000[0];
    private String desc = DataBean.CD_000[1];

    public BusinessException(String message) {
        super(message);
        this.desc = message;
    }

    public BusinessException(String code , String message) {
        super(message);
        this.desc = message;
        this.code = code;
    }
    public BusinessException(String[] codeAndDesc) {
        super(codeAndDesc[1]);
        this.desc = codeAndDesc[1];
        this.code = codeAndDesc[0];
    }
    public BusinessException(String[] codeAndDesc, Throwable cause) {
        super(codeAndDesc[1], cause);
        this.desc = codeAndDesc[1];
        this.code = codeAndDesc[0];
    }
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
