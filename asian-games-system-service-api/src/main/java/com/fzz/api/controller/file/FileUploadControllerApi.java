package com.fzz.api.controller.file;

import com.fzz.common.result.ReturnResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequestMapping("/file")
public interface FileUploadControllerApi {

    ReturnResult uploadFiles(Integer competitionNameId, MultipartFile[] files) throws IOException;

}
