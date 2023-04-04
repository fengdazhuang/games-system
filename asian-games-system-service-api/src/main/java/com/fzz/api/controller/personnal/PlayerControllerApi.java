package com.fzz.api.controller.personnal;

import com.fzz.common.result.ReturnResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/player")
public interface PlayerControllerApi {

    @GetMapping("/queryRatioByCountry")
    ReturnResult queryRatioByCountry();;
}
