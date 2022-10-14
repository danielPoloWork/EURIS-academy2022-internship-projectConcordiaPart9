package com.euris.academy2022.concordia.configurations;

import com.euris.academy2022.concordia.businessLogics.services.MemberService;
import com.euris.academy2022.concordia.dataPersistences.DTOs.MemberDto;
import com.euris.academy2022.concordia.dataPersistences.DTOs.ResponseDto;
import com.euris.academy2022.concordia.dataPersistences.models.Member;
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

import java.util.List;

import static com.euris.academy2022.concordia.utils.constants.SecurityConstant.*;

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
                .antMatchers("/*").hasAnyRole(MANAGER, ADMIN, A1, A2, A3, B1, C1, C2)
                // MAPPING MEMBER
                .antMatchers(HttpMethod.POST, MAPPING_MEMBER + "/**").hasAnyRole(MANAGER, ADMIN)
                .antMatchers(HttpMethod.PUT, MAPPING_MEMBER + "/**").hasAnyRole(MANAGER, ADMIN)
                .antMatchers(HttpMethod.DELETE, MAPPING_MEMBER + "/**").hasAnyRole(MANAGER, ADMIN)
                .antMatchers(HttpMethod.GET, MAPPING_MEMBER + "/**").hasAnyRole(MANAGER, ADMIN, CONCORDIA)
                // MAPPING USER DETAIL MANAGER
                .antMatchers(HttpMethod.POST, MAPPING_USER_DETAIL_MANAGER + "/**").hasRole(MANAGER)
                .antMatchers(HttpMethod.PUT, MAPPING_USER_DETAIL_MANAGER + "/**").hasRole(MANAGER)
                .antMatchers(HttpMethod.DELETE, MAPPING_USER_DETAIL_MANAGER + "/**").hasRole(MANAGER)
                .antMatchers(HttpMethod.GET, MAPPING_USER_DETAIL_MANAGER + "/**").hasRole(MANAGER)
                // MAPPING TASK
                .antMatchers(HttpMethod.POST, MAPPING_TASK + "/**").hasRole(ADMIN)
                .antMatchers(HttpMethod.PUT, MAPPING_TASK + "/**").hasAnyRole(ADMIN, A1, A2, A3, B1, C1, C2)
                .antMatchers(HttpMethod.DELETE, MAPPING_TASK + "/**").hasRole(ADMIN)
                .antMatchers(HttpMethod.GET, MAPPING_TASK + "/**").hasAnyRole(ADMIN, A1, A2, A3, B1, C1, C2)
                // MAPPING ASSIGNEE
                .antMatchers(HttpMethod.POST, MAPPING_ASSIGNEE + "/**").hasAnyRole(ADMIN, A1, A2, A3, B1, C1, C2)
                .antMatchers(HttpMethod.DELETE, MAPPING_ASSIGNEE + "/**").hasAnyRole(ADMIN, A1, A2, A3, B1, C1, C2)
                // MAPPING COMMENT
                .antMatchers(HttpMethod.POST, MAPPING_COMMENT + "/**").hasAnyRole(ADMIN, A1, A2, A3, B1, C1, C2)
                .antMatchers(HttpMethod.PUT, MAPPING_COMMENT + "/**").hasAnyRole(ADMIN, A1, A2, A3, B1, C1, C2)
                .antMatchers(HttpMethod.DELETE, MAPPING_COMMENT + "/**").hasAnyRole(ADMIN, A1, A2, A3, B1, C1, C2)
                .antMatchers(HttpMethod.GET, MAPPING_COMMENT + "/**").hasAnyRole(ADMIN, A1, A2, A3, B1, C1, C2)
                // MAPPING TABLET
                .antMatchers(HttpMethod.GET, MAPPING_TABLET + "/**").hasAnyRole(ADMIN, A1, A2, A3, B1, C1, C2)
                // MAPPING CONFIGURATION
                .antMatchers(HttpMethod.POST, MAPPING_CONFIGURATION + "/**").hasRole(ADMIN)
                .antMatchers(HttpMethod.PUT, MAPPING_CONFIGURATION + "/**").hasRole(ADMIN)
                .antMatchers(HttpMethod.DELETE, MAPPING_CONFIGURATION + "/**").hasRole(ADMIN)
                .antMatchers(HttpMethod.GET, MAPPING_CONFIGURATION + "/**").hasRole(ADMIN)
                // MAPPING CONFIGURATION
                .antMatchers(HttpMethod.POST, MAPPING_CONNECTION_WINDOW + "/**").hasRole(ADMIN)
                .antMatchers(HttpMethod.PUT, MAPPING_CONNECTION_WINDOW + "/**").hasRole(ADMIN)
                .antMatchers(HttpMethod.DELETE, MAPPING_CONNECTION_WINDOW + "/**").hasRole(ADMIN)
                .antMatchers(HttpMethod.GET, MAPPING_CONNECTION_WINDOW + "/**").hasRole(ADMIN)
                //
                .anyRequest().authenticated()
                .and().httpBasic()
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().build();
    }

    @Bean(name = "generateUserDetailsManager")
    public UserDetailsManager generateUserDetailsManager(BCryptPasswordEncoder passwordEncoder, MemberService memberService) {

        InMemoryUserDetailsManager userDetailsManager = new InMemoryUserDetailsManager();

        ResponseDto<List<MemberDto>> managers = memberService.getByRole(MemberRole.MANAGER);

        if (managers != null && managers.getBody() != null) {
                System.out.println("Managers already exists.");
            } else {
                userDetailsManager.createUser(User
                        .withUsername("default_manager")
                        .password(passwordEncoder.encode("manager"))
                        .roles(MANAGER)
                        .build());
        }


        ResponseDto<List<Member>> getAllResponse = memberService.getAllMember();

        if (getAllResponse != null && getAllResponse.getBody() != null) {
            getAllResponse.getBody().forEach(member -> userDetailsManager
                    .createUser(User
                            .withUsername(member.getUsername())
                            .password(passwordEncoder.encode(member.getPassword()))
                            .roles(member.getRole().getLabel())
                            .build()));
            } else {
            System.out.println("There are no members to add.");
        }


        return userDetailsManager;
    }
}