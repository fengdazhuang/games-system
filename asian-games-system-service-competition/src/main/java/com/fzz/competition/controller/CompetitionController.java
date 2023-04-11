package com.fzz.competition.controller;

import com.fzz.api.BaseController;
import com.fzz.api.controller.competition.CompetitionControllerApi;
import com.fzz.common.enums.ResponseStatusEnum;
import com.fzz.common.result.ReturnResult;
import com.fzz.common.utils.JsonUtils;
import com.fzz.common.utils.RedisUtil;
import com.fzz.competition.service.CompetitionCategoryService;
import com.fzz.competition.service.CompetitionInfoService;
import com.fzz.model.bo.AddCompetitionCategory;
import com.fzz.model.bo.AddCompetitionInfo;
import com.fzz.model.entity.CompetitionCategory;
import com.fzz.model.entity.CompetitionInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CompetitionController extends BaseController implements CompetitionControllerApi {

    @Autowired
    private CompetitionCategoryService competitionCategoryService;

    @Autowired
    private CompetitionInfoService competitionInfoService;

    @Autowired
    private RedisUtil redisUtil;


    @Override
    public ReturnResult queryComCategorys() {
        String categorysStr = redisUtil.get(REDIS_COMPETITION_CATEGORYS);
        List<CompetitionCategory> competitionCategories;
        if(StringUtils.isNotBlank(categorysStr)){
            competitionCategories = JsonUtils.jsonToList(categorysStr, CompetitionCategory.class);
        }else{
            competitionCategories = competitionCategoryService.listComCategorys();
            redisUtil.set(REDIS_COMPETITION_CATEGORYS, JsonUtils.objectToJson(competitionCategories));
        }
        return ReturnResult.ok(competitionCategories);
    }

    @Override
    public ReturnResult queryComNames() {
        String infosStr = redisUtil.get(REDIS_COMPETITION_INFOS);
        List<CompetitionInfo> competitionInfos;
        if(StringUtils.isNotBlank(infosStr)){
            competitionInfos = JsonUtils.jsonToList(infosStr, CompetitionInfo.class);
        }else{
            competitionInfos = competitionInfoService.listComInfos();
            redisUtil.set(REDIS_COMPETITION_INFOS,JsonUtils.objectToJson(competitionInfos));
        }
        return ReturnResult.ok(competitionInfos);
    }

//    @Override
//    public ReturnResult queryComNames(Integer competitionCategoryId) {
//        String infosStr = redisUtil.get(REDIS_COMPETITION_INFOS + ":" + competitionCategoryId);
//        List<CompetitionInfo> competitionInfos;
//        if(StringUtils.isNotBlank(infosStr)){
//            competitionInfos = JsonUtils.jsonToList(infosStr, CompetitionInfo.class);
//        }else{
//            competitionInfos = competitionInfoService.listComNamesByComCategoryId(competitionCategoryId);
//            redisUtil.set(REDIS_COMPETITION_INFOS + ":" + competitionCategoryId, JsonUtils.objectToJson(competitionInfos));
//        }
//        return ReturnResult.ok(competitionInfos);
//    }

    @Override
    public ReturnResult addComCategory(AddCompetitionCategory addCompetitionCategory) {
        boolean res = competitionCategoryService.saveCompetitionCategory(addCompetitionCategory);
        if(res){
            redisUtil.del(REDIS_COMPETITION_CATEGORYS);
            return ReturnResult.ok();
        }
        return ReturnResult.error(ResponseStatusEnum.COMPETITION_CATEGORY_ADD_ERROR);
    }

    @Override
    public ReturnResult addComInfo(AddCompetitionInfo addCompetitionInfo) {
        boolean res = competitionInfoService.saveCompetitionInfo(addCompetitionInfo);
        if(res){
//            redisUtil.del(REDIS_COMPETITION_INFOS+":"+addCompetitionInfo.getCompetitionCategoryId());
            redisUtil.del(REDIS_COMPETITION_INFOS);
            return ReturnResult.ok();
        }
        return ReturnResult.error(ResponseStatusEnum.COMPETITION_INFO_ADD_ERROR);

    }


}
