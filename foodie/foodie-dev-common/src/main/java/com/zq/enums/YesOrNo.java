package com.zq.enums;

public enum YesOrNo {

    NO(0,"NO"),
    YES(1,"YES");

    public final Integer type;
    public final String value;

    YesOrNo(Integer type, String value){
        this.type = type;
        this.value = value;
    }
}
