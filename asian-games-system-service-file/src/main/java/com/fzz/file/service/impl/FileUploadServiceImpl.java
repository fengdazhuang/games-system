package com.fzz.file.service.impl;

import com.fzz.file.service.FileUploadService;
import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class FileUploadServiceImpl implements FileUploadService {

    @Autowired
    private FastFileStorageClient fastFileStorageClient;

    @Override
    public String uploadFileToFdfs(MultipartFile file,String suffix) throws IOException {
        StorePath storePath = fastFileStorageClient.uploadFile(file.getInputStream(), file.getSize(), suffix, null);
        return storePath.getFullPath();
    }
}
