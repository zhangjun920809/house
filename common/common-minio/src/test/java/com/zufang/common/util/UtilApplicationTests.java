package com.zufang.common.util;

import com.zufang.common.minio.utils.MinioUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UtilApplicationTests {

    @Test
    void contextLoads() {
        System.out.println("------");
        String asiatrip = MinioUtil.getPresignedObjectUrl("asiatrip", "audit.txt");
        System.out.println(""+asiatrip);
    }

    @Test
    void uploadFile(){
        MinioUtil.uploadFile("asiatrip","上传图片.jpg","C:\\Users\\User\\Desktop\\图片.jpg");
    }

    @Test
    void statObject(){
        MinioUtil.statObject("asiatrip","上传图片.jpg");
    }



}
