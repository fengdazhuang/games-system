package com.fzz.api.controller.medical;


import com.fzz.common.result.ReturnResult;
import com.fzz.model.bo.AddDopTestBO;
import com.fzz.model.bo.SubmitDopResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RequestMapping("/api7/DopTest")
@Api(value = "兴奋剂检测的api")
public interface DopTestControllerApi {

    @GetMapping("/pageResult")
    @ApiOperation(value = "按条件分页查找兴奋剂检测数据")
    ReturnResult pageResult(@RequestParam Integer pageNumber,
                            @RequestParam Integer pageSize,
                            @RequestParam Integer examinationType,
                            @RequestParam Integer examinationResult,
                            @RequestParam Date examinationTime,
                            @RequestParam String country,
                            @RequestParam String name
                            );


    @GetMapping("/pageExamination")
    @ApiOperation(value = "按条件分页查找兴奋剂检测待检测数据")
    ReturnResult pageExamination(@RequestParam Integer pageNumber,
                                 @RequestParam Integer pageSize,
                                 @RequestParam String sampleNumber
    );

    @PostMapping("/addDopTest")
    @ApiOperation(value = "兴奋剂检测登记")
    ReturnResult addDopTest(@RequestBody AddDopTestBO addDopTestBO);

    @PutMapping("/submitResult")
    @ApiOperation(value = "批量提交兴奋剂检测结果")
    ReturnResult submitResult(@RequestBody List<SubmitDopResult> submitDopResultList);
}
