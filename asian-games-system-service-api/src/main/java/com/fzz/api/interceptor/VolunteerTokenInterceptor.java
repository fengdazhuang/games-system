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

public class VolunteerTokenInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String volunteerId = request.getHeader("volunteerId");
        String volunteerToken = request.getHeader("volunteerToken");
        if(StringUtils.isNotBlank(volunteerId)&&StringUtils.isNotBlank(volunteerToken)){
            if(StringUtils.isBlank(volunteerId)){
                throw new CustomException(ResponseStatusEnum.UN_LOGIN);
            }else{
                String redisToken = redisUtil.get(BaseController.REDIS_VOLUNTEER_TOKEN + ":" + volunteerId);
                if(!redisToken.equalsIgnoreCase(volunteerToken)){
                    throw new CustomException(ResponseStatusEnum.TICKET_INVALID);
                }
            }
            return true;
        }else{
            throw new CustomException(ResponseStatusEnum.UN_LOGIN);
        }

    }

}
