package com.fzz.personnel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fzz.common.utils.JsonUtils;
import com.fzz.common.utils.RedisUtil;
import com.fzz.common.utils.SnowFlakeUtil;
import com.fzz.model.bo.AddPlayerBO;
import com.fzz.model.entity.Player;
import com.fzz.model.vo.QueryCountryVO;
import com.fzz.model.vo.QueryPlayerVO;
import com.fzz.personnel.mapper.PlayerMapper;
import com.fzz.personnel.service.PlayerService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlayerServiceImpl extends ServiceImpl<PlayerMapper, Player> implements PlayerService {

    private static final String[] countrys={"阿富汗","孟加拉国","不丹","巴林","文莱", "柬埔寨","中国","中国香港",
            "印度尼西亚","印度","伊朗","伊拉克","约旦","日本","哈萨克斯坦","吉尔吉斯斯坦","韩国","沙特阿拉伯","科威特",
            "老挝","黎巴嫩","中国澳门","马来西亚","马尔代夫"};

    private static final String REDIS_COMPETITION_INFOS = "redis_competition_infos";
    private static final String REDIS_PLAYER_INFO = "redis_player_info";

    @Autowired
    private RedisUtil redisUtil;


    @Override
    public List<QueryCountryVO> getRatioByCountry() {
        List<QueryCountryVO> queryCountryVOList=new ArrayList<>();
        for(String country:countrys){
            LambdaQueryWrapper<Player> queryWrapper=new LambdaQueryWrapper<>();
            queryWrapper.eq(Player::getCountry,country);
            int count = this.count(queryWrapper);
            QueryCountryVO queryCountryVO=new QueryCountryVO();
            queryCountryVO.setName(country);
            queryCountryVO.setCount(count);
            queryCountryVOList.add(queryCountryVO);
        }
        return queryCountryVOList;
    }


    @Override
    @Transactional
    public boolean savePlayer(AddPlayerBO addPlayer) {
        Player player=new Player();
        BeanUtils.copyProperties(addPlayer,player);
        SnowFlakeUtil snowFlakeUtil = new SnowFlakeUtil (12,13);
        Long snowFlakeId  = snowFlakeUtil.getNextId();
        player.setId(snowFlakeId);
        player.setCreateTime(new Date());
        boolean res = this.save(player);
        if(res){
            redisUtil.set(REDIS_PLAYER_INFO+":"+snowFlakeId,JsonUtils.objectToJson(player));
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public boolean removePlayer(Long id) {
        boolean res = this.removeById(id);
        if(res){
            redisUtil.del(REDIS_PLAYER_INFO+":"+id);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public boolean updatePlayerById(AddPlayerBO addPlayer) {
        Player player=new Player();
        BeanUtils.copyProperties(addPlayer,player);
        return this.updateById(player);
    }

    @Override
    public Page<QueryPlayerVO> pagePlayers(Integer pageNumber, Integer pageSize, String competitionName, String name, String country) {
        Page<Player> playerPage=new Page<>(pageNumber,pageSize);
        LambdaQueryWrapper<Player> queryWrapper=new LambdaQueryWrapper<>();
        String[] names = competitionName.split(",");
        queryWrapper.eq(StringUtils.isNotBlank(country),Player::getCountry, country);
        queryWrapper.in(names.length>0,Player::getCompetitionName, (Object[]) names);
        queryWrapper.like(StringUtils.isNotBlank(name),Player::getName,name);
        this.page(playerPage,queryWrapper);
        Page<QueryPlayerVO> queryPlayerVOPage=new Page<>();
        BeanUtils.copyProperties(playerPage,queryPlayerVOPage,"records");
        List<QueryPlayerVO> queryPlayerVOS = playerPage.getRecords().stream().map((item -> {
            QueryPlayerVO queryPlayerVO = new QueryPlayerVO();
            BeanUtils.copyProperties(item, queryPlayerVO);
            return queryPlayerVO;
        })).collect(Collectors.toList());
        queryPlayerVOPage.setRecords(queryPlayerVOS);
        return queryPlayerVOPage;
    }


/*    private String getComNameById(Integer categoryId ,Integer infoId){
        String comInfosStr = redisUtil.get(REDIS_COMPETITION_INFOS + ":" + categoryId);
        List<CompetitionInfo> competitionInfos = JsonUtils.jsonToList(comInfosStr, CompetitionInfo.class);
        for(CompetitionInfo competitionInfo:competitionInfos){
            if(competitionInfo.getId().equals(infoId)){
                return competitionInfo.getName();
            }
        }
        return "未知错误";
    }*/
}
