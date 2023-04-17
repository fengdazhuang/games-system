package com.fzz.api.controller.competition;

import com.fzz.common.result.ReturnResult;
import com.fzz.model.bo.AddComCategoryBO;
import com.fzz.model.bo.AddComInfoBO;
import com.fzz.model.bo.AddComPositionBO;
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

    @GetMapping("/getComPositions")
    @ApiOperation(value = "查询比赛场地")
    ReturnResult queryComPositions(@RequestParam String area,@RequestParam String keyword);

    @GetMapping("/getComAreas")
    @ApiOperation(value = "查询比赛赛区")
    ReturnResult queryComAreas();

    @DeleteMapping("/deleteComPosition")
    @ApiOperation(value = "删除比赛场地")
    ReturnResult deleteComPosition(@RequestParam Integer id);

    @PostMapping("/addComCategory")
    @ApiOperation(value = "添加运动项目类型")
    ReturnResult addComCategory(@Valid @RequestBody AddComCategoryBO addComCategoryBO);

    @PostMapping("/addComInfo")
    @ApiOperation(value = "添加具体运动项目")
    ReturnResult addComInfo(@Valid @RequestBody AddComInfoBO addComInfoBO);

    @PostMapping("/addComArea")
    @ApiOperation(value = "添加比赛赛区")
    ReturnResult addComArea(@RequestBody String comArea);

    @PostMapping("/addComPosition")
    @ApiOperation(value = "添加具体比赛场所")
    ReturnResult addComPosition(@Valid @RequestBody AddComPositionBO addComPositionBO);

}
