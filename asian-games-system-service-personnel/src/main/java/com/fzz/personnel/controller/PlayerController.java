package com.fzz.personnel.controller;

import com.fzz.api.controller.personnal.PlayerControllerApi;
import com.fzz.common.result.ReturnResult;
import com.fzz.model.vo.QueryCountryVO;
import com.fzz.personnel.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PlayerController implements PlayerControllerApi {

    @Autowired
    private PlayerService playerService;


    @Override
    public ReturnResult queryRatioByCountry() {
        List<QueryCountryVO> queryCountryVOList = playerService.getRatioByCountry();
        return ReturnResult.ok(queryCountryVOList);
    }
}
