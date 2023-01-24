package com.fpt.edu.herofundbackend.service;

import com.cloudinary.Cloudinary;
import com.fpt.edu.herofundbackend.config.CloudinaryProperties;
import com.fpt.edu.herofundbackend.constant.SystemConstant;
import com.fpt.edu.herofundbackend.dto.CommonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Log4j2
public class FileService {


    private final CloudinaryProperties cloudinaryProperties;
    private final static String URL = "url";
    private final static String FOLDER = "folder";

    public CommonResponse upload(MultipartFile file) {
        CommonResponse commonResponse = new CommonResponse();
        Cloudinary cloudinary = new Cloudinary(cloudinaryProperties.getUrl());
        try {
            Map<String,String> map = new HashMap<>();
            map.put(FOLDER, cloudinaryProperties.getFolder());
            cloudinary.uploader().buildUploadParams(map);
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(), map);
            String publicId = uploadResult.get(URL).toString();
            commonResponse.setSuccessWithDataResponse(publicId);
            return commonResponse;
        } catch (Exception ex) {
            log.error(ex.getMessage());
            commonResponse.setFailResponse(SystemConstant.Message.FAIL_UPLOAD);
            return commonResponse;
        }
    }
}
