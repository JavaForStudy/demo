package com.example.demo.designPatterns;

/**
 * @ClassName
 * @Description TODO
 * @Auth lan
 * @Date 2019/1/8 20:48
 **/
public enum SingletonEnum {

    INSTANCE;

    private String objName;

    public String getObjName() {
        return objName;
    }

    public void setObjName(String objName) {
        this.objName = objName;
    }

    public static void main(String[] args){
        System.out.println(INSTANCE.getObjName());
    }
}
