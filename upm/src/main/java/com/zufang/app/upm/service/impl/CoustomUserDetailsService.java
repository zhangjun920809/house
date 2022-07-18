package com.zufang.app.upm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zufang.app.upm.entity.User;
import com.zufang.app.upm.mapper.UserMapper;
import com.zufang.app.upm.service.UserService;
import com.zufang.auth.oauthserver.entity.SecurityUser;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author User
 * @date 2022/7/15 11:38
 */
@Service("userDetailsService")
public class CoustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getUserByName(username);

        if(user == null){

        }
        com.zufang.auth.oauthserver.entity.User user1 = new com.zufang.auth.oauthserver.entity.User();
        BeanUtils.copyProperties(user,user1);
        // todo 从数据库 获取权限列表，暂时写死
        List<String> authorities = new ArrayList<>();

        authorities.add("permission1");
        SecurityUser securityUser = new SecurityUser(user1);
        securityUser.setPermissionList(authorities);
        return securityUser;

    }
}
