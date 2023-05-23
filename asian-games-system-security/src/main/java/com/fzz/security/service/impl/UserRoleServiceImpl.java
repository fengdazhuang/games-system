package com.fzz.security.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fzz.security.entity.UserRole;
import com.fzz.security.mapper.UserRoleMapper;
import com.fzz.security.service.UserRoleService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-01-12
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

}
