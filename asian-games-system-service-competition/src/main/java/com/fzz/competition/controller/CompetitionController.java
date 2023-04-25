package com.fzz.competition.controller;

import com.alibaba.fastjson.JSONObject;
import com.fzz.api.BaseController;
import com.fzz.api.controller.competition.CompetitionControllerApi;
import com.fzz.common.enums.ResponseStatusEnum;
import com.fzz.common.result.ReturnResult;
import com.fzz.common.utils.JsonUtils;
import com.fzz.common.utils.RedisUtil;
import com.fzz.competition.service.ComAreaService;
import com.fzz.competition.service.ComPositionService;
import com.fzz.competition.service.ComCategoryService;
import com.fzz.competition.service.ComInfoService;
import com.fzz.model.bo.AddComCategoryBO;
import com.fzz.model.bo.AddComInfoBO;
import com.fzz.model.bo.AddComPositionBO;
import com.fzz.model.bo.ComInfoBO;
import com.fzz.model.entity.ComArea;
import com.fzz.model.entity.ComCategory;
import com.fzz.model.entity.ComPosition;
import com.fzz.model.vo.QueryComInfoVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class CompetitionController extends BaseController implements CompetitionControllerApi {

    @Autowired
    private ComCategoryService comCategoryService;

    @Autowired
    private ComInfoService comInfoService;
    
    @Autowired
    private ComPositionService comPositionService;
    
    @Autowired
    private ComAreaService comAreaService;

    @Autowired
    private RedisUtil redisUtil;


    @Override
    public ReturnResult queryComCategorys() {
        String categorysStr = redisUtil.get(REDIS_COMPETITION_CATEGORYS);
        List<ComCategory> competitionCategories;
        if(StringUtils.isNotBlank(categorysStr)){
            competitionCategories = JsonUtils.jsonToList(categorysStr, ComCategory.class);
        }else{
            competitionCategories = comCategoryService.listComCategorys();
            redisUtil.set(REDIS_COMPETITION_CATEGORYS, JsonUtils.objectToJson(competitionCategories));
        }
        return ReturnResult.ok(competitionCategories);
    }

    @Override
    public ReturnResult queryComNames() {
        String infosStr = redisUtil.get(REDIS_COMPETITION_INFOS);
        List<QueryComInfoVO> comInfos;
        if(StringUtils.isNotBlank(infosStr)){
            comInfos = JsonUtils.jsonToList(infosStr, QueryComInfoVO.class);
        }else{
            comInfos = comInfoService.listComInfos();
            redisUtil.set(REDIS_COMPETITION_INFOS,JsonUtils.objectToJson(comInfos));
        }
        return ReturnResult.ok(comInfos);
    }

    @Override
    public ReturnResult queryComPositions(String area, String keyword) {
        List<ComPosition> comPositions = comPositionService.listComPositions(area,keyword);
        return ReturnResult.ok(comPositions);
    }

    @Override
    public ReturnResult queryComAreas() {
        List<ComArea> comAreas = comAreaService.listComAreas();
        return ReturnResult.ok(comAreas);
    }

    @Override
    public ReturnResult deleteComPosition(Integer id) {
        boolean res = comPositionService.removeComPositionById(id);
        if(res){
            return ReturnResult.ok();
        }
        return ReturnResult.error(ResponseStatusEnum.COMPETITION_POSITION_DELETE_ERROR);
    }

//    @Override
//    public ReturnResult queryComNames(Integer competitionCategoryId) {
//        String infosStr = redisUtil.get(REDIS_COMPETITION_INFOS + ":" + competitionCategoryId);
//        List<ComInfo> comInfos;
//        if(StringUtils.isNotBlank(infosStr)){
//            comInfos = JsonUtils.jsonToList(infosStr, ComInfo.class);
//        }else{
//            comInfos = comInfoService.listComNamesByComCategoryId(competitionCategoryId);
//            redisUtil.set(REDIS_COMPETITION_INFOS + ":" + competitionCategoryId, JsonUtils.objectToJson(ComInfos));
//        }
//        return ReturnResult.ok(comInfos);
//    }

    @Override
    public ReturnResult addComCategory(AddComCategoryBO addCompetitionCategory) {
        boolean res = comCategoryService.saveCompetitionCategory(addCompetitionCategory);
        if(res){
            redisUtil.del(REDIS_COMPETITION_CATEGORYS);
            return ReturnResult.ok();
        }
        return ReturnResult.error(ResponseStatusEnum.COMPETITION_CATEGORY_ADD_ERROR);
    }

    @Override
    public ReturnResult addComInfo(AddComInfoBO addComInfo) {
        boolean res = comInfoService.saveComInfo(addComInfo);
        if(res){
//            redisUtil.del(REDIS_COMPETITION_INFOS+":"+addComInfo.getCompetitionCategoryId());
            redisUtil.del(REDIS_COMPETITION_INFOS);
            return ReturnResult.ok();
        }
        return ReturnResult.error(ResponseStatusEnum.COMPETITION_INFO_ADD_ERROR);

    }

    @Override
    public ReturnResult addComArea(Map<String,Object> map) {
        String comArea = (String) map.get("comArea");
        boolean res = comAreaService.saveComArea(comArea);
        if(res){
            return ReturnResult.ok();
        }
        return ReturnResult.error(ResponseStatusEnum.COMPETITION_AREA_CREATE_ERROR);
    }

    @Override
    public ReturnResult modifyComInfo(ComInfoBO comInfoBO) {
        boolean res = comInfoService.updateComInfo(comInfoBO);
        if(res){
            redisUtil.del(REDIS_COMPETITION_CATEGORYS);
            return ReturnResult.ok();
        }
        return ReturnResult.error(ResponseStatusEnum.COMPETITION_INFO_UPDATE_ERROR);
    }

    @Override
    public ReturnResult deleteComInfo(Integer id) {
        boolean res = comInfoService.removeComInfoById(id);
        if(res){
            redisUtil.del(REDIS_COMPETITION_CATEGORYS);
            return ReturnResult.ok();
        }
        return ReturnResult.error(ResponseStatusEnum.COMPETITION_INFO_DELETE_ERROR);
    }

    @Override
    public ReturnResult addComPosition(AddComPositionBO addComPositionBO) {
        boolean res = comPositionService.saveComPosition(addComPositionBO);
        if(res){
            return ReturnResult.ok();
        }
        return ReturnResult.error(ResponseStatusEnum.COMPETITION_POSITION_CREATE_ERROR);
    }


}
