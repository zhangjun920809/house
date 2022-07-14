package com.zufang.app.upm.service.impl;

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

        // TODO 验证手机号是否已注册
    }
}
