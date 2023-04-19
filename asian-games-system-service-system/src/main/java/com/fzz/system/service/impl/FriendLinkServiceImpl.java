package com.fzz.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fzz.model.bo.FriendLinkBO;
import com.fzz.model.entity.FriendLink;
import com.fzz.system.mapper.FriendLinkMapper;
import com.fzz.system.service.FriendLinkService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class FriendLinkServiceImpl extends ServiceImpl<FriendLinkMapper, FriendLink> implements FriendLinkService {
    @Override
    public List<FriendLink> listFriendLinks() {
        return this.list();
    }

    @Override
    @Transactional
    public boolean saveFriendLink(FriendLinkBO friendLinkBO) {
        FriendLink friendLink=new FriendLink();
        BeanUtils.copyProperties(friendLinkBO,friendLink);
        friendLink.setCreateTime(new Date());
        return this.save(friendLink);
    }
}
