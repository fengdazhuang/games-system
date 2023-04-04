package com.fzz.personnel.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fzz.model.entity.Player;
import com.fzz.model.vo.QueryCountryVO;

import java.util.List;

public interface PlayerService extends IService<Player> {

    List<QueryCountryVO> getRatioByCountry();
}
