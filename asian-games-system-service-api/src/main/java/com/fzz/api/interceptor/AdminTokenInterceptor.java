package com.fzz.api.interceptor;

import com.fzz.api.BaseController;
import com.fzz.common.enums.ResponseStatusEnum;
import com.fzz.common.exception.CustomException;
import com.fzz.common.utils.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AdminTokenInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String adminId = request.getHeader("adminId");
        String adminToken = request.getHeader("adminToken");
        if(StringUtils.isNotBlank(adminId)&&StringUtils.isNotBlank(adminToken)){
            if(StringUtils.isBlank(adminId)){
                throw new CustomException(ResponseStatusEnum.UN_LOGIN);
            }else{
                String redisToken = redisUtil.get(BaseController.REDIS_ADMIN_TOKEN + ":" + adminId);
                if(!redisToken.equalsIgnoreCase(adminToken)){
                    throw new CustomException(ResponseStatusEnum.TICKET_INVALID);
                }
            }
            return true;
        }else{
            throw new CustomException(ResponseStatusEnum.UN_LOGIN);
        }

    }

}
