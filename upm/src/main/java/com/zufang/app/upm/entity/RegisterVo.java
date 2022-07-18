package com.zufang.app.upm.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author User
 * @date 2022/7/14 18:02
 */
@Data
public class RegisterVo {
    @NotEmpty(message = "用户名不能为空")
    private String username;
    @NotEmpty(message = "密码不能为空")
    private String password;
    @NotEmpty(message = "手机号不能为空")
    @Max(11)
    private String mobile;
    // 验证码
    private String code;
}
