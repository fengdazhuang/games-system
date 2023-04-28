package com.fzz.personnel.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fzz.model.bo.AddPlayerBO;
import com.fzz.model.entity.Player;
import com.fzz.model.vo.QueryPlayerVO;

public interface PlayerService extends IService<Player> {

    boolean savePlayer(AddPlayerBO addPlayer);

    boolean removePlayers(Long[] id);

    boolean updatePlayerById(AddPlayerBO addPlayer);

    Page<QueryPlayerVO> pagePlayers(Integer pageNumber, Integer pageSize, String competitionName,
                                    String name, String country,Integer arrivalStatus, Integer healthyStatus);

    void updateArrivalStatus(Long playerId);
}
