package com.imyeego.controller;

import com.imyeego.pojo.BaseResult;
import com.imyeego.utils.FileUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;

@Controller
public class DownloadController {

    final static String MIMETYPE = "application/octet-stream";
    final static String CONTENT_DISPOSITION = "Content-Disposition";
    final static String RANGE = "Range";
    final static String CONTENT_LENGTH = "Content-Length";
    final static String CONTENT_TYPE = "Content-Type";
    final static String CONTENT_RANGE = "Content-Range";


    @ResponseBody
    @RequestMapping(value = "/download/pycharm.exe", method = RequestMethod.GET)
    public BaseResult download(HttpServletRequest request, HttpServletResponse response){
        String path = FileUtil.getPath(request, "download");
        File file = new File(path, "pycharm.exe");
        System.out.println(file.getName());
        if (!file.exists()) return new BaseResult(200, "File not existed...");
        String range = request.getHeader("Range");
        if (range == null){
            range = "bytes=0-";
        }
        response.setContentType(MIMETYPE);

        //set response header
        String end = String.valueOf(file.length() - 1);
        String start = range.substring(6, range.indexOf("-"));
        long from = Long.parseLong(start);
        long downloadSize = file.length()- from;
        response.setHeader(CONTENT_RANGE, start + "-" + end + "/" + file.length());
        response.setHeader(CONTENT_TYPE, MIMETYPE);
        long contentLength = file.length() - Long.parseLong(start);
        response.setHeader(CONTENT_LENGTH, contentLength + "");
        response.setHeader(CONTENT_DISPOSITION, String.format("attachment; filename=%s", file.getName()));


        try(RandomAccessFile in = new RandomAccessFile(file, "rw");
            OutputStream out = response.getOutputStream()){

            if (from > 0){
                in.seek(from);
            }

            // 缓冲区大小
            int bufLen = (int) (downloadSize < 2048 ? downloadSize : 2048);
            byte[] buffer = new byte[bufLen];
            int num;
            int count = 0; // 当前写到客户端的大小
            while ((num = in.read(buffer)) != -1) {
                out.write(buffer, 0, num);
                count += num;
                //处理最后一段，计算不满缓冲区的大小
                if (downloadSize - count < bufLen) {
                    bufLen = (int) (downloadSize-count);
                    if(bufLen==0){
                        break;
                    }
                    buffer = new byte[bufLen];
                }
            }
            response.flushBuffer();


        } catch (IOException e){
            e.printStackTrace();
        }
        return new BaseResult(200, "success");

    }
}
