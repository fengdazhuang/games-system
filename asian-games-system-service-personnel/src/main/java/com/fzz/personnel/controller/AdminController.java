package com.fzz.personnel.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fzz.api.BaseController;
import com.fzz.api.controller.personnal.AdminControllerApi;
import com.fzz.common.enums.AdminStatusEnum;
import com.fzz.common.enums.ResponseStatusEnum;
import com.fzz.common.result.ReturnResult;
import com.fzz.common.utils.JsonUtils;
import com.fzz.common.utils.RedisUtil;
import com.fzz.common.utils.ValidateCodeUtils;
import com.fzz.model.bo.AddAdminBO;
import com.fzz.model.bo.AdminLoginBO;
import com.fzz.model.bo.ResetPasswordBO;
import com.fzz.model.bo.UpdateStatusBO;
import com.fzz.model.entity.Admin;
import com.fzz.model.vo.QueryAdminVO;
import com.fzz.model.vo.ValidateCodeVO;
import com.fzz.personnel.service.AdminService;
import com.wf.captcha.SpecCaptcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

@RestController
public class AdminController extends BaseController implements AdminControllerApi {

    @Autowired
    private AdminService adminService;

    @Autowired
    private RedisUtil redisUtil;



    /**
     * 管理员登录
     * @param adminLoginBO 管理员的登录bo
     * @return 成功
     */
    @Override
    public ReturnResult login(AdminLoginBO adminLoginBO,
                              HttpServletRequest request,
                              HttpServletResponse response) throws Exception {
        //判断该管理员是否存在
        String username = adminLoginBO.getUsername();
        Admin admin=adminService.adminIsExists(username);

        //校验验证码
        String key = adminLoginBO.getKey();
        String code = redisUtil.get(key);
        String validateCode = adminLoginBO.getValidateCode();
        redisUtil.del(key);
        if(!validateCode.equalsIgnoreCase(code)){
            return ReturnResult.error(ResponseStatusEnum.SYSTEM_VALIDATE_CODE_ERROR);
        }

        //判断该管理员是否可用
        Integer status = admin.getStatus();
        if(status.equals(AdminStatusEnum.DISABLED.code())){
            return ReturnResult.error(ResponseStatusEnum.ADMIN_NOT_AVAILABLE);
        }

        //校验密码
        String password = adminLoginBO.getPassword();
        if(!password.equals(admin.getPassword())){
            return ReturnResult.error(ResponseStatusEnum.ADMIN_PASSWORD_ERROR);
        }

        //保存用户标识符和id在cookie中，在redis中保存标识符和admin对象
        String uToken=UUID.randomUUID().toString();
        redisUtil.set(REDIS_USER_TOKEN+":"+admin.getId(),uToken);
        redisUtil.set(REDIS_USER_INFO+":"+admin.getId(), JsonUtils.objectToJson(admin));

        setCookie(request,response,"utoken",uToken,COOKIE_MONTH);
        setCookie(request,response,"uid",String.valueOf(admin.getId()),COOKIE_MONTH);

        //更新管理员登陆时间
        adminService.setAdminLoginTime(admin);
        return ReturnResult.ok(admin);
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

    /**
     * 管理员退出
     * @param id 管理员的id
     * @return 成功
     */
    @Override
    public ReturnResult logout(Long id,
                              HttpServletRequest request,
                              HttpServletResponse response) throws Exception {

        redisUtil.del(REDIS_USER_TOKEN+":"+id);
        redisUtil.del(REDIS_USER_INFO+":"+id);
        deleteCookie(request,response,"utoken");
        deleteCookie(request,response,"uid");
        return ReturnResult.ok();
    }

    @Override
    public ReturnResult listAdmins(Integer pageNumber, Integer pageSize, String name) {
        if(pageNumber<=0){
            pageNumber=COMMON_START_PAGE;
        }
        if(pageSize<=0){
            pageSize=COMMON_PAGE_SIZE;
        }
        Page<QueryAdminVO> adminPage = adminService.pageAdmins(pageNumber,pageSize,name);

        return ReturnResult.ok(adminPage);
    }

    @Override
    public ReturnResult addAdmin(AddAdminBO addAdminBO) {
        boolean res = adminService.saveAdmin(addAdminBO);
        if(res){
            return ReturnResult.ok();
        }
        return ReturnResult.error(ResponseStatusEnum.ADMIN_CREATE_ERROR);
    }

    @Override
    public ReturnResult queryAdmin(String username) {
        boolean res = adminService.usernameIsExists(username);
        if(res){
            return ReturnResult.error(ResponseStatusEnum.ADMIN_USERNAME_ALREADY_EXISTS);
        }
        return ReturnResult.ok();
    }

    @Override
    public ReturnResult updateStatus(List<UpdateStatusBO> updateStatusBOList) {
        boolean res = adminService.updateAdminStatus(updateStatusBOList);
        if(res){
            return ReturnResult.ok();
        }
        return ReturnResult.error(ResponseStatusEnum.ADMIN_STATUS_MODIFY_ERROR);
    }


    @Override
    public ReturnResult resetPassword(ResetPasswordBO resetPasswordBO) {

        adminService.resetAdminPassword(resetPasswordBO);
        return ReturnResult.ok();
    }

    @Override
    public ReturnResult getInfo(Long id) {
        Admin admin = adminService.getAdminInfo(id);
        return ReturnResult.ok(admin);
    }

    @Override
    public ReturnResult modifyInfo(Admin admin) {
        boolean res = adminService.updateAdminInfo(admin);
        if(res){
            return ReturnResult.ok();
        }
        return ReturnResult.error(ResponseStatusEnum.ADMIN_MODIFY_INFO_ERROR);
    }
}

