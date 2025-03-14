package com.fzz.api.controller.personnal;

import com.fzz.common.result.ReturnResult;
import com.fzz.model.bo.AddPlayerBO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Map;

@RequestMapping("/api1/player")
@Api(value = "PlayerController")
public interface PlayerControllerApi {


    @GetMapping("/listPlayers")
    @ApiOperation(value = "根据条件分页查询运动员")
    ReturnResult listPlayers(@RequestParam Integer pageNumber,
                             @RequestParam Integer pageSize,
                             @RequestParam String competitionName,
                             @RequestParam String name,
                             @RequestParam String country,
                             @RequestParam Integer arrivalStatus,
                             @RequestParam Integer healthyStatus);

    @PostMapping("/addPlayer")
    @ApiOperation(value = "添加运动员")
    ReturnResult addPlayer(@Valid @RequestBody AddPlayerBO addPlayer) throws IOException;

    @DeleteMapping("/deletePlayer")
    @ApiOperation(value = "删除运动员")
    ReturnResult deletePlayer(@RequestParam Long[] id) throws IOException;

    @PutMapping("/updatePlayer")
    @ApiOperation(value = "修改运动员")
    ReturnResult updatePlayer(@Valid @RequestBody AddPlayerBO addPlayer);

    @GetMapping("/queryPlayer")
    @ApiOperation(value = "查找运动员")
    ReturnResult queryPlayer(@RequestParam Long id);

    @PostMapping("/faceSearch")
    @ApiOperation(value = "搜索人脸")
    ReturnResult faceSearch(@RequestBody Map<String,Object> map);




}
