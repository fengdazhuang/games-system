package com.fzz.competition.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fzz.model.bo.AddComPositionBO;
import com.fzz.model.entity.ComPosition;

import java.util.List;

public interface ComPositionService extends IService<ComPosition> {

    boolean saveComPosition(AddComPositionBO addComPositionBO);

    boolean removeComPositionById(Integer id);

    List<ComPosition> listComPositions(String area, String keyword);
}
