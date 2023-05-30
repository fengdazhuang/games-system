package com.fzz.api.controller.personnal;

import com.fzz.common.result.ReturnResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RequestMapping("/api1")
public interface ExcelControllerApi {


    @PostMapping("/export/player")
    ReturnResult excelExport(@RequestBody Map<String,Object> map);

    @PostMapping("/import/player")
    ReturnResult excelImport(@RequestBody MultipartFile multipartFile);
}
