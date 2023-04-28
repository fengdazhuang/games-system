package com.fzz.personnel.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fzz.api.BaseController;
import com.fzz.api.controller.personnal.JudgeControllerApi;
import com.fzz.common.enums.ResponseStatusEnum;
import com.fzz.common.result.ReturnResult;
import com.fzz.common.utils.JsonUtils;
import com.fzz.common.utils.RedisUtil;
import com.fzz.common.utils.SnowFlakeUtil;
import com.fzz.model.bo.AddJudgeBO;
import com.fzz.model.entity.Judge;
import com.fzz.model.vo.QueryJudgeVO;
import com.fzz.personnel.service.JudgeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
public class JudgeController extends BaseController implements JudgeControllerApi {


    @Autowired
    private JudgeService judgeService;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private RestTemplate restTemplate;


    @Override
    public ReturnResult listJudges(Integer pageNumber, Integer pageSize, String competitionName,
                                   String name, String country, Integer arrivalStatus, Integer healthyStatus) {
        if(pageNumber<=0){
            pageNumber=COMMON_START_PAGE;
        }
        if(pageSize<=0){
            pageSize=COMMON_PAGE_SIZE;
        }
        Page<QueryJudgeVO> queryJudgeVOPage=judgeService.pageJudges(pageNumber, pageSize,
                competitionName, name, country,arrivalStatus,healthyStatus);
        List<QueryJudgeVO> records = queryJudgeVOPage.getRecords();

        Set<String> set = JsonUtils.jsonToSet(redisUtil.get(REDIS_JUDGE_MONGO_IDS), String.class);
        List<String> base64List = getBase64ListByMongoId(set);

        int i=0;
        for(QueryJudgeVO queryJudgeVO:records){
            queryJudgeVO.setPhoto(base64List.get(i++));
        }
        redisUtil.del(REDIS_JUDGE_MONGO_IDS);
        queryJudgeVOPage.setRecords(records);
        return ReturnResult.ok(queryJudgeVOPage);
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
    public ReturnResult addJudge(AddJudgeBO addJudgeBO) {
        SnowFlakeUtil snowFlakeUtil = new SnowFlakeUtil(12,13);
        Long snowFlakeId  = snowFlakeUtil.getNextId();
        addJudgeBO.setId(snowFlakeId);

        String base64 = addJudgeBO.getPhoto();
        String url="http://localhost:8009/api9/file/uploadToGridFS";
        Map<String,Object> request = new HashMap<>();
        request.put("id",String.valueOf(snowFlakeId));
        request.put("base64",base64);
        ResponseEntity<String> entity = restTemplate.postForEntity(url, request, String.class);
        String mongoId = entity.getBody();
        addJudgeBO.setMongoId(mongoId);

        boolean res = judgeService.saveJudge(addJudgeBO);
        if(res){
            return ReturnResult.ok();
        }
        return ReturnResult.error(ResponseStatusEnum.JUDGE_CREATE_ERROR);
    }

    @Override
    public ReturnResult updateJudge(AddJudgeBO addJudge) {
        boolean res = judgeService.updatePlayerById(addJudge);
        if(res){
            redisUtil.del(REDIS_JUDGE_INFO + ":" + addJudge.getId());
            return ReturnResult.ok();
        }

        return ReturnResult.error(ResponseStatusEnum.JUDGE_MODIFY_ERROR);
    }

    @Override
    public ReturnResult queryJudge(Long id) {
        String judgeStr = redisUtil.get(REDIS_JUDGE_INFO + ":" + id);
        if(StringUtils.isNotBlank(judgeStr)){
            Judge judge = JsonUtils.jsonToPojo(judgeStr, Judge.class);
            redisUtil.set(REDIS_JUDGE_INFO + ":" + id,JsonUtils.objectToJson(judge));
            return ReturnResult.ok(judge);
        }
        return ReturnResult.error(ResponseStatusEnum.JUDGE_NOT_EXISTS);
    }

    @Override
    public ReturnResult deleteJudge(Long[] id) {
        boolean res = judgeService.removeJudges(id);
        if(res){
            for(Long judgeId:id){
                redisUtil.del(REDIS_JUDGE_INFO + ":" + judgeId);
            }
            return ReturnResult.ok();
        }
        return ReturnResult.error(ResponseStatusEnum.JUDGE_DELETE_ERROR);
    }
}
