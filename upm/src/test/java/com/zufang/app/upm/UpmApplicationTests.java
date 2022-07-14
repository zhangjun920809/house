package com.zufang.app.upm;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zufang.app.upm.entity.User;
import com.zufang.app.upm.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import sun.invoke.WrapperInstance;

import java.util.Date;
import java.util.List;

@SpringBootTest
class UpmApplicationTests {

    @Autowired
    private UserMapper userMapper;
    @Test
    void contextLoads() {
        List<User> users = userMapper.selectList(null);
        for (User user : users) {
            System.out.println(user.toString());
        }
        System.out.println();
    }

    @Test
    void add() {
        User user = new User();
        user.setUsername("zhangxiaoliu");
        user.setPassword("zhang@123");
        user.setNickname("俊哥");
        user.setMobile("13691375623");
        user.setIdNumber("11111223");
        user.setOpenid("dfs");
        user.setSex(2);
        user.setIsDeleted(true);
        user.setIsDisabled(false);
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        userMapper.insert(user);
        System.out.println();
    }
    @Test
    void update() {
        User user = new User();
        user.setUsername("zhangxiaoliu");
        user.setPassword("zhang@123");
        user.setNickname("俊哥-update");
        user.setMobile("13691375623");
        user.setIdNumber("11111223");
        user.setOpenid("dfs");
        user.setSex(2);
        user.setIsDeleted(true);
        user.setIsDisabled(false);
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("id",3);
        userMapper.update(user,queryWrapper);
        System.out.println();
    }

}
