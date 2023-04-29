package com.fzz.service.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fzz.model.bo.AddVolPositionBO;
import com.fzz.model.entity.VolPosition;
import com.fzz.model.vo.VolPositionVO;

import java.util.List;

public interface VolPositionService extends IService<VolPosition> {
    Page<VolPositionVO> pageVolunteerPositions(Integer pageNumber, Integer pageSize,Integer risk,
                                               Integer volunteerType, String name);

    List<VolPosition> listVolPositionsByRisk(Integer risk);

    boolean addVolunteerPosition(AddVolPositionBO addVolPositionBO);

    boolean removeVolPositions(String[] ids);
}
