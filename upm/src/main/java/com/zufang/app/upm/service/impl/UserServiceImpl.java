package com.zufang.app.upm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zufang.app.upm.entity.RegisterVo;
import com.zufang.app.upm.entity.User;
import com.zufang.app.upm.mapper.UserMapper;
import com.zufang.app.upm.service.UserService;
import com.zufang.serverbase.exception.ZufangException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

/**
 * @author User
 * @date 2022/7/14 18:40
 */
@Component
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    public BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    public void register(RegisterVo registerVo) {
        String code = registerVo.getCode();
        String mobile = registerVo.getMobile();
        String password = registerVo.getPassword();
        String username = registerVo.getUsername();
        if (StringUtils.isEmpty(code) || StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password) || StringUtils.isEmpty(username)  ){
            throw new ZufangException(20001,"参数有空，注册失败");
        }
        // TODO 验证code码

        //  用户名是否重复
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username",username);
        Integer count = baseMapper.selectCount(wrapper);
        if (count > 0){
            throw new ZufangException(20001,"用户名已被使用，注册失败");
        }
        //  验证手机号是否已注册
        QueryWrapper<User> wrapper2 = new QueryWrapper<>();
        wrapper2.eq("mobile",mobile);
        Integer mobileCount = baseMapper.selectCount(wrapper2);
        if (mobileCount > 0){
            throw new ZufangException(20001,"手机号已被注册，注册失败");
        }
        User user = new User();
        user.setUsername(username);
        // 昵称默认等于 username
        user.setNickname(username);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        user.setMobile(mobile);
        user.setIsDisabled(false);
        //头像给默认值
        user.setAvatar("http://");
        baseMapper.insert(user);
    }

    @Override
    public User getUserByName(String username) {

        return baseMapper.selectOne(new QueryWrapper<User>().eq("username",username));
    }
}
