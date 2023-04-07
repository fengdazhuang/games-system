package com.fzz.api.controller.personnal;

import com.fzz.common.result.ReturnResult;
import com.fzz.model.bo.ListPlayersBO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@RequestMapping("/player")
@Api(value = "PlayerController")
public interface PlayerControllerApi {

    @GetMapping("/queryRatioByCountry")
    ReturnResult queryRatioByCountry();;

    @GetMapping("/listPlayers")
    @ApiOperation(value = "根据条件分页查询运动员")
    ReturnResult listPlayers(@Valid @RequestBody ListPlayersBO listPlayersBO);



}
