package com.Buddewar.PracticeDemo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig {

// adding the custom tables
    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource)
    {
        JdbcUserDetailsManager theUserDetailsManager= new JdbcUserDetailsManager(dataSource);
        theUserDetailsManager.setUsersByUsernameQuery(
                "select username,password,enabled from users where username=?"
        );

        theUserDetailsManager.setAuthoritiesByUsernameQuery(
                "SELECT u.username, CONCAT('ROLE_', r.name) " +
                        "FROM users u INNER JOIN roles r ON u.role_id = r.id " +
                        "WHERE u.username = ?"
        );

        return theUserDetailsManager;
    }
   @Autowired
    private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
    {
        http.authorizeHttpRequests(configurer->
                        configurer
                                .requestMatchers("/admin/**").hasRole("Admin")
                                .requestMatchers("/user/**").hasRole("User")
                                .requestMatchers("/showLoginForm", "/showRegistrationForm", "/Registration", "/css/**", "/js/**").permitAll()
                                .anyRequest().authenticated()
                ).formLogin(form->
                form
                        .loginPage("/showLoginForm")
                        .loginProcessingUrl("/authenticateTheUser")
                        .successHandler(customAuthenticationSuccessHandler)
                        .permitAll()

        )
                .logout(logout->
                        logout.permitAll()
                );
        return http.build();

    }

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }
}
