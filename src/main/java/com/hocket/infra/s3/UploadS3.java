package com.hocket.infra.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class UploadS3 {
    @Value("${aws.s3.image.bucket}")
    private String bucket;

    @Value("${cloud.aws.region.static}")
    private String region;

    private  final AmazonS3 amazonS3;

    public String uploadImageToS3(MultipartFile multipartFile, String dirName, String hocketId){

        File file = convertMultipartFileToFile(multipartFile);

        StringBuilder fileName = new StringBuilder();
        fileName.append(dirName).append("/").append(hocketId).append("_").append(UUID.randomUUID().toString());

        amazonS3.putObject(new PutObjectRequest(bucket, fileName.toString(), file)
                .withCannedAcl(CannedAccessControlList.PublicRead));

        removeFile(file);

        return "https://s3." + region + ".amazonaws.com/" + bucket + fileName.toString();
    }


    private File convertMultipartFileToFile(MultipartFile multipartFile) {

        String rootPath = System.getProperty("user.dir");

        try{
            multipartFile.transferTo(new File(rootPath +"/"+ multipartFile.getOriginalFilename()));
        }catch (IOException e){
           log.error(e.getMessage());
        }
        File file = new File(multipartFile.getOriginalFilename());

        return file;
    }
    private void removeFile(File file) {
        if(file != null){
            file.delete();
        }
    }

    //
}
