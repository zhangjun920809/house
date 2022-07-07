package com.zufang.common.util.minio;

import cn.hutool.core.lang.ObjectId;
import cn.hutool.core.text.StrPool;
import com.zufang.common.util.minio.config.MinioConfig;
import io.minio.*;
import io.minio.http.Method;
import io.minio.messages.Bucket;
import io.minio.messages.DeleteError;
import io.minio.messages.DeleteObject;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Component

public class MinioUtil {
    public static Log log = LogFactory.getLog(MinioUtil.class);

    @Autowired
    MinioConfig minioConfig;
    private static MinioClient minioClient;

    /**
     * 初始化minio配置
     *
     * @param :
     * @return: void
     * @date :
     */

    @PostConstruct
    public void init() {
        try {
            minioClient =
                    MinioClient.builder()
                            .endpoint(minioConfig.getEndpoint())
                            .credentials(minioConfig.getAccessKey(), minioConfig.getSecretKey())
                            .build();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("minioncliet init error: ", e.fillInStackTrace());
        }
    }

    /**
     * 判断 bucket是否存在
     *
     * @param bucketName: 文件桶名
     * @return: boolean
     * @date :
     */
    @SneakyThrows(Exception.class)
    public static boolean bucketExists(String bucketName) {
        return minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
    }

    /**
     * 创建 bucket
     *
     * @param bucketName: 文件桶名
     * @return: void
     * @date :
     */
    @SneakyThrows(Exception.class)
    public static void createBucket(String bucketName) {
        boolean isExist = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
        if (!isExist) {
            minioClient.makeBucket(MakeBucketArgs.builder()
                    .bucket(bucketName)
                    .build());
        }
    }

    /**
     * 获取全部bucket
     *
     * @param :
     * @return: java.util.List<io.minio.messages.Bucket>
     * @date :
     */
    @SneakyThrows(Exception.class)
    public static List<Bucket> getAllBuckets() {
        return minioClient.listBuckets();
    }


    /**
     * 将给定流作为对象上传到bucket中 （视频）
     *
     * @param bucketName: 文件桶名
     * @param objectName:   文件名
     * @param inputStream:     文件流
     * @return: java.lang.String : 文件url地址
     * @date :
     */
    @SneakyThrows(Exception.class)
    public static String uploadStream(String bucketName, String objectName, InputStream inputStream) {
        ObjectWriteResponse response = minioClient.putObject(PutObjectArgs.builder().bucket(bucketName).object(objectName).stream(
                inputStream, inputStream.available(), -1)
                .contentType("video/mp4")
                .build());
        return getPresignedObjectUrl(bucketName, objectName);
    }

    /**
     * 文件上传
     *
     * @param bucketName: 文件桶名
     * @param objectName:       文件
     * @param fileName:       文件路径
     * @date :
     */
    @SneakyThrows(Exception.class)
    public static String uploadFile(String bucketName, String  objectName,String fileName) {
        ObjectWriteResponse objectWriteResponse = minioClient.uploadObject(UploadObjectArgs.builder()
                .bucket(bucketName).object(objectName).filename(fileName).build());
        return getPresignedObjectUrl(bucketName, objectName);
    }

    /**
     * 文件上传
     *
     * @param bucketName
     * @param multipartFile
     */
    @SneakyThrows
    public static String upload(String bucketName, MultipartFile multipartFile) {

        String objectName = ObjectId.next();

        ByteArrayInputStream inputStream = new ByteArrayInputStream(multipartFile.getBytes());
        minioClient.putObject(PutObjectArgs.builder().bucket(bucketName)
                .object(objectName).stream(inputStream,
                        inputStream.available(), -1)
                .contentType(FileTypeUtils.getFileType(multipartFile)).build());


        return getPresignedObjectUrl(bucketName, objectName);

    }

    /**  上传视频
     *
     * @param bucketName
     * @param objectName
     * @param fileName
     * @return
     */
    @SneakyThrows(Exception.class)
    public static void uploadVideo(String bucketName, String  objectName,String fileName,String contentType) {
        minioClient.uploadObject(UploadObjectArgs.builder()
                .bucket(bucketName).object(objectName).filename(fileName).contentType(contentType).build());
    }


    /**
     *  上传多个对象
     * @param jsonArray
     * @param bucketName
     */
    @SneakyThrows(Exception.class)
    public static void uploadMore(JSONArray jsonArray, String  bucketName) {
        List<SnowballObject> objects = new ArrayList<SnowballObject>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            objects.add(
                    new SnowballObject(
                            jsonObject.getString("objectName"),
                            jsonObject.getString("fileName")));
            objects.add(
                    new SnowballObject(
                            jsonObject.getString("objectName"),
                            new ByteArrayInputStream(jsonObject.getString("content").getBytes(StandardCharsets.UTF_8)),
                            jsonObject.getString("content").length(),
                            null));
        }

        minioClient.uploadSnowballObjects(
                UploadSnowballObjectsArgs.builder().bucket(bucketName).objects(objects).build());
    }

    /**
     * 删除文件
     *
     * @param bucketName: 文件桶名
     * @param objectName:   对象名称
     * @return: void
     * @date :
     */
    @SneakyThrows(Exception.class)
    public static void deleteFile(String bucketName, String objectName) {
        minioClient.removeObject(RemoveObjectArgs.builder().bucket(bucketName).object(objectName).build());
    }

    /**
     *  批量删除
     *  删除指定桶的多个文件对象,返回删除错误的对象列表，全部删除成功，返回空列表
     * @param objectNames
     * @param bucketName
     */
    @SneakyThrows(Exception.class)
    public static List<String>  deleteFileBatch(List<String> objectNames, String bucketName) {
        List<DeleteObject> objects = new LinkedList<>();
        for(String objName:objectNames){
            objects.add(new DeleteObject(objName));
        }

        Iterable<Result<DeleteError>> results =
                minioClient.removeObjects(
                        RemoveObjectsArgs.builder().bucket(bucketName).objects(objects).build());

        ArrayList<String> resultList = new ArrayList<>();
        for (Result<DeleteError> result : results) {
            DeleteError error = result.get();
            resultList.add(error.objectName());
        }
        return resultList;
    }

    /**
     * 获取minio文件的访问地址
     *
     * @param bucketName: 桶名
     * @param objectName:   文件名
     * @return: java.lang.String
     * @date :
     */
    @SneakyThrows(Exception.class)
    public static String getPresignedObjectUrl(String bucketName, String objectName) {
        Map<String, String> reqParams = new HashMap<String, String>();
        reqParams.put("response-content-type", "application/json");

        String url =
                minioClient.getPresignedObjectUrl(
                        GetPresignedObjectUrlArgs.builder()
                                .method(Method.GET)
                                .bucket(bucketName)
                                .object(objectName)
                                .expiry(2, TimeUnit.HOURS)
                                .build());

        return url;
    }

    /**
     *  获取对象的信息和元数据。
     * @param bucketName
     * @param objectName
     */
    @SneakyThrows(Exception.class)
    public static void statObject(String bucketName, String objectName){

        StatObjectResponse statObjectResponse = minioClient.statObject(
                StatObjectArgs.builder().bucket(bucketName).object(objectName).build());
        System.out.println(statObjectResponse.toString());
    }

    /**
     * 以流的形式获取一个文件对象
     *
     * @param bucketName 存储桶名称
     * @param objectName 存储桶里的对象名称
     * @return
     */
    @SneakyThrows
    public InputStream getObject(String bucketName, String objectName) {

        if (bucketExists(bucketName)) {
            return  minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .build());

        }
        return null;
    }

    /**
     * 以流的形式获取一个文件对象（断点下载）
     *
     * @param bucketName 存储桶名称
     * @param objectName 存储桶里的对象名称
     * @param offset     起始字节的位置
     * @param length     要读取的长度 (可选，如果无值则代表读到文件结尾)
     * @return
     */
    @SneakyThrows
    public InputStream getObject(String bucketName, String objectName, long offset, Long length) {

        if (bucketExists(bucketName)) {

                return minioClient.getObject(GetObjectArgs.builder().bucket(bucketName).object(objectName).offset(offset).length(length).build());
        }
        return null;

    }
}
