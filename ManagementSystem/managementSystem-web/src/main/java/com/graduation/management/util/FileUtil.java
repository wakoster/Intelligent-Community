package com.graduation.management.util;

import org.apache.commons.lang.StringUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

@Controller
public class FileUtil {

    public static String installationPackagePath;

    @Value("${save.path.installationPackage}")
    public void setInstallationPackagePath(String installationPackagePath) {
        FileUtil.installationPackagePath = installationPackagePath;
    }

    public static String saveMultipartFile(MultipartFile multipartFile) throws Exception {
        if(StringUtils.isEmpty(installationPackagePath)){
            throw new NullPointerException("服务器获取安装包地址失败");
        }
        String originalFilename = multipartFile.getOriginalFilename();
        InputStream inputStream = multipartFile.getInputStream();
        OutputStream outputStream = new FileOutputStream(installationPackagePath + originalFilename);
        IOUtils.copy(inputStream, outputStream);
        outputStream.flush();
        inputStream.close();
        outputStream.close();
        return installationPackagePath + originalFilename;
    }
}
