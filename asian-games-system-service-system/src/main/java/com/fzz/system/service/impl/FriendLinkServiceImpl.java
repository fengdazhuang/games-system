package com.fzz.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fzz.model.bo.AddFriendLinkBO;
import com.fzz.model.bo.UpdateFriendLinkBO;
import com.fzz.model.bo.UpdateStatusBO;
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
    public boolean updateFriendLink(UpdateFriendLinkBO updateFriendLinkBO) {
        FriendLink friendLink=new FriendLink();
        BeanUtils.copyProperties(updateFriendLinkBO,friendLink);
        return this.updateById(friendLink);
    }

    @Override
    @Transactional
    public boolean updateFriendLinkStatus(List<UpdateStatusBO> updateStatusBOList) {
        boolean flag;
        for(UpdateStatusBO item:updateStatusBOList){
            LambdaUpdateWrapper<FriendLink> updateWrapper=new LambdaUpdateWrapper<>();
            updateWrapper.eq(FriendLink::getId,item.getId());
            updateWrapper.set(FriendLink::getStatus,item.getStatus());
            flag = this.update(updateWrapper);
            if(!flag){
                return false;
            }
        }
        return true;
    }

    @Override
    @Transactional
    public boolean saveFriendLink(AddFriendLinkBO addFriendLinkBO) {
        FriendLink friendLink=new FriendLink();
        BeanUtils.copyProperties(addFriendLinkBO,friendLink);
        friendLink.setCreateTime(new Date());
        return this.save(friendLink);
    }
}
