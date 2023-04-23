package com.fzz.competition.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fzz.competition.mapper.ComInfoMapper;
import com.fzz.competition.service.ComInfoService;
import com.fzz.model.bo.AddComInfoBO;
import com.fzz.model.bo.ComInfoBO;
import com.fzz.model.entity.ComInfo;
import com.fzz.model.vo.QueryComInfoVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class ComInfoServiceImpl extends ServiceImpl<ComInfoMapper, ComInfo> implements ComInfoService {


//    @Override
//    public List<ComInfo> listComNamesByComCategoryId(Integer competitionCategoryId) {
//        LambdaQueryWrapper<ComInfo> queryWrapper=new LambdaQueryWrapper<>();
//        queryWrapper.eq(ComInfo::getCompetitionCategoryId,competitionCategoryId);
//        return this.list(queryWrapper);
//
//    }

    @Override
    @Transactional
    public boolean saveComInfo(AddComInfoBO addComInfo) {
        ComInfo comInfo=new ComInfo();
        BeanUtils.copyProperties(addComInfo,comInfo);
        comInfo.setCreateTime(new Date());
        comInfo.setUpdateTime(new Date());
        return this.save(comInfo);
    }

    @Override
    @Transactional
    public boolean updateComInfo(ComInfoBO comInfoBO) {
        ComInfo comInfo=new ComInfo();
        BeanUtils.copyProperties(comInfoBO,comInfo);
        return this.updateById(comInfo);
    }

    @Override
    @Transactional
    public boolean removeComInfoById(Integer id) {
        return this.removeById(id);
    }

    @Override
    public List<QueryComInfoVO> listComInfos() {

        List<ComInfo> list = this.list();
        List<QueryComInfoVO> comInfoVOS = list.stream().map(((item -> {
            QueryComInfoVO queryComInfoVO = new QueryComInfoVO();
            BeanUtils.copyProperties(item, queryComInfoVO);
            return queryComInfoVO;
        }))).collect(Collectors.toList());
        return comInfoVOS;
    }
}
