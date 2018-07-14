package com.kedacom.controller;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;


import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

/**
 * package: com.kedacom.controller
 * author: yaorui
 * date : 2018/7/9
 */
@Api(value = "文件上传下载Controller", tags = "文件上传下载操作接口")
@RestController
@Slf4j
public class FileController {

    @Value("prop.spring-folder")
    private String UPLOAD_FOLDER ;

    /**
     * 单文件上传
     */
    @PostMapping("/fileUpload")
    public String imageUpload(@RequestParam("file") MultipartFile file){
        log.info("图片上传：{}", JSON.toJSONString(file,true));
        if (Objects.isNull(file) || file.isEmpty()) {
            log.error("文件为空");
            return "文件为空，请重新上传";
        }
        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOAD_FOLDER + file.getOriginalFilename());
            //如果没有files文件夹，则创建
            if (!Files.isWritable(path)) {
                Files.createDirectories(Paths.get(UPLOAD_FOLDER));
            }
            //文件写入指定路径
            Files.write(path, bytes);
            log.info("文件写入成功");
            return "文件上传成功";
        } catch (IOException e) {
            e.printStackTrace();
            return "文件传输异常";
        }
    }

    /**
     * 多文件上传
     */
    @PostMapping("/filesUpload")
    public String multifileUpload(HttpServletRequest request){
        List<MultipartFile> files = ((MultipartHttpServletRequest)request).getFiles("fileList");
        if(files.isEmpty()){
            log.error("文件为空");
            return "文件为空，请重新上传";
        }
        for(MultipartFile file:files){
            log.info("图片上传：{}", JSON.toJSONString(file,true));
            String fileName = file.getOriginalFilename();
            if(file.isEmpty()){
                log.error("文件为空");
                return "文件为空，请重新上传";
            }else{
                File dest = new File(UPLOAD_FOLDER + "/" + fileName);
                //判断文件父目录是否存在
                if(!dest.getParentFile().exists()){
                    dest.getParentFile().mkdir();
                }
                try {
                    file.transferTo(dest);
                }catch (Exception e) {
                    e.printStackTrace();
                    return "文件传输异常";
                }
            }
        }
        log.info("文件写入成功");
        return "文件上传成功";
    }
}