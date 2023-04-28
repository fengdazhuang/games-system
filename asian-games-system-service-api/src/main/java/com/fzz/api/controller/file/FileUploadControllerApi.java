package com.fzz.api.controller.file;

import com.fzz.common.result.ReturnResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RequestMapping("/api9/file")
public interface FileUploadControllerApi {

    @PostMapping("/uploadFiles")
    ReturnResult uploadFiles(MultipartFile[] files) throws IOException;

    @PostMapping("/uploadToGridFS")
    String uploadToGridFS(@RequestBody Map<String,Object> map) throws IOException;

    @GetMapping("/readInGridFS")
    ReturnResult readInGridFS(@RequestParam String set) throws Exception;


}
