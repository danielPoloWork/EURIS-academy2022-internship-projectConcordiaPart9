package com.euris.academy2022.concordia.businessLogics.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;

import static com.euris.academy2022.concordia.utils.constants.SecurityConstant.*;

@EnableWebSecurity
public class TestSecurityCfg {

    @Bean(name = BEAN_ADMIN)
    public UserDetailsManager beanUdmAdmin() {
        InMemoryUserDetailsManager iMUDM = new InMemoryUserDetailsManager();
        iMUDM.createUser(User
                .withUsername(BEAN_USERNAME_ADMIN)
                .password(BEAN_PASSWORD_ADMIN)
                .roles(ADMIN)
                .build());
        return iMUDM;
    }

    @Bean(name = BEAN_BASIC_MEMBER)
    public UserDetailsManager beanUdmBasicMember() {
        InMemoryUserDetailsManager iMUDM = new InMemoryUserDetailsManager();
        iMUDM.createUser(User
                .withUsername(BEAN_USERNAME_BASIC_MEMBER)
                .password(BEAN_PASSWORD_BASIC_MEMBER)
                .roles(A1)
                .build());
        return iMUDM;
    }
}

