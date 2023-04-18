package com.fzz.competition.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fzz.model.bo.AddComInfoBO;
import com.fzz.model.entity.ComInfo;
import com.fzz.model.vo.QueryComInfoVO;

import java.util.List;

public interface ComInfoService extends IService<ComInfo> {

//    List<ComInfo> listComNamesByComCategoryId(Integer comCategoryId);

    boolean saveComInfo(AddComInfoBO addComInfoBO);

    List<QueryComInfoVO> listComInfos();
}
