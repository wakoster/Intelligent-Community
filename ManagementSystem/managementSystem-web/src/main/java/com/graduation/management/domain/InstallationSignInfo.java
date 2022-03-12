package com.graduation.management.domain;

import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.io.Serializable;

/**
 * InstallationSignInfo
 * 保存安装包和用户的sign关系
 */
@Data
@Entity
@IdClass(InstallationSignInfo.InstallationSignInfoUPK.class)
public class InstallationSignInfo{
    /**
     * 用户编号
     */
    @Id
    @ManyToOne(cascade = CascadeType.ALL)
    private UserInfo user_info;

    /**
     * 安装包编号
     */
    @Id
    @ManyToOne(cascade = CascadeType.ALL)
    private InstallationPackage installation_package;

    /**
     * 标注sign信息
     */
    @Column(nullable = false)
    @ColumnDefault("false")
    private Boolean sign;

    @Data
    static class InstallationSignInfoUPK implements Serializable {
        private UserInfo user_info;
        private InstallationPackage installation_package;
    }
}

