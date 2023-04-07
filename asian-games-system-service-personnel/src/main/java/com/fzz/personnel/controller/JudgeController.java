package com.fzz.personnel.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fzz.api.BaseController;
import com.fzz.api.controller.personnal.JudgeControllerApi;
import com.fzz.common.result.ReturnResult;
import com.fzz.model.bo.ListJudgesBO;
import com.fzz.model.entity.Judge;
import com.fzz.model.vo.QueryJudgeVO;
import com.fzz.personnel.service.JudgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JudgeController extends BaseController implements JudgeControllerApi {

    @Autowired
    private JudgeService judgeService;

    @Override
    public ReturnResult listJudges(ListJudgesBO listJudgesBO) {
        Integer pageNumber = listJudgesBO.getPageNumber();
        Integer pageSize = listJudgesBO.getPageSize();
        if(pageNumber<=0){
            listJudgesBO.setPageNumber(COMMON_START_PAGE);
        }
        if(pageSize<=0){
            listJudgesBO.setPageSize(COMMON_PAGE_SIZE);
        }
        Page<QueryJudgeVO> queryJudgeVOPage=judgeService.pageJudges(listJudgesBO);

        return ReturnResult.ok(queryJudgeVOPage);
    }
}
