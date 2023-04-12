package com.fzz.api.controller.competition;

import com.fzz.common.result.ReturnResult;
import com.fzz.model.bo.AddCompetitionCategoryBO;
import com.fzz.model.bo.AddCompetitionInfoBO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/api4/competition")
@Api(value = "CompetitionController")
public interface CompetitionControllerApi {

    @GetMapping("/getComCategorys")
    @ApiOperation(value = "查询所有运动项目类型")
    ReturnResult queryComCategorys();

//    @GetMapping("/getComNamesByComCategoryId")
//    @ApiOperation(value = "查询所有运动项目类型")
//    ReturnResult queryComNames(@RequestParam Integer competitionCategoryId);

    @GetMapping("/getComInfos")
    @ApiOperation(value = "查询所有运动项目")
    ReturnResult queryComNames();

    @PostMapping("/addComCategory")
    @ApiOperation(value = "添加运动项目类型")
    ReturnResult addComCategory(@Valid @RequestBody AddCompetitionCategoryBO addCompetitionCategory);

    @PostMapping("/addComInfo")
    @ApiOperation(value = "添加具体运动项目")
    ReturnResult addComInfo(@Valid @RequestBody AddCompetitionInfoBO addCompetitionInfo);

}
