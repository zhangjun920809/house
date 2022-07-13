package com.zufang.common.util;

import com.zufang.common.util.minio.FileTypeUtils;
import com.zufang.common.util.minio.MinioUtil;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.ClassUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Spliterators;
import java.util.UUID;

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
