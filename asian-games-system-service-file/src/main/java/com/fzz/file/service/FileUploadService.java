package com.fzz.file.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileUploadService {

    String uploadFileToFdfs(MultipartFile file,String suffix) throws IOException;
}
