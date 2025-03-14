package com.fzz.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fzz.model.bo.AddFriendLinkBO;
import com.fzz.model.bo.UpdateFriendLinkBO;
import com.fzz.model.bo.UpdateStatusBO;
import com.fzz.model.entity.FriendLink;
import com.fzz.model.vo.FriendLinkVO;

import java.util.List;

public interface FriendLinkService extends IService<FriendLink> {
    List<FriendLinkVO> listFriendLinks();

    boolean saveFriendLink(AddFriendLinkBO addFriendLinkBO);

    boolean updateFriendLinkStatus(List<UpdateStatusBO> updateStatusBOList);

    boolean updateFriendLink(UpdateFriendLinkBO updateFriendLinkBO);
}
