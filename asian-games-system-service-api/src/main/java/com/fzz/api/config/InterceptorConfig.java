package com.fzz.api.config;


import com.fzz.api.interceptor.AdminTokenInterceptor;
import com.fzz.api.interceptor.VolunteerTokenInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


//@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

//    @Bean
//    public PassportInterceptor passportInterceptor(){
//        return new PassportInterceptor();
//    }
//

    @Bean
    public VolunteerTokenInterceptor volunteerTokenInterceptor(){
        return new VolunteerTokenInterceptor();
    }

    @Bean
    public AdminTokenInterceptor adminTokenInterceptor(){
        return new AdminTokenInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

//        registry.addInterceptor(userTokenInterceptor())
//                .addPathPatterns("/user/getAccountInfo")
//                .addPathPatterns("/user/updateUserInfo")
//                .addPathPatterns("/fs/uploadFace");
//
        registry.addInterceptor(volunteerTokenInterceptor())
                .addPathPatterns("/api3/volunteer/modifyPassword")
                .addPathPatterns("/api3/volunteer/modifyInfo")
                .addPathPatterns("/api3/volunteer/forgetPassword")
                .addPathPatterns("/api3/volunteer/chooseVolType")
                .addPathPatterns("/api3/volunteer/applyVolunteer")
                .addPathPatterns("/api3/volunteer/queryVolunteer");

        registry.addInterceptor(adminTokenInterceptor())
                .addPathPatterns("/api3/volunteer/pageVolunteers")
                .addPathPatterns("/api3/volunteer/pagePreVolunteers")
                .addPathPatterns("/api3/volunteer/doReview")
                .addPathPatterns("/api3/volunteer/resetRisk")
                .addPathPatterns("/api3/volunteer/pageVolPositions")
                .addPathPatterns("/api1/admin/listAdmins")
                .addPathPatterns("/api1/admin/addAdmin")
                .addPathPatterns("/api1/admin/updateStatus")
                .addPathPatterns("/api1/admin/resetPassword")
                .addPathPatterns("/api1/admin/getInfo")
                .addPathPatterns("/api1/admin/modifyInfo")
                .addPathPatterns("/api1/judge/listJudges")
                .addPathPatterns("/api1/judge/addJudge")
                .addPathPatterns("/api1/judge/updateJudge")
                .addPathPatterns("/api1/judge/queryJudge")
                .addPathPatterns("/api1/player/listPlayers")
                .addPathPatterns("/api1/judge/deleteJudge")
                .addPathPatterns("/api1/player/addPlayer")
                .addPathPatterns("/api1/player/deletePlayer")
                .addPathPatterns("/api1/player/updatePlayer")
                .addPathPatterns("/api1/player/queryPlayer");



    }

}
