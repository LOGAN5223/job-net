package com.logan.socialnetwork.security;

import com.logan.socialnetwork.model.Users;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepo){
        return username -> {
            Users user = userRepo.findByUsername(username);
            if(user != null) return user;

            throw new UsernameNotFoundException("Email '" + username + "'not found");
        };
    }

    @Bean
    @Primary
    public SecurityFilterChain filterChain(HttpSecurity http)throws Exception{
        http
//                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(form -> form
                        .loginPage("/login")
                        .permitAll())
                .authorizeHttpRequests((authz) -> authz
                        .requestMatchers("/api/", "/api/**").hasRole("USER")
                        .requestMatchers("/profileSettings/", "/profileSetting/**").hasRole("PREUSER")
                        .requestMatchers("/", "/**").permitAll()
                        .anyRequest()
                        .authenticated());
        return http.build();
    }

}
