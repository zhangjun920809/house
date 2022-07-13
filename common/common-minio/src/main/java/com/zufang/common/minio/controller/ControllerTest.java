package com.zufang.common.minio.controller;

import com.zufang.common.minio.utils.MinioUtil;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
public class ControllerTest {

    /**
     *  文件视频已测试
     * @param multipartFile
     * @return
     */
    @PostMapping("/uploadFile")
    public Map upload(@RequestParam MultipartFile multipartFile, @RequestParam String buketName) throws Exception{
        HashMap<Object, Object> map = new HashMap<>();
        map.put("data",MinioUtil.uploadFile(buketName,multipartFile));
        return map;
    }


}
