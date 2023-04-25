package com.fzz.api.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

//    @Bean
//    public PassportInterceptor passportInterceptor(){
//        return new PassportInterceptor();
//    }
//
//    @Bean
//    public AdminTokenInterceptor adminStatusInterceptor(){
//        return new AdminTokenInterceptor();
//    }

//    @Bean
//    public UserTokenInterceptor userTokenInterceptor(){
//        return new UserTokenInterceptor();
//    }
//
//    @Bean
//    public AdminTokenInterceptor adminTokenInterceptor(){
//        return new AdminTokenInterceptor();
//    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

//        registry.addInterceptor(userTokenInterceptor())
//                .addPathPatterns("/user/getAccountInfo")
//                .addPathPatterns("/user/updateUserInfo")
//                .addPathPatterns("/fs/uploadFace");
//
//        registry.addInterceptor(adminStatusInterceptor())
//                .addPathPatterns("/writer/*")
//                .addPathPatterns("/user/updateUserInfo");

//        registry.addInterceptor(adminTokenInterceptor())
//                .addPathPatterns("/adminMng/addNewAdmin")
//                .addPathPatterns("/adminMng/getAdminList")
//                .addPathPatterns("/adminMng/adminIsExist")
//                .addPathPatterns("/appUser/queryAll")
//                .addPathPatterns("/appUser/userDetail")
//                .addPathPatterns("/appUser/freezeUserOrNot");



    }

}
