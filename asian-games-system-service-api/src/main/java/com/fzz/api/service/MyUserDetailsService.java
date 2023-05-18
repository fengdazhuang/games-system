package com.fzz.api.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fzz.api.mapper.AdminMapper;
import com.fzz.model.entity.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userDetailsService")
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LambdaQueryWrapper<Admin> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Admin::getUsername,username);
        Admin admin = adminMapper.selectOne(queryWrapper);
        if(admin==null){
            throw new UsernameNotFoundException("用户名不存在");
        }
        List<GrantedAuthority> authorityList = AuthorityUtils.commaSeparatedStringToAuthorityList("admin");
        return new User(admin.getUsername(),new BCryptPasswordEncoder().encode(admin.getPassword()),authorityList);
    }
}
