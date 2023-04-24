package com.fzz.api.controller.system;

import com.fzz.common.result.ReturnResult;
import com.fzz.model.bo.AddFriendLinkBO;
import com.fzz.model.bo.UpdateFriendLinkBO;
import com.fzz.model.bo.UpdateStatusBO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api5/system")
public interface SystemControllerApi {

    @GetMapping("/getFriendLinks")
    ReturnResult queryFriendLinks();

    @PostMapping("/addFriendLink")
    ReturnResult addFriendLink(@RequestBody AddFriendLinkBO addFriendLinkBO);

    @PutMapping("/modifyFriendLinkStatus")
    ReturnResult modifyFriendLinkStatus(@RequestBody List<UpdateStatusBO> updateStatusBOList);

    @PutMapping("/modifyFriendLink")
    ReturnResult modifyFriendLink(@RequestBody UpdateFriendLinkBO updateFriendLinkBO);
}
