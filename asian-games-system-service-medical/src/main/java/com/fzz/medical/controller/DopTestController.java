package com.fzz.medical.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fzz.api.BaseController;
import com.fzz.api.controller.medical.DopTestControllerApi;
import com.fzz.common.enums.ResponseStatusEnum;
import com.fzz.common.result.ReturnResult;
import com.fzz.medical.service.DopTestService;
import com.fzz.model.bo.AddDopTestBO;
import com.fzz.model.bo.SubmitDopResult;
import com.fzz.model.entity.DopTest;
import com.fzz.model.vo.DopTestVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class DopTestController extends BaseController implements DopTestControllerApi {

    @Autowired
    private DopTestService dopTestService;

    @Override
    public ReturnResult pageResult(Integer pageNumber, Integer pageSize, Integer examinationType,
                                   Integer examinationResult, Date examinationTime, String country, String name) {

        if(pageNumber<=0){
            pageNumber=COMMON_START_PAGE;
        }
        if(pageSize<=0){
            pageSize=COMMON_PAGE_SIZE;
        }
        Page<DopTest> page = dopTestService.pageResult(pageNumber,pageSize,examinationType,
                examinationResult,examinationTime,country,name);
        return ReturnResult.ok(page);
    }

    @Override
    public ReturnResult pageExamination(Integer pageNumber, Integer pageSize, String sampleNumber) {
        if(pageNumber<=0){
            pageNumber=COMMON_START_PAGE;
        }
        if(pageSize<=0){
            pageSize=COMMON_PAGE_SIZE;
        }
        Page<DopTestVO> page = dopTestService.pageExamination(pageNumber,pageSize,sampleNumber);
        return ReturnResult.ok(page);
    }

    @Override
    public ReturnResult addDopTest(AddDopTestBO addDopTestBO) {
        Long playerId = addDopTestBO.getPlayerId();
        String faceBase64 = faceGet(String.valueOf(playerId), "player");
        Map<String, Object> map = faceMatch(addDopTestBO.getBase64(), faceBase64);
        if((double)map.get("score")>75){
            boolean res = dopTestService.saveDopTest(addDopTestBO);
            if(res){
                return ReturnResult.ok();
            }
            return ReturnResult.error(ResponseStatusEnum.DOP_TEST_REGISTER_FAILED);
        }
        return ReturnResult.error(ResponseStatusEnum.FACE_NOT_MATCH);
    }

    @Override
    public ReturnResult submitResult(List<SubmitDopResult> submitDopResultList) {
        boolean res = dopTestService.updateExaminationResult(submitDopResultList);
        if(res){
            return ReturnResult.ok();
        }
        return ReturnResult.error(ResponseStatusEnum.DOP_TEST_RESULT_SUBMIT_FAILED);

    }


}
