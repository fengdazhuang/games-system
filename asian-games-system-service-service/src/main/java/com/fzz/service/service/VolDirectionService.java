package com.fzz.service.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fzz.model.entity.VolDirection;

import java.util.List;

public interface VolDirectionService extends IService<VolDirection> {
    List<VolDirection> listVolDirections(Integer volunteerType);
}
