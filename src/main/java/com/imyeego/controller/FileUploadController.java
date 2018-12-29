package com.imyeego.controller;

import com.imyeego.pojo.BaseResult;
import com.imyeego.utils.FileNameGenerator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

@RestController
public class FileUploadController {

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public BaseResult upload(@RequestParam("file") final MultipartFile file, HttpServletRequest request){

        String path = FileNameGenerator.getUploadPath(request, "upload");
        String fileName = FileNameGenerator.generatorFileName(file.getOriginalFilename());

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
        String path = FileNameGenerator.getUploadPath(request, "upload");
        FileNameGenerator.base64ToImage(base64, path);
        return new BaseResult(200, "success");

    }
}
