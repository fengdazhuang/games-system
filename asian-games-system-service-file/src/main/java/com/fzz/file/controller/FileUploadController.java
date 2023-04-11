package com.fzz.file.controller;

import com.fzz.api.BaseController;
import com.fzz.api.controller.file.FileUploadControllerApi;
import com.fzz.common.result.ReturnResult;
import com.fzz.file.FileResource;
import com.fzz.file.service.FileUploadService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class FileUploadController extends BaseController implements FileUploadControllerApi {

    @Autowired
    private FileUploadService fileUploadService;

    @Autowired
    private FileResource fileResource;

    @Override
    public ReturnResult uploadFiles(MultipartFile[] files) throws IOException {
        List<String> filePathList = new ArrayList<>();
        if (files != null && files.length > 0) {
            for (MultipartFile file : files) {
                if (file != null) {
                    String fileName = file.getOriginalFilename();
                    if (StringUtils.isNotBlank(fileName)) {
                        String fileSplit[] = fileName.split("\\.");
                        String suffix = fileSplit[fileSplit.length - 1];
                        if (!suffix.equalsIgnoreCase("png") && !suffix.equalsIgnoreCase("jpg")
                                && !suffix.equalsIgnoreCase("jpeg")) {
                            continue;
                        }
                        String filePath = fileUploadService.uploadFileToFdfs(file, suffix);
                        if (StringUtils.isNotBlank(filePath)) {
                            filePathList.add(fileResource.getHost() + filePath);
                        }
                    }
                }
            }
        }
        return ReturnResult.ok(filePathList);
    }
}