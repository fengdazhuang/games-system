package com.fzz.api.controller.service;

import com.fzz.common.result.ReturnResult;
import com.fzz.model.bo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    @PutMapping("/resetRisk")
    @ApiOperation(value = "修改志愿者服务任务")
    ReturnResult resetVolunteerRisk(@RequestBody ResetVolunteerRiskBO resetVolunteerRiskBO);

    @GetMapping("/getVolDirections")
    @ApiOperation(value = "根据志愿者类型获取所有志愿方向")
    ReturnResult queryVolDirections(@RequestParam Integer volunteerType);


    @PostMapping("/login")
    @ApiOperation(value = "根据志愿者类型获取所有志愿方向")
    ReturnResult login(@RequestBody VolunteerLoginBO volunteerLoginBO,
                       HttpServletRequest request,
                       HttpServletResponse response);

    @GetMapping("/getCodeImage")
    @ApiOperation(value = "获取登陆时验证码的图片")
    ReturnResult getCodeImage();

    @GetMapping("/logout")
    @ApiOperation(value = "志愿者登出")
    ReturnResult logout(Long id,
                        HttpServletRequest request,
                        HttpServletResponse response) throws Exception;

    @PutMapping("/forgetPassword")
    @ApiOperation(value = "志愿者忘记密码")
    ReturnResult forgetPassword(@Valid @RequestBody VolunteerRegisterBO volunteerRegisterBO);

    @PutMapping("/modifyPassword")
    @ApiOperation(value = "志愿者修改密码")
    ReturnResult modifyPassword(@Valid @RequestBody ModifyPasswordBO modifyPasswordBO);

    @PutMapping("/modifyInfo")
    @ApiOperation(value = "志愿者修改个人信息")
    ReturnResult modifyInfo(@Valid @RequestBody VolunteerInfoBO volunteerInfoBO);

    @PutMapping("/chooseVolType")
    @ApiOperation(value = "志愿者修改个人信息")
    ReturnResult chooseVolunteerType(@RequestParam Long id,@RequestParam Integer volunteerType);

    @GetMapping("/pageVolPositions")
    @ApiOperation(value = "分页条件查询志愿服务点")
    ReturnResult pageVolunteerPositions(@RequestParam Integer pageNumber,
                                   @RequestParam Integer pageSize);




}
