package com.fzz.personnel.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fzz.model.bo.AddJudge;
import com.fzz.model.entity.Judge;
import com.fzz.model.vo.QueryJudgeVO;

public interface JudgeService extends IService<Judge> {

    boolean saveJudge(AddJudge addJudge);

    boolean removeJudge(Long id);

    boolean updatePlayerById(AddJudge addJudge);

    Page<QueryJudgeVO> pageJudges(Integer pageNumber, Integer pageSize, String competitionName, String name, String country);
}
