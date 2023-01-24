package com.fpt.edu.herofundbackend.controller;


import com.fpt.edu.herofundbackend.dto.CommonResponse;
import com.fpt.edu.herofundbackend.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/v1/files")
public class FileController {

    private final FileService fileService;

    @RequestMapping(path = "upload", method = RequestMethod.POST)
    public CommonResponse upload(@RequestParam("file")MultipartFile file){
        return fileService.upload(file);
    }

}
