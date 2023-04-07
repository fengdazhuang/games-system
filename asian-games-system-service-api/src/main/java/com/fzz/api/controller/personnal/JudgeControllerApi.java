package com.fzz.api.controller.personnal;

import com.fzz.common.result.ReturnResult;
import com.fzz.model.bo.ListJudgesBO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@RequestMapping("/judge")
@Api(value = "judgeController")
public interface JudgeControllerApi {

    @RequestMapping("/listJudges")
    @ApiOperation(value="根据条件分页查询裁判")
    ReturnResult listJudges(@Valid @RequestBody ListJudgesBO listJudgeBO);
}
