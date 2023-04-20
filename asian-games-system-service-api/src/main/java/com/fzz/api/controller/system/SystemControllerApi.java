package com.fzz.api.controller.system;

import com.fzz.common.result.ReturnResult;
import com.fzz.model.bo.FriendLinkBO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api5/system")
public interface SystemControllerApi {

    @GetMapping("/getFriendLinks")
    ReturnResult queryFriendLinks();

    @PostMapping("/addFriendLink")
    ReturnResult addFriendLink(@RequestBody FriendLinkBO friendLinkBO);
}
