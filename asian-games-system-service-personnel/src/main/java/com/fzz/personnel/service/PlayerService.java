package com.fzz.personnel.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fzz.model.bo.ListPlayersBO;
import com.fzz.model.entity.Player;
import com.fzz.model.vo.QueryCountryVO;
import com.fzz.model.vo.QueryPlayerVO;

import java.util.List;

public interface PlayerService extends IService<Player> {

    List<QueryCountryVO> getRatioByCountry();

    Page<QueryPlayerVO> pagePlayers(ListPlayersBO listPlayersBO);
}
