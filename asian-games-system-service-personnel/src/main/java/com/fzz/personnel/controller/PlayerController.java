package com.fzz.personnel.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fzz.api.BaseController;
import com.fzz.api.controller.personnal.PlayerControllerApi;
import com.fzz.common.enums.ResponseStatusEnum;
import com.fzz.common.result.ReturnResult;
import com.fzz.common.utils.JsonUtils;
import com.fzz.common.utils.RedisUtil;
import com.fzz.model.bo.AddPlayerBO;
import com.fzz.model.entity.Player;
import com.fzz.model.vo.QueryPlayerVO;
import com.fzz.personnel.service.PlayerService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PlayerController extends BaseController implements PlayerControllerApi {

    @Autowired
    private PlayerService playerService;

    @Autowired
    private RedisUtil redisUtil;


//    @Override
//    public ReturnResult queryRatioByCountry() {
//        List<QueryCountryVO> queryCountryVOList = playerService.getRatioByCountry();

//        return ReturnResult.ok(queryCountryVOList);
//    }


    @Override
    public ReturnResult listPlayers(Integer pageNumber, Integer pageSize, String competitionName, String name, String country) {
        if(pageNumber<=0){
            pageNumber=COMMON_START_PAGE;
        }
        if(pageSize<=0){
            pageSize=COMMON_PAGE_SIZE;
        }
        Page<QueryPlayerVO> playerPage=playerService.pagePlayers(pageNumber, pageSize, competitionName, name, country);
        return ReturnResult.ok(playerPage);
    }

    @Override
    public ReturnResult addPlayer(AddPlayerBO addPlayer) {
        boolean res = playerService.savePlayer(addPlayer);
        if(res){
            return ReturnResult.ok();
        }
        return ReturnResult.error(ResponseStatusEnum.PLAYER_CREATE_ERROR);
    }

    @Override
    public ReturnResult deletePlayer(Long[] id) {
        boolean res = playerService.removePlayers(id);
        if(res){
            for(Long playerId:id){
                redisUtil.del(REDIS_PLAYER_INFO + ":" + playerId);
            }
            return ReturnResult.ok();
        }
        return ReturnResult.error(ResponseStatusEnum.PLAYER_DELETE_ERROR);
    }

    @Override
    public ReturnResult updatePlayer(AddPlayerBO addPlayer) {
        boolean res = playerService.updatePlayerById(addPlayer);
        if(res){
            redisUtil.del(REDIS_PLAYER_INFO + ":" + addPlayer.getId());
            return ReturnResult.ok();
        }

        return ReturnResult.error(ResponseStatusEnum.PLAYER_MODIFY_ERROR);
    }

    @Override
    public ReturnResult queryPlayer(Long id) {
        String playerStr = redisUtil.get(REDIS_PLAYER_INFO + ":" + id);
        if(StringUtils.isNotBlank(playerStr)){
            Player player = JsonUtils.jsonToPojo(playerStr, Player.class);
            redisUtil.set(REDIS_PLAYER_INFO + ":" + id,JsonUtils.objectToJson(player));
            return ReturnResult.ok(player);
        }
        return ReturnResult.error(ResponseStatusEnum.PLAYER_NOT_EXISTS);
    }


}
