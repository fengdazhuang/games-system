package com.fzz.personnel.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fzz.api.BaseController;
import com.fzz.api.controller.personnal.PlayerControllerApi;
import com.fzz.common.enums.ResponseStatusEnum;
import com.fzz.common.result.ReturnResult;
import com.fzz.common.utils.BaiduFaceUtil;
import com.fzz.common.utils.JsonUtils;
import com.fzz.common.utils.RedisUtil;
import com.fzz.common.utils.SnowFlakeUtil;
import com.fzz.model.bo.AddPlayerBO;
import com.fzz.model.entity.Player;
import com.fzz.model.vo.QueryPlayerVO;
import com.fzz.personnel.service.PlayerService;
import com.mongodb.client.gridfs.GridFSBucket;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@RestController
public class PlayerController extends BaseController implements PlayerControllerApi {

    @Autowired
    private PlayerService playerService;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private RestTemplate restTemplate;


    @Override
    public ReturnResult listPlayers(Integer pageNumber, Integer pageSize,
                                    String competitionName, String name,
                                    String country,Integer arrivalStatus,
                                    Integer healthyStatus) {
        if(pageNumber<=0){
            pageNumber=COMMON_START_PAGE;
        }
        if(pageSize<=0){
            pageSize=COMMON_PAGE_SIZE;
        }
        Page<QueryPlayerVO> playerPage=playerService.pagePlayers(pageNumber, pageSize,
                competitionName, name, country, arrivalStatus, healthyStatus);
        List<QueryPlayerVO> records = playerPage.getRecords();

        Set<String> set = JsonUtils.jsonToSet(redisUtil.get(REDIS_PLAYER_MONGO_IDS), String.class);
        List<String> base64List = getBase64ListByMongoId(set);

        int i=0;
        for(QueryPlayerVO queryPlayerVO:records){
            queryPlayerVO.setPhoto(base64List.get(i++));
        }
        redisUtil.del(REDIS_PLAYER_MONGO_IDS);
        playerPage.setRecords(records);
        return ReturnResult.ok(playerPage);
    }


    private List<String> getBase64ListByMongoId(Set<String> set){
        String url="http://localhost:8009/api9/file/readInGridFS?set="+JsonUtils.objectToJson(set);
        ResponseEntity<ReturnResult> entity = restTemplate.getForEntity(url, ReturnResult.class);
        ReturnResult body = entity.getBody();
        List<String> base64List = null;
        if(body.getCode()==200){
            String json = JsonUtils.objectToJson(body.getData());
            base64List=JsonUtils.jsonToList(json,String.class);
        }
        return base64List;
    }

    @Override
    public ReturnResult addPlayer(AddPlayerBO addPlayerBO) {
        SnowFlakeUtil snowFlakeUtil = new SnowFlakeUtil (12,13);
        Long snowFlakeId  = snowFlakeUtil.getNextId();
        addPlayerBO.setId(snowFlakeId);

        String base64 = addPlayerBO.getPhoto();
        String url="http://localhost:8009/api9/file/uploadToGridFS";
        Map<String,Object> request = new HashMap<>();
        request.put("id",String.valueOf(snowFlakeId));
        request.put("base64",base64);
        ResponseEntity<String> entity = restTemplate.postForEntity(url, request, String.class);
        String mongoId = entity.getBody();
        addPlayerBO.setMongoId(mongoId);

        boolean res = playerService.savePlayer(addPlayerBO);
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
