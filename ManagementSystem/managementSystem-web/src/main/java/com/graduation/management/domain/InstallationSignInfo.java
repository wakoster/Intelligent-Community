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
public class InstallationSignInfo implements Serializable{
    /**
     * 用户编号
     */
    @Id
    @ManyToOne(cascade = CascadeType.ALL)
    private UserInfo userInfo;

    /**
     * 安装包编号
     */
    @Id
    @ManyToOne(cascade = CascadeType.ALL)
    private InstallationPackage installationPackage;

    /**
     * 标注sign信息
     */
    @Column(nullable = false)
    @ColumnDefault("0")
    private Long sign;

    @Data
    static class InstallationSignInfoUPK implements Serializable {
        private UserInfo userInfo;
        private InstallationPackage installationPackage;
    }
}

