/*
 * Copyright Axemtum 2024
 */
package com.axemtum.manager;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/**
 * The security configuration for the application
 *
 * @author Magnus Rossander
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public UserDetailsService userDetailsService() throws Exception {
        UserBuilder users = User.builder();
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(users.username("haris").password(passwordEncoder().encode("1234")).roles("VD", "MANAGER").build());
        manager.createUser(users.username("hanna").password(passwordEncoder().encode("1234")).roles("MANAGER").build());
        manager.createUser(users.username("emma").password(passwordEncoder().encode("1234")).roles("MANAGER").build());
        return manager;
    }

    @Bean
    public SecurityFilterChain formLoginFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                .anyRequest().authenticated())
                .formLogin(withDefaults())
                .logout((logout) -> logout
                .logoutSuccessUrl("/login")
                .permitAll()
                );
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
