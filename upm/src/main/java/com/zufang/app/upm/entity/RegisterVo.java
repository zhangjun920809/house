package com.zufang.app.upm.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author User
 * @date 2022/7/14 18:02
 */
@Data
public class RegisterVo {
    private String username;

    private String password;

    private String mobile;
    // 验证码
    private String code;
}
