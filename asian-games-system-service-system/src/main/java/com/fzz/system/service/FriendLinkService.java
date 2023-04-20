package com.fzz.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fzz.model.bo.FriendLinkBO;
import com.fzz.model.entity.FriendLink;

import java.util.List;

public interface FriendLinkService extends IService<FriendLink> {
    List<FriendLink> listFriendLinks();

    boolean saveFriendLink(FriendLinkBO friendLinkBO);
}
