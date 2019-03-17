package com.huantt.webpush.conf

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

/**
 * @author huantt on 11/12/18
 */
@Configuration
@EnableWebSecurity
class BasicAuthentication extends WebSecurityConfigurerAdapter {

    @Autowired
    Environment env

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser(env.getProperty('username.admin'))
                .password(passwordEncoder().encode(env.getProperty('password.admin')))
                .authorities(env.getProperty('role.admin'))
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/clients").permitAll()
                .antMatchers("/clients/bought").permitAll()
                .anyRequest().authenticated()

        http.httpBasic()
        http.sessionManagement().disable()
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder()
    }
}
