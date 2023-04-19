package com.fzz.api.controller.service;

import com.fzz.common.result.ReturnResult;
import com.fzz.model.bo.DoReviewBO;
import com.fzz.model.bo.VolunteerRegisterBO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/api3/volunteer")
@Api(value = "志愿者服务")
public interface VolunteerControllerApi {

    @PostMapping("/register")
    @ApiOperation(value = "志愿者注册")
    ReturnResult register(@Valid @RequestBody VolunteerRegisterBO volunteerRegisterBO);

    @PostMapping("/sendCode")
    @ApiOperation(value = "向邮箱发送验证码")
    ReturnResult sendEmailCode(@RequestBody String email);

    @GetMapping("/pageVolunteers")
    @ApiOperation(value = "分页条件查询已招募志愿者")
    ReturnResult pageVolunteers(@RequestParam Integer pageNumber,
                                @RequestParam Integer pageSize,
                                @RequestParam Integer volunteerType,
                                @RequestParam String risk);

    @GetMapping("/pagePreVolunteers")
    @ApiOperation(value = "分页条件查询待审批志愿者")
    ReturnResult pagePreVolunteers(@RequestParam Integer pageNumber,
                                   @RequestParam Integer pageSize,
                                   @RequestParam Integer orderType);

    @PutMapping("/doReview")
    @ApiOperation(value = "审批志愿者")
    ReturnResult doReview(@RequestBody DoReviewBO doReviewBO);

}
