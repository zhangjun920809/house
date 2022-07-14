package com.zufang.auth.oauthserver.config;

import com.zufang.auth.oauthserver.security.DefaultPasswordEncoder;
import com.zufang.auth.oauthserver.security.TokenManager;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class TokenWebSecurityConfig extends WebSecurityConfigurerAdapter {
    private TokenManager tokenManager;
    private DefaultPasswordEncoder defaultPasswordEncoder;

}
