package com.fzz.personnel.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fzz.model.bo.ListJudgesBO;
import com.fzz.model.entity.Judge;
import com.fzz.model.vo.QueryJudgeVO;

public interface JudgeService extends IService<Judge> {
    Page<QueryJudgeVO> pageJudges(ListJudgesBO listJudgesBO);
}
