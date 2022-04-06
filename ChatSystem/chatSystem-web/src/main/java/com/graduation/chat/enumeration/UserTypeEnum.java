package com.graduation.chat.enumeration;

public enum UserTypeEnum {
    /**
     * 二进制数，用于权限控制
     */
    HIGHEST_AUTHORITY("最高系统管理员",0),
    MANAGER("系统管理员",1),
    CONSUMER("普通用户",2)
    ;

    private String type;
    private Integer code;

    UserTypeEnum(String type, Integer code)
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

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
