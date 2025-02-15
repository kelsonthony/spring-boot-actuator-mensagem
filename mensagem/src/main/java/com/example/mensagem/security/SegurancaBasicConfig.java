package com.example.mensagem.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SegurancaBasicConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JPAUsuarioDetailsService userDetailsService;
    
    @Autowired
    private InMemoryUserDetailsManager inMemoryUserDetailsManager;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
        authenticationManagerBuilder
        		.userDetailsService(inMemoryUserDetailsManager)
        		.passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(HttpMethod.GET,"/mensagens/**","/paises").permitAll()
                .antMatchers(HttpMethod.POST, "/mensagens").hasAnyRole("CLIENTE","ADMIN")
                .antMatchers(HttpMethod.GET, "/actuator/health", "/actuator/info").permitAll()
                .antMatchers("/actuator/**").hasRole("ACTUATOR")
            .and()
                .csrf().disable()
                .httpBasic();
    }

}
