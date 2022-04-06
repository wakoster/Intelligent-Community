package com.graduation.management.enumeration;

public enum InstallationPackageOperatingStateEnum {
    UNINSTALLED("未安装"),
    INSTALLED("已安装"),
    NORMAL_OPERATION("正常运行"),
    STOPPED("已停止"),
    UN_START("未启动"),
    DEAD_STAR("启动失败")
    ;

    private String code;

    InstallationPackageOperatingStateEnum(String code)
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
