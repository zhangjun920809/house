package com.zufang.app.upm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zufang.app.upm.entity.RegisterVo;
import com.zufang.app.upm.entity.User;

/**
 * @author User
 * @date 2022/7/14 17:19
 */
public interface UserService extends IService<User> {
    void register(RegisterVo registerVo);
}