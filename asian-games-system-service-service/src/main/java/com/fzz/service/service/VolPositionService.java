package com.fzz.service.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fzz.model.entity.VolPosition;
import com.fzz.model.vo.VolPositionVO;

public interface VolPositionService extends IService<VolPosition> {
    Page<VolPositionVO> pageVolunteerPositions(Integer pageNumber, Integer pageSize,Integer risk,
                                               Integer volunteerType, String name);
}
