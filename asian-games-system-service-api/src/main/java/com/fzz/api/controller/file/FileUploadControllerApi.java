package com.fzz.api.controller.file;

import com.fzz.common.result.ReturnResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequestMapping("/api9/file")
public interface FileUploadControllerApi {

    @PostMapping("/uploadFiles")
    ReturnResult uploadFiles(MultipartFile[] files) throws IOException;

}
