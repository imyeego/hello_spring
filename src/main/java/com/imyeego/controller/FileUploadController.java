package com.imyeego.controller;

import com.imyeego.pojo.BaseResult;
import com.imyeego.utils.FileUtil;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

@RestController
public class FileUploadController {

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public BaseResult upload(@RequestParam("file") final MultipartFile file, HttpServletRequest request){

        String path = FileUtil.getPath(request, "upload");
        String fileName = FileUtil.generatorFileName(file.getOriginalFilename());

        File targetFile = new File(path, fileName);

        try {
            file.transferTo(targetFile);
        } catch (IOException e) {
            e.printStackTrace();
            return new BaseResult(200, "failed");
        }
        return new BaseResult(200, "success");

    }

    @RequestMapping(value = "/upload_base64", method = RequestMethod.POST)
    public BaseResult uploadFromBase64(@RequestParam("base64") final String base64, HttpServletRequest request){
        System.out.println("------" + base64 + "------");
        String path = FileUtil.getPath(request, "upload");
        FileUtil.base64ToImage(base64, path);
        return new BaseResult(200, "success");

    }
}
