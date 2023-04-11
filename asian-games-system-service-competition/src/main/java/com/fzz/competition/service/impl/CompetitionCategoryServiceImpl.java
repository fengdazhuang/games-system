package com.fzz.competition.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fzz.competition.mapper.CompetitionCategoryMapper;
import com.fzz.competition.service.CompetitionCategoryService;
import com.fzz.model.bo.AddCompetitionCategoryBO;
import com.fzz.model.entity.CompetitionCategory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class CompetitionCategoryServiceImpl extends ServiceImpl<CompetitionCategoryMapper, CompetitionCategory> implements CompetitionCategoryService {


    @Override
    public List<CompetitionCategory> listComCategorys() {
        return this.list();
    }

    @Override
    @Transactional
    public boolean saveCompetitionCategory(AddCompetitionCategoryBO addCompetitionCategory){
        CompetitionCategory competitionCategory=new CompetitionCategory();
        BeanUtils.copyProperties(addCompetitionCategory,competitionCategory);
        competitionCategory.setCreateTime(new Date());
        competitionCategory.setUpdateTime(new Date());
        return this.save(competitionCategory);
    }
}
