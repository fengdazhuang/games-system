package com.fzz.personnel.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fzz.model.bo.AddPlayer;
import com.fzz.model.entity.Player;
import com.fzz.model.vo.QueryCountryVO;
import com.fzz.model.vo.QueryPlayerVO;

import java.util.List;

public interface PlayerService extends IService<Player> {

    List<QueryCountryVO> getRatioByCountry();

    boolean savePlayer(AddPlayer addPlayer);

    boolean removePlayer(Long id);

    boolean updatePlayerById(AddPlayer addPlayer);

    Page<QueryPlayerVO> pagePlayers(Integer pageNumber, Integer pageSize, String competitionName, String name, String country);
}
