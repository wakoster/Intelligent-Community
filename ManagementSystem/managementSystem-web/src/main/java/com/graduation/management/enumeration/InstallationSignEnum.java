package com.graduation.management.enumeration;

public enum InstallationSignEnum {
    UN_FOLLOWED("未关注",false),
    FOLLOWED("已关注",true)
    ;

    private String type;
    private Boolean code;

    InstallationSignEnum(String type, Boolean code)
    {
        this.type = type;
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getCode() {
        return code;
    }

    public void setCode(Boolean code) {
        this.code = code;
    }
}
