package com.zufang.common.util;

import com.zufang.common.util.minio.MinioUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class ControllerTest {

    @RequestMapping("/uploadFile")
    public String upload(MultipartFile multipartFile){
        String asiatrip = MinioUtil.upload("asiatrip", multipartFile);
        System.out.println(asiatrip);
        return "sss";
    }
}
