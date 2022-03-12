package com.graduation.management.enumeration;

public enum InstallationPackageStateEnum {
    UNINSTALLED("未安装"),
    INSTALLED("已安装")
    ;

    private String code;

    InstallationPackageStateEnum(String code)
    {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
