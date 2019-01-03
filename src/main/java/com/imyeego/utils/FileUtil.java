package com.imyeego.utils;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileUtil {

    public static String generatorFileName(final String originalFileName){

        System.out.println("原始文件名称：" + originalFileName);

        //获取文件类型，以最后一个`.`为标识
        String type = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
        //获取文件名称（不包含格式）
        String name = originalFileName.substring(0, originalFileName.lastIndexOf("."));

        //设置文件新名称: 文件名称_日期（不包含格式）
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String date = sdf.format(d);
        String fileName = name + "_" + date + "." + type;

        System.out.println("保存文件名称：" + fileName);

        return fileName;
    }

    public static String getPath(HttpServletRequest request, String dir){
        String path = request.getSession().getServletContext().getRealPath("/" + dir);
        File filePath = new File(path);
        System.out.println("文件的保存路径：" + path);
        if (!filePath.exists() && !filePath.isDirectory()) {
            System.out.println("目录不存在，创建目录:" + filePath);
            filePath.mkdir();
        }
        return path;
    }

    public static void base64ToImage(final String base64String, final String uploadPath){
        String[] strings = base64String.split(",");
        String extension;
        switch (strings[0]) {//check image's extension
            case "data:image/jpeg;base64":
                extension = "jpeg";
                break;
            case "data:image/png;base64":
                extension = "png";
                break;
            default://should write cases for more images types
                extension = "jpg";
                break;
        }
        //convert base64 string to binary data
        byte[] data = DatatypeConverter.parseBase64Binary(strings[1]);
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String date = sdf.format(d);
        String fileName = date + "." + extension;
        File file = new File(uploadPath, fileName);
        try (OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file))) {
            outputStream.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
