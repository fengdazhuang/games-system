package com.fzz.medical.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fzz.api.BaseController;
import com.fzz.api.controller.medical.DopTestControllerApi;
import com.fzz.common.enums.ResponseStatusEnum;
import com.fzz.common.result.ReturnResult;
import com.fzz.medical.service.DopTestService;
import com.fzz.model.bo.AddDopTestBO;
import com.fzz.model.bo.SubmitDopResultBO;
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
        String base64 = addDopTestBO.getBase64();
        Map<String, Object> result = faceSearch(base64, "player");
        if(result!=null){
            List<Map<String,Object>> userList = (List<Map<String, Object>>) result.get("user_list");
            Map<String, Object> userInfo = userList.get(0);
            if((double) userInfo.get("score")>70){
                boolean res = dopTestService.saveDopTest(addDopTestBO);
                if(res){
                    return ReturnResult.ok();
                }
                return ReturnResult.error(ResponseStatusEnum.DOP_TEST_REGISTER_FAILED);
            }
            return ReturnResult.error(ResponseStatusEnum.FACE_NOT_MATCH);
        }
        return ReturnResult.error(ResponseStatusEnum.FACE_NOT_MATCH);
    }

    @Override
    public ReturnResult submitResult(List<SubmitDopResultBO> submitDopResultBOList) {
        boolean res = dopTestService.updateExaminationResult(submitDopResultBOList);
        if(res){
            return ReturnResult.ok();
        }
        return ReturnResult.error(ResponseStatusEnum.DOP_TEST_RESULT_SUBMIT_FAILED);

    }


}
