package com.fzz.personnel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fzz.model.entity.Player;
import com.fzz.model.vo.QueryCountryVO;
import com.fzz.personnel.mapper.PlayerMapper;
import com.fzz.personnel.service.PlayerService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PlayerServiceImpl extends ServiceImpl<PlayerMapper, Player> implements PlayerService {

    private static final String[] countrys={"阿富汗","孟加拉国","不丹","巴林","文莱", "柬埔寨","中国","中国香港",
            "印度尼西亚","印度","伊朗","伊拉克","约旦","日本","哈萨克斯坦","吉尔吉斯斯坦","韩国","沙特阿拉伯","科威特",
            "老挝","黎巴嫩","中国澳门","马来西亚","马尔代夫"};


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
}
