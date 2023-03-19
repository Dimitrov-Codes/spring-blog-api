package com.springboot.blog.config;

import com.springboot.blog.security.JWTAuthenticationEntryPoint;
import com.springboot.blog.security.JWTAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableMethodSecurity
@Configuration
public class SecurityConfig {
     private final JWTAuthenticationEntryPoint authenticationEntryPoint;
     private final JWTAuthenticationFilter authenticationFilter;

     public SecurityConfig(UserDetailsService userDetailsService, JWTAuthenticationEntryPoint authenticationEntryPoint,
                           JWTAuthenticationFilter authenticationFilter) {
          this.authenticationEntryPoint = authenticationEntryPoint;
          this.authenticationFilter = authenticationFilter;
     }

     @Bean
     public static PasswordEncoder passwordEncoder() {
          return new BCryptPasswordEncoder();
     }

     @Bean
     public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
          return authenticationConfiguration.getAuthenticationManager();
     }

     @Bean
     SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

          httpSecurity.csrf().disable()
                  .authorizeHttpRequests(authorize -> authorize
                          .requestMatchers(HttpMethod.GET, "/api/**").permitAll()
                          .requestMatchers("/api/auth/**").permitAll() //allows all users to access the login / register api
                          .anyRequest().authenticated())
                  .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
                  .exceptionHandling(exception -> exception.authenticationEntryPoint(authenticationEntryPoint))
                  .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
          return httpSecurity.build();
     }

//    @Bean
//    public UserDetailsService userDetailsService() {
//        UserDetails adnan = User.builder().username("adnan").password(passwordEncoder().encode("abxca")).roles("ADMIN").build();
//        UserDetails npc = User.builder().username("abc").password(passwordEncoder().encode("abc123")).roles("USER").build();
//        return new InMemoryUserDetailsManager(adnan, npc);
//
//    }
}
