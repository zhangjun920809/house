package com.zufang.auth.oauthserver.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author User
 * @date 2022/7/13 21:29
 */
@Data
public class User implements Serializable {

    private String username;

    private String password;

    private String salt;
}
