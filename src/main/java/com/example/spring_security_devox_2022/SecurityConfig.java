package com.example.spring_security_devox_2022;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Set;

@Configuration
@EnableWebSecurity
class SecurityConfig {
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .authorizeHttpRequests(authConf -> {
                    authConf.requestMatchers("/").permitAll();
                    authConf.requestMatchers("/error").permitAll();
                    authConf.anyRequest().authenticated();
                })
                .formLogin(Customizer.withDefaults())
                .oauth2Login(conf -> {
                })
                .oauth2Client(Customizer.withDefaults())
                .addFilterBefore(new RobotFilter(), UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    UserDetailsService usersDetails() {
        return new InMemoryUserDetailsManager(Set.of(User.builder()
                .username("max")
                .password("{noop}pass123")
                .authorities("ROLE_user")
                .build())
        );
    }
}
