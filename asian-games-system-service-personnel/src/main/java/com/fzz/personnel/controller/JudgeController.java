package com.fzz.personnel.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fzz.api.BaseController;
import com.fzz.api.controller.personnal.JudgeControllerApi;
import com.fzz.common.enums.ResponseStatusEnum;
import com.fzz.common.result.ReturnResult;
import com.fzz.common.utils.JsonUtils;
import com.fzz.common.utils.RedisUtil;
import com.fzz.model.bo.AddJudge;
import com.fzz.model.entity.Judge;
import com.fzz.model.vo.QueryJudgeVO;
import com.fzz.personnel.service.JudgeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JudgeController extends BaseController implements JudgeControllerApi {

    @Autowired
    private JudgeService judgeService;

    @Autowired
    private RedisUtil redisUtil;


    @Override
    public ReturnResult listJudges(Integer pageNumber, Integer pageSize, String competitionName, String name, String country) {
        if(pageNumber<=0){
            pageNumber=COMMON_START_PAGE;
        }
        if(pageSize<=0){
            pageSize=COMMON_PAGE_SIZE;
        }
        Page<QueryJudgeVO> queryJudgeVOPage=judgeService.pageJudges(pageNumber, pageSize, competitionName, name, country);

        return ReturnResult.ok(queryJudgeVOPage);
    }

    @Override
    public ReturnResult addJudge(AddJudge addJudge) {
        boolean res = judgeService.saveJudge(addJudge);
        if(res){
            return ReturnResult.ok();
        }
        return ReturnResult.error(ResponseStatusEnum.JUDGE_CREATE_ERROR);
    }

    @Override
    public ReturnResult updateJudge(AddJudge addJudge) {
        boolean res = judgeService.updatePlayerById(addJudge);
        if(res){
            return ReturnResult.ok();
        }

        return ReturnResult.error(ResponseStatusEnum.JUDGE_MODIFY_ERROR);
    }

    @Override
    public ReturnResult queryJudge(Long id) {
        String judgeStr = redisUtil.get(REDIS_JUDGE_INFO + ":" + id);
        if(StringUtils.isNotBlank(judgeStr)){
            Judge judge = JsonUtils.jsonToPojo(judgeStr, Judge.class);
            return ReturnResult.ok(judge);
        }
        return ReturnResult.error(ResponseStatusEnum.JUDGE_NOT_EXISTS);
    }

    @Override
    public ReturnResult deleteJudge(Long id) {
        boolean res = judgeService.removeJudge(id);
        if(res){
            return ReturnResult.ok();
        }
        return ReturnResult.error(ResponseStatusEnum.JUDGE_DELETE_ERROR);
    }
}
