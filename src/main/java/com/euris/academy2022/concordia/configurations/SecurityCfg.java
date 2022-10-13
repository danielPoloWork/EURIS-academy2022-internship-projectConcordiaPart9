package com.euris.academy2022.concordia.configurations;

import com.euris.academy2022.concordia.utils.enums.MemberRole;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder.BCryptVersion;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collection;

@EnableWebSecurity
public class SecurityCfg {

  @Bean(name = "generatePasswordEncoder2A")
  public BCryptPasswordEncoder generatePasswordEncoder2A() {
    return new BCryptPasswordEncoder(BCryptVersion.$2A);
  }

  @Bean(name = "generateSecurityFilterChain")
  public SecurityFilterChain generateSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
    return httpSecurity
        .csrf().disable()
        .authorizeRequests()
        // ROOT ------------------------------------------------------------------------------------
        .antMatchers("/*").hasAnyRole(BASIC, ADMIN)
        // AUTHORIZATION MODEL ---------------------------------------------------------------------
        .antMatchers(HttpMethod.POST, MAPPING_MEMBER + "/**").hasAnyRole(MANAGER, ADMIN)
        .antMatchers(HttpMethod.PUT, MAPPING_MEMBER + "/**").hasAnyRole(MANAGER, ADMIN)
        .antMatchers(HttpMethod.DELETE, MAPPING_MEMBER + "/**").hasRole(MANAGER)
        .antMatchers(HttpMethod.GET, MAPPING_MEMBER + "/**").hasAnyRole(MANAGER, ADMIN, BASIC)
        // AUTHORIZATION USER-DETAIL-MANAGER MODEL -------------------------------------------------
        .antMatchers(HttpMethod.POST, MAPPING_USER_DETAIL_MANAGER + "/**").hasRole(MANAGER)
        .antMatchers(HttpMethod.PUT, MAPPING_USER_DETAIL_MANAGER + "/**").hasRole(MANAGER)
        .antMatchers(HttpMethod.DELETE, MAPPING_USER_DETAIL_MANAGER + "/**").hasRole(MANAGER)
        .antMatchers(HttpMethod.GET, MAPPING_USER_DETAIL_MANAGER + "/**").hasRole(MANAGER)
        // USER MODEL ------------------------------------------------------------------------------
        .antMatchers(HttpMethod.POST,MAPPING_USER + "/**").hasAnyRole(BASIC, ADMIN)
        .antMatchers(HttpMethod.PUT,MAPPING_USER + "/**").hasAnyRole(BASIC, ADMIN)
        .antMatchers(HttpMethod.DELETE, MAPPING_USER + "/**").hasRole(ADMIN)
        .antMatchers(HttpMethod.GET,MAPPING_USER + "/**").hasAnyRole(BASIC, ADMIN)
        .anyRequest().authenticated()
        .and().httpBasic()
        .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and().build();
  }

  @Bean(name = "generateUserDetailsManager")
  public UserDetailsManager generateUserDetailsManager(BCryptPasswordEncoder passwordEncoder, AuthorizationService authorizationService) {

    InMemoryUserDetailsManager userDetailsManager = new InMemoryUserDetailsManager();

    // SET DEFAULT USER AS MANAGER -----------------------------------------------------------------
    ApiResponse<Collection<Authorization>> authManagerCollection = authorizationService.responseGetByRole(AuthRoleEnum.MANAGER);


    if (authManagerCollection != null) {
      if (!authManagerCollection.getBody().isEmpty()) {
        System.out.println("Managers already exists.\n");
      } else {
        userDetailsManager.createUser(User
            .withUsername("temp")
            .password(passwordEncoder.encode("temp"))
            .roles(MANAGER)
            .build());
      }
    }


    // SET DYNAMIC USERS ---------------------------------------------------------------------------
    ApiResponse<Collection<Authorization>> authCollection = authorizationService.responseGetAll();

    if (authCollection != null) {
      if (authCollection.getBody().isEmpty()) {
        System.out.println("There are no users to add.\n");
      } else {
        authCollection.getBody().forEach(auth -> userDetailsManager
            .createUser(User
                .withUsername(auth.getUsername())
                .password(passwordEncoder.encode(auth.getPassword()))
                .roles(auth.getRole().getLabel())
                .build()));
      }
    }


    return userDetailsManager;
  }
}