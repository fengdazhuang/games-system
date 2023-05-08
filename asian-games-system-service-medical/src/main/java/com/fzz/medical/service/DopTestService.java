package com.fzz.medical.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fzz.model.bo.AddDopTestBO;
import com.fzz.model.bo.SubmitDopResultBO;
import com.fzz.model.entity.DopTest;
import com.fzz.model.vo.DopTestVO;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public interface DopTestService extends IService<DopTest> {
    Page<DopTest> pageResult(Integer pageNumber, Integer pageSize, Integer examinationType, Integer examinationResult, Date examinationTime, String country, String name);

    Page<DopTestVO> pageExamination(Integer pageNumber, Integer pageSize, String sampleNumber);

    boolean saveDopTest(AddDopTestBO addDopTestBO);

    boolean updateExaminationResult(List<SubmitDopResultBO> submitDopResultBOList);
}
