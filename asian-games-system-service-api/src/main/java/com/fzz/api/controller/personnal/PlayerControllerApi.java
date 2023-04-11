package com.fzz.api.controller.personnal;

import com.fzz.common.result.ReturnResult;
import com.fzz.model.bo.AddPlayer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/api1/player")
@Api(value = "PlayerController")
public interface PlayerControllerApi {

//    @GetMapping("/queryRatioByCountry")
//    ReturnResult queryRatioByCountry();

    @GetMapping("/listPlayers")
    @ApiOperation(value = "根据条件分页查询运动员")
    ReturnResult listPlayers(@RequestParam Integer pageNumber,
                             @RequestParam Integer pageSize,
                             @RequestParam String competitionName,
                             @RequestParam String name,
                             @RequestParam String country);

    @PostMapping("/addPlayer")
    @ApiOperation(value = "添加运动员")
    ReturnResult addPlayer(@Valid @RequestBody AddPlayer addPlayer);

    @DeleteMapping("/deletePlayer")
    @ApiOperation(value = "删除运动员")
    ReturnResult deletePlayer(@RequestParam Long id);

    @PutMapping("/updatePlayer")
    @ApiOperation(value = "修改运动员")
    ReturnResult updatePlayer(@Valid @RequestBody AddPlayer addPlayer);

    @GetMapping("/queryPlayer")
    @ApiOperation(value = "查找运动员")
    ReturnResult queryPlayer(@RequestParam Long id);




}
