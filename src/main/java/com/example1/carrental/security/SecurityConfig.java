package com.example1.carrental.security;

import com.example1.carrental.filter.CustomAuthenticationFilter;
import com.example1.carrental.filter.CustomAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

        private final UserDetailsService userDetailsService;
        private final PasswordEncoder passwordEncoder;

        private static final String[] AUTH_WHITELIST = {
                "**/swagger-resources/**",
                "/swagger-ui.html",
                "/v2/api-docs",
                "/webjars/**"
        };


        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
                auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
        }


        @Override
        protected void configure(HttpSecurity http) throws Exception {
                http.csrf().disable();
                http.sessionManagement().sessionCreationPolicy(STATELESS);
                http.authorizeRequests().antMatchers(AUTH_WHITELIST).permitAll()
                        .and().httpBasic().authenticationEntryPoint(swaggerAuthenticationEntryPoint());
                http.authorizeRequests().antMatchers("/login").permitAll()
                        .antMatchers("/admin/**").hasAnyAuthority("ROLE_ADMIN")
                        .antMatchers("/cars/**").hasAnyAuthority("ROLE_USER")
                        .anyRequest().authenticated()
                        .and().logout().logoutSuccessUrl("/login");
                http.addFilter(new CustomAuthenticationFilter(authenticationManagerBean()));
                http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
        }


        @Bean
        public BasicAuthenticationEntryPoint swaggerAuthenticationEntryPoint() {
                BasicAuthenticationEntryPoint entryPoint = new BasicAuthenticationEntryPoint();
                entryPoint.setRealmName("Swagger Realm");
                return entryPoint;
        }


        @Bean
        @Override
        public AuthenticationManager authenticationManagerBean() throws Exception {
                return super.authenticationManagerBean();
        }

}
