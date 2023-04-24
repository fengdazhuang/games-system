package com.fzz.system.controller;

import com.fzz.api.BaseController;
import com.fzz.api.controller.system.SystemControllerApi;
import com.fzz.common.enums.ResponseStatusEnum;
import com.fzz.common.result.ReturnResult;
import com.fzz.model.bo.AddFriendLinkBO;
import com.fzz.model.bo.UpdateFriendLinkBO;
import com.fzz.model.bo.UpdateStatusBO;
import com.fzz.model.entity.FriendLink;
import com.fzz.system.service.FriendLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SystemController extends BaseController implements SystemControllerApi {

    @Autowired
    private FriendLinkService friendLinkService;

    @Override
    public ReturnResult queryFriendLinks() {
        List<FriendLink> list=friendLinkService.listFriendLinks();
        return ReturnResult.ok(list);
    }

    @Override
    public ReturnResult modifyFriendLinkStatus(List<UpdateStatusBO> updateStatusBOList) {
        boolean res = friendLinkService.updateFriendLinkStatus(updateStatusBOList);
        if(res){
            return ReturnResult.ok();
        }
        return ReturnResult.error(ResponseStatusEnum.FRIENDLINK_MODIFY_STATUS_ERROR);
    }

    @Override
    public ReturnResult modifyFriendLink(UpdateFriendLinkBO updateFriendLinkBO) {
        boolean res = friendLinkService.updateFriendLink(updateFriendLinkBO);
        if(res){
            return ReturnResult.ok();
        }
        return ReturnResult.error(ResponseStatusEnum.FRIENDLINK_UPDATE_INFO_ERROR);
    }

    @Override
    public ReturnResult addFriendLink(AddFriendLinkBO addFriendLinkBO) {
        boolean res = friendLinkService.saveFriendLink(addFriendLinkBO);
        if(res){
            return ReturnResult.ok();
        }
        return ReturnResult.error(ResponseStatusEnum.FRIENDLINK_CREATE_ERROR);
    }
}
