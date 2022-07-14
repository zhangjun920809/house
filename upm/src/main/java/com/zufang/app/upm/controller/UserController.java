package com.zufang.app.upm.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.zufang.app.upm.entity.RegisterVo;
import com.zufang.app.upm.service.UserService;
import com.zufang.serverbase.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author User
 * @date 2022/7/14 18:16
 */
@RestController
@RequestMapping
public class UserController {
    @Autowired
    private UserService userServiceImpl;

    //注册
    @PostMapping("/register")
    public Response register(@RequestBody RegisterVo registerVo){
        userServiceImpl.register(registerVo);
        return Response.ok();
    }
}
