package com.graduation.chat.enumeration;

/**
 * 权限枚举，请与management组件中的内容保持一致，切勿轻易修改
 */
public enum AccessAuthorityEnum {
    ONLY_MANAGER("仅管理员访问",UserTypeEnum.MANAGER.getCode()),
    DEFAULT_ACCESS("默认访问",UserTypeEnum.CONSUMER.getCode() + UserTypeEnum.MANAGER.getCode())
    ;

    private String type;
    private Integer code;

    AccessAuthorityEnum(String type, Integer code)
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
