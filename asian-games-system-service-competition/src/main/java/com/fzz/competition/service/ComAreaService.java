package com.fzz.competition.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fzz.model.entity.ComArea;

import java.util.List;

public interface ComAreaService extends IService<ComArea> {

    boolean saveComArea(String comArea);

    List<ComArea> listComAreas();
}
