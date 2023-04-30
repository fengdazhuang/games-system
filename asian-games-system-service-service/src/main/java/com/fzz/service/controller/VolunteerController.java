package com.fzz.service.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fzz.api.BaseController;
import com.fzz.api.controller.service.VolunteerControllerApi;
import com.fzz.common.enums.ResponseStatusEnum;
import com.fzz.common.result.ReturnResult;
import com.fzz.common.utils.JsonUtils;
import com.fzz.common.utils.RedisUtil;
import com.fzz.common.utils.ValidateCodeUtils;
import com.fzz.model.bo.*;
import com.fzz.model.dto.VolTeamDto;
import com.fzz.model.entity.VolDirection;
import com.fzz.model.entity.VolPosition;
import com.fzz.model.entity.Volunteer;
import com.fzz.model.vo.PreVolunteerVO;
import com.fzz.model.vo.ValidateCodeVO;
import com.fzz.model.vo.VolPositionVO;
import com.fzz.model.vo.VolunteerVO;
import com.fzz.service.service.IEmailService;
import com.fzz.service.service.VolDirectionService;
import com.fzz.service.service.VolPositionService;
import com.fzz.service.service.VolunteerService;
import com.wf.captcha.SpecCaptcha;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@RestController
public class VolunteerController extends BaseController implements VolunteerControllerApi {

    @Autowired
    private VolunteerService volunteerService;

    @Autowired
    private VolDirectionService volDirectionService;

    @Autowired
    private VolPositionService volPositionService;

    @Autowired
    private IEmailService emailService;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public ReturnResult register(VolunteerRegisterBO volunteerRegisterBO) {
        String email = volunteerRegisterBO.getEmail();
        String validateCode = volunteerRegisterBO.getValidateCode();
        String code = redisUtil.get(REDIS_VOLUNTEER_EMAIL_CODE + ":" + email);
        if(!validateCode.equals(code)){
            return ReturnResult.error(ResponseStatusEnum.VOLUNTEER_VALIDATE_CODE_ERROR);
        }
        Volunteer volunteer=new Volunteer();
        volunteer.setEmail(email);
        volunteer.setPassword(volunteerRegisterBO.getPassword());
        volunteer.setCreateTime(new Date());
        boolean res = volunteerService.saveVolunteer(volunteer);
        if(res){
            return ReturnResult.ok(volunteer);
        }
        return ReturnResult.error(ResponseStatusEnum.VOLUNTEER_REGISTER_FAILED);
    }

    @Override
    public ReturnResult sendEmailCode(Map<String,Object> map) {
        String email = (String) map.get("email");
        Integer type = (Integer) map.get("type");
        String randomCode = RandomStringUtils.randomAlphanumeric(10);
        Volunteer volunteer = volunteerService.getVolunteerByEmail(email);
        if(type==0){
            if(volunteer!=null){
                return ReturnResult.error(ResponseStatusEnum.VOLUNTEER_IS_ALREADY_EXISTS);
            }
            emailService.sendSimpleMail(Arrays.asList(email),"亚运会志愿者注册",randomCode);
            redisUtil.set(REDIS_VOLUNTEER_EMAIL_CODE+":"+email,randomCode,30*60);
            return ReturnResult.ok();
        } else if(type==1){
            if(volunteer==null){
                return ReturnResult.error(ResponseStatusEnum.VOLUNTEER_IS_NOT_EXISTS);
            }
            emailService.sendSimpleMail(Arrays.asList(email),"亚运会志愿者重置密码",randomCode);
            redisUtil.set(REDIS_VOLUNTEER_EMAIL_CODE+":"+email,randomCode,30*60);
            return ReturnResult.ok();
        }
        return ReturnResult.error(ResponseStatusEnum.FAILED);
    }

    @Override
    public ReturnResult pageVolunteers(Integer pageNumber, Integer pageSize, Integer volunteerType, Integer risk) {
        if(pageNumber<=0){
            pageNumber=COMMON_START_PAGE;
        }
        if(pageSize<=0){
            pageSize=COMMON_PAGE_SIZE;
        }
        Page<VolunteerVO> volunteerVOPage = volunteerService.pageVolunteers(pageNumber,pageSize,volunteerType,risk);
        return ReturnResult.ok(volunteerVOPage);
    }

    @Override
    public ReturnResult pagePreVolunteers(Integer pageNumber, Integer pageSize, Integer orderType) {
        if(pageNumber<=0){
            pageNumber=COMMON_START_PAGE;
        }
        if(pageSize<=0){
            pageSize=COMMON_PAGE_SIZE;
        }
        Page<PreVolunteerVO> volunteerVOPage = volunteerService.pagePreVolunteers(pageNumber,pageSize,orderType);
        return ReturnResult.ok(volunteerVOPage);
    }

    @Override
    public ReturnResult doReview(DoReviewBO doReviewBO) {
        boolean res = volunteerService.doReview(doReviewBO);
        if(res){
            String email = doReviewBO.getEmail();
            String emailContent = doReviewBO.getEmailContent();
            Integer status = doReviewBO.getStatus();
            if(StringUtils.isNotBlank(emailContent)){
                emailService.sendSimpleMail(Arrays.asList(email),
                        status ==1?"志愿者审核通过":"志愿者审核未通过",emailContent);
            }
            return ReturnResult.ok();
        }
        return ReturnResult.error(ResponseStatusEnum.VOLUNTEER_REVIEW_ERROR);
    }

    @Override
    public ReturnResult login(VolunteerLoginBO volunteerLoginBO,
                              HttpServletRequest request,
                              HttpServletResponse response) {
        //判断该管理员是否存在
        String email = volunteerLoginBO.getEmail();
        Volunteer volunteer=volunteerService.volunteerIsExists(email);

        //校验验证码
        String key = volunteerLoginBO.getKey();
        String code = redisUtil.get(key);
        String validateCode = volunteerLoginBO.getValidateCode();
        redisUtil.del(key);
        if(!validateCode.equalsIgnoreCase(code)){
            return ReturnResult.error(ResponseStatusEnum.SYSTEM_VALIDATE_CODE_ERROR);
        }

        //校验密码
        String password = volunteerLoginBO.getPassword();
        if(!password.equals(volunteer.getPassword())){
            return ReturnResult.error(ResponseStatusEnum.VOLUNTEER_LOGIN_ERROR);
        }

        //保存用户标识符和id在cookie中，在redis中保存标识符和admin对象
        String uToken=UUID.randomUUID().toString();
        redisUtil.set(REDIS_VOLUNTEER_TOKEN+":"+volunteer.getId(),uToken);
        redisUtil.set(REDIS_VOLUNTEER_INFO+":"+volunteer.getId(), JsonUtils.objectToJson(volunteer));

        setCookie(request,response,"utoken",uToken,COOKIE_MONTH);
        setCookie(request,response,"uid",String.valueOf(volunteer.getId()),COOKIE_MONTH);

        return ReturnResult.ok(volunteer);
    }

    @Override
    public ReturnResult queryVolDirections(Integer volunteerType) {
        String str = redisUtil.get(REDIS_VOLUNTEER_DIRECTION_INFOS + ":" + volunteerType);
        List<VolDirection> volDirections = null;
        if(StringUtils.isNotBlank(str)){
            volDirections = JsonUtils.jsonToList(str, VolDirection.class);
        }else{
            volDirections = volDirectionService.listVolDirections(volunteerType);
            redisUtil.set(REDIS_VOLUNTEER_DIRECTION_INFOS+":"+volunteerType,JsonUtils.objectToJson(volDirections));
        }
        return ReturnResult.ok(volDirections);
    }

    @Override
    public ReturnResult resetVolunteerRisk(ResetVolunteerRiskBO resetVolunteerRiskBO) {
        boolean res = volunteerService.updateVolunteerRisk(resetVolunteerRiskBO);
        if(res){
            return ReturnResult.ok();
        }
        return ReturnResult.error(ResponseStatusEnum.VOLUNTEER_REST_RISK_ERROR);
    }

    /**
     * 获得验证码图片给前端
     * @return 验证码图片键值对
     */
    @Override
    public ReturnResult getCodeImage() {
        String key = UUID.randomUUID().toString();
        SpecCaptcha specCaptcha = ValidateCodeUtils.validateCodeImage();
        String code = specCaptcha.text();
        String base64 = specCaptcha.toBase64();
        redisUtil.set(key,code,30*60);
        ValidateCodeVO validateCodeVO=new ValidateCodeVO();
        validateCodeVO.setKey(key);
        validateCodeVO.setValidateCode(base64);
        return ReturnResult.ok(validateCodeVO);
    }


    @Override
    public ReturnResult deleteVolPosition(String[] ids) {

        boolean res = volPositionService.removeVolPositions(ids);
        if(res){
            return ReturnResult.ok();
        }
        return ReturnResult.error(ResponseStatusEnum.VOLUNTEER_POSITION_DELETE_ERROR);
    }

    @Override
    public ReturnResult addVolPosition(AddVolPositionBO addVolPositionBO) {
        boolean res = volPositionService.addVolunteerPosition(addVolPositionBO);
        if(res){
            return ReturnResult.ok();
        }
        return ReturnResult.error(ResponseStatusEnum.VOLUNTEER_POSITION_CREATE_ERROR);
    }

    @Override
    public ReturnResult queryVolPositions(Integer risk) {
        List<VolPosition> volPositionList = volPositionService.listVolPositionsByRisk(risk);
        return ReturnResult.ok(volPositionList);
    }

    @Override
    public ReturnResult getVolTeamInfo(String teamId) {
        VolTeamDto volTeamDto = volunteerService.getVolTeamInfo(teamId);
        return ReturnResult.ok(volTeamDto);
    }

    @Override
    public ReturnResult queryVolunteer(Long id) {
        Volunteer volunteer = volunteerService.getVolunteerDetailById(id);
        return ReturnResult.ok(volunteer);
    }

    @Override
    public ReturnResult applyVolunteer(ApplyVolunteerBO applyVolunteerBO) {
        boolean res = volunteerService.applyVolunteer(applyVolunteerBO);
        if(res){
            return ReturnResult.ok();
        }
        return ReturnResult.error(ResponseStatusEnum.VOLUNTEER_APPLY_FAILED);
    }

    @Override
    public ReturnResult pageVolunteerPositions(Integer pageNumber, Integer pageSize, Integer risk,
                                               Integer volunteerType, String name) {
        if(pageNumber<=0){
            pageNumber=COMMON_START_PAGE;
        }
        if(pageSize<=0){
            pageSize=COMMON_PAGE_SIZE;
        }
        Page<VolPositionVO> page = volPositionService.pageVolunteerPositions(pageNumber,pageSize,risk,volunteerType,name);
        return ReturnResult.ok(page);
    }

    @Override
    public ReturnResult chooseVolunteerType(Long id, Integer volunteerType) {
        boolean res = volunteerService.updateVolunteerType(id, volunteerType);
        if(res){
            return ReturnResult.ok();
        }
        return ReturnResult.error(ResponseStatusEnum.VOLUNTEER_MODIFY_TYPE_FAILED);
    }

    @Override
    public ReturnResult modifyInfo(VolunteerInfoBO volunteerInfoBO) {
        boolean res = volunteerService.perfectOrUpdateVolunteerInfo(volunteerInfoBO);
        if(res){
            return ReturnResult.ok();
        }
        return ReturnResult.error(ResponseStatusEnum.VOLUNTEER_MODIFY_INFO_FAILED);
    }

    @Override
    public ReturnResult modifyPassword(ModifyPasswordBO modifyPasswordBO) {
        boolean res = volunteerService.updateVolunteerPassword(modifyPasswordBO);
        if(res){
            return ReturnResult.ok();
        }
        return ReturnResult.error(ResponseStatusEnum.VOLUNTEER_MODIFY_PASSWORD_FAILED);
    }

    @Override
    public ReturnResult forgetPassword(VolunteerRegisterBO volunteerRegisterBO) {
        String email = volunteerRegisterBO.getEmail();
        String validateCode = volunteerRegisterBO.getValidateCode();
        String code = redisUtil.get(REDIS_VOLUNTEER_EMAIL_CODE + ":" + email);
        if(!validateCode.equals(code)){
            return ReturnResult.error(ResponseStatusEnum.VOLUNTEER_VALIDATE_CODE_ERROR);
        }
        boolean res = volunteerService.forgetVolunteerPassword(volunteerRegisterBO);
        if(res){
            return ReturnResult.ok();
        }
        return ReturnResult.error(ResponseStatusEnum.VOLUNTEER_MODIFY_PASSWORD_FAILED);
    }

    @Override
    public ReturnResult logout(Long id,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {

        redisUtil.del(REDIS_VOLUNTEER_TOKEN+":"+id);
        redisUtil.del(REDIS_VOLUNTEER_INFO+":"+id);
        deleteCookie(request,response,"utoken");
        deleteCookie(request,response,"uid");
        return ReturnResult.ok();
    }
}
