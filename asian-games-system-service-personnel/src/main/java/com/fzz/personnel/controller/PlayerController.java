package com.fzz.personnel.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fzz.api.BaseController;
import com.fzz.api.controller.personnal.PlayerControllerApi;
import com.fzz.common.result.ReturnResult;
import com.fzz.model.bo.ListPlayersBO;
import com.fzz.model.entity.Player;
import com.fzz.model.vo.QueryCountryVO;
import com.fzz.model.vo.QueryPlayerVO;
import com.fzz.personnel.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PlayerController extends BaseController implements PlayerControllerApi {

    @Autowired
    private PlayerService playerService;


    @Override
    public ReturnResult queryRatioByCountry() {
        List<QueryCountryVO> queryCountryVOList = playerService.getRatioByCountry();
        return ReturnResult.ok(queryCountryVOList);
    }

    @Override
    public ReturnResult listPlayers(ListPlayersBO listPlayersBO) {
        Integer pageNumber = listPlayersBO.getPageNumber();
        Integer pageSize = listPlayersBO.getPageSize();
        if(pageNumber<=0){
            listPlayersBO.setPageNumber(COMMON_START_PAGE);
        }
        if(pageSize<=0){
            listPlayersBO.setPageSize(COMMON_PAGE_SIZE);
        }
        Page<QueryPlayerVO> playerPage=playerService.pagePlayers(listPlayersBO);
        return ReturnResult.ok(playerPage);
    }




}
