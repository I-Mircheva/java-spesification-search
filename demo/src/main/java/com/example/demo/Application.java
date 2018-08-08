package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.sql.DataSource;

@SpringBootApplication
public class Application {

    @Bean
    public WebSecurityConfigurerAdapter webSecurityConfig(DataSource dataSource) {
        return new WebSecurityConfigurerAdapter() {
            @Override
            protected void configure(HttpSecurity http) throws Exception {
                http.authorizeRequests()
                        .antMatchers("/h2-console/**").permitAll()
                        .and().formLogin()//enable form login instead of basic login
                        .and().csrf().ignoringAntMatchers("/h2-console/**")//don't apply CSRF protection to /h2-console
                        .and().headers().frameOptions().sameOrigin();//allow use of frame to same origin urls
            }

            @Override
            protected void configure(AuthenticationManagerBuilder builder) throws Exception {
                builder.jdbcAuthentication()
                        .dataSource(dataSource);
            }
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

}
