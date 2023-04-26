package com.fzz.api.controller.personnal;

import com.fzz.common.result.ReturnResult;
import com.fzz.model.bo.AddJudgeBO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/api1/judge")
@Api(value = "judgeController")
public interface JudgeControllerApi {

    @GetMapping("/listJudges")
    @ApiOperation(value="根据条件分页查询裁判")
    ReturnResult listJudges(@RequestParam Integer pageNumber,
                            @RequestParam Integer pageSize,
                            @RequestParam String competitionName,
                            @RequestParam String name,
                            @RequestParam String country,
                            @RequestParam Integer arrivalStatus,
                            @RequestParam Integer healthyStatus);

    @PostMapping("/addJudge")
    @ApiOperation(value = "添加裁判")
    ReturnResult addJudge(@Valid @RequestBody AddJudgeBO addJudge);

    @PutMapping("/updateJudge")
    @ApiOperation(value = "修改裁判")
    ReturnResult updateJudge(@Valid @RequestBody AddJudgeBO addJudge);

    @GetMapping("/queryJudge")
    @ApiOperation(value = "查找裁判")
    ReturnResult queryJudge(@RequestParam Long id);

    @DeleteMapping("/deleteJudge")
    @ApiOperation(value = "删除裁判")
    ReturnResult deleteJudge(@RequestParam Long[] id);
}
