package com.fzz.competition.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fzz.competition.mapper.ComAreaMapper;
import com.fzz.competition.service.ComAreaService;
import com.fzz.model.entity.ComArea;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class ComAreaServiceImpl extends ServiceImpl<ComAreaMapper, ComArea> implements ComAreaService {

    @Override
    @Transactional
    public boolean saveComArea(String comArea) {
        ComArea area=new ComArea();
        area.setName(comArea);
        area.setCreateTime(new Date());
        return this.save(area);
    }

    @Override
    public List<ComArea> listComAreas() {
        return this.list();
    }
}
