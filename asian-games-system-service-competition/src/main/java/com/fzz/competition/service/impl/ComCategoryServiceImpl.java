package com.fzz.competition.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fzz.competition.mapper.ComCategoryMapper;
import com.fzz.competition.service.ComCategoryService;
import com.fzz.model.bo.AddComCategoryBO;
import com.fzz.model.entity.ComCategory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class ComCategoryServiceImpl extends ServiceImpl<ComCategoryMapper, ComCategory> implements ComCategoryService {


    @Override
    public List<ComCategory> listComCategorys() {
        return this.list();
    }

    @Override
    @Transactional
    public boolean saveCompetitionCategory(AddComCategoryBO addCompetitionCategory){
        ComCategory competitionCategory=new ComCategory();
        BeanUtils.copyProperties(addCompetitionCategory,competitionCategory);
        competitionCategory.setCreateTime(new Date());
        competitionCategory.setUpdateTime(new Date());
        return this.save(competitionCategory);
    }
}
