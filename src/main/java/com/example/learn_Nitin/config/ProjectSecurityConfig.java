package com.example.learn_Nitin.config;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ProjectSecurityConfig {
    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf((csrf) -> csrf.ignoringRequestMatchers("/saveMsg")
                        .ignoringRequestMatchers("/public/**"))
//                        .ignoringRequestMatchers(PathRequest.toH2Console()))
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/dashboard").authenticated()
                        .requestMatchers("/displayMessages/**").hasRole("ADMIN")
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/student/**").hasRole("STUDENT")
                        .requestMatchers("/displayProfile").authenticated()
                        .requestMatchers("/updateProfile").authenticated()
                        .requestMatchers("/closeMsg/**").hasRole("ADMIN")
                        .requestMatchers("/error").permitAll()
                        .requestMatchers("", "/", "/home").permitAll()
                        .requestMatchers("/holidays/**").permitAll()
                        .requestMatchers("/contact").permitAll()
                        .requestMatchers("/saveMsg").permitAll()
                        .requestMatchers("/courses").permitAll()
                        .requestMatchers("/about").permitAll()
                        .requestMatchers("/assets/**").permitAll()
                        .requestMatchers("/login").permitAll()
                        .requestMatchers("/logout").permitAll()
                        .requestMatchers("/public/**").permitAll())
//                        .requestMatchers(PathRequest.toH2Console()).permitAll())
                .formLogin(formLogin-> formLogin
                        .loginPage("/login")
                        .defaultSuccessUrl("/dashboard")
                        .failureUrl("/login?error=true")
                        .permitAll())
//                .logout(logout->logout
//                        .logoutSuccessUrl("/login?logout=true")
//                        .invalidateHttpSession(true)
//                        .permitAll())
                .httpBasic(Customizer.withDefaults());
//                .headers().frameOptions().disable();
        return http.build();
//        return http.
//                authorizeHttpRequests((requests)->requests
//                .anyRequest().denyAll())
//                .formLogin(Customizer.withDefaults())
//                .httpBasic(Customizer.withDefaults())
//                .build();

    }
    //Since we have defined your own AuthenticationProvider, it gets priority compared to InMemoryUserDetailsManager
    //So below code is not needed anymore
    //Creating users inside the memory of the application itself
//    @Bean
//    public InMemoryUserDetailsManager userDetailsManager()
//    {
//        //whenever we are using default password encoder
//        //Spring security framework internally will store my password using plain text itself
//        //If we don't use DefaultPasswordEncoder Spring security will be clueless in what standard
//        //and how to store the password
//        UserDetails admin= User.withDefaultPasswordEncoder()
//                .username("user")
//                .password("12345")
//                .roles("USER")
//                .build();
//        UserDetails user=User.withDefaultPasswordEncoder()
//                .username("admin")
//                .password("54321")
//                .roles("ADMIN")
//                .build();
//        return new InMemoryUserDetailsManager(user,admin);
//    }

    //We need to indicate to Spring Security that we want to use BCryptPasswordEncoder
    //as a password encoder implementation inside my project
    //So with that we can use the same BCrypt Password Encoder throughout my application
    //whenever the user is registering himself or whenever the user is trying to log into the web application

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }
}
