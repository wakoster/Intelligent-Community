package com.graduation.management.util;

import com.graduation.management.result.BaseResult;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class NSSMUtil {
    //创建servername服务
    public static BaseResult install(String installationName,String fileName) throws Exception{
        File file = new File(FileUtil.installationPackagePath + "install.bat");
        //输入流
        FileOutputStream fileOutputStream;
        try {
            fileOutputStream = new FileOutputStream(file);
            if(!file.exists()){
                file.createNewFile();
            }
            //将字符串转成字节
            String jar = "java -jar " + fileName;
            byte[] bytes = jar.getBytes();
            // 写入文件
            fileOutputStream.write(bytes);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String powerShell = "cd " + FileUtil.installationPackagePath + ";.\\nssm install " + installationName + " \"" + FileUtil.installationPackagePath + "install.bat\"";
        return powerShell(powerShell);
    }
    //启动服务
    public static BaseResult start(String installationName) throws Exception {
        String powerShell = "cd " + FileUtil.installationPackagePath + ";.\\nssm start " + installationName;
        return powerShell(powerShell);
    }
    //暂停服务
    public static BaseResult stop(String installationName) throws Exception {
        String powerShell = "cd " + FileUtil.installationPackagePath + ";.\\nssm stop " + installationName;
        return powerShell(powerShell);
    }
    //重新启动服务
    public static BaseResult restart(String installationName) throws Exception {
        String powerShell = "cd " + FileUtil.installationPackagePath + ";.\\nssm restart " + installationName;
        return powerShell(powerShell);
    }
    //删除创建的服务
    public static BaseResult remove(String installationName) throws Exception {
        String cmd = "sc delete " + installationName;
        return cmd(cmd);
    }

    private static BaseResult powerShell(String powerShell) throws Exception {
        Runtime runtime = Runtime.getRuntime();  //获取Runtime实例
        //执行命令
        String[] command = {"PowerShell", "/c", powerShell};
        Process process = runtime.exec(command);
        // 标准输入流（必须写在 waitFor 之前）
        String inStr = consumeInputStream(process.getInputStream());
        // 标准错误流（必须写在 waitFor 之前）
        String errStr = consumeInputStream(process.getErrorStream()); //若有错误信息则输出
        int proc = process.waitFor();
        if (proc == 0) {
            return BaseResult.SUCCESS(inStr);
        } else {
            return BaseResult.FAIL((long) -1,null,errStr);
        }
    }

    private static BaseResult cmd(String cmd) throws Exception {
        Runtime runtime = Runtime.getRuntime();  //获取Runtime实例
        //执行命令
        String[] command = {"cmd", "/c", cmd};
        Process process = runtime.exec(command);
        // 标准输入流（必须写在 waitFor 之前）
        String inStr = consumeInputStream(process.getInputStream());
        // 标准错误流（必须写在 waitFor 之前）
        String errStr = consumeInputStream(process.getErrorStream()); //若有错误信息则输出
        int proc = process.waitFor();
        if (proc == 0) {
            return BaseResult.SUCCESS(inStr);
        } else {
            return BaseResult.FAIL((long) -1,null,errStr);
        }
    }

    private static String consumeInputStream(InputStream is) throws IOException {
        //TODO:这里的编码格式会导致输出中文乱码，但目前不知道nssm输出的是什么格式的中文,GBK格式输出也是乱码，后期修改
        BufferedReader br = new BufferedReader(new InputStreamReader(is, "GBK"));
        String s;
        StringBuilder sb = new StringBuilder();
        while ((s = br.readLine()) != null) {
            sb.append(s);
        }
        return sb.toString();
    }
}
