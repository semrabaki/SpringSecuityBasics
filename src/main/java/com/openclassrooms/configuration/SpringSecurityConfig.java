package com.openclassrooms.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableWebSecurity
public class SpringSecurityConfig  extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.inMemoryAuthentication().
                withUser("springuser").password(passwordEncoder().encode("spring123")).roles("USER").and().
                withUser("springadmin").password(passwordEncoder().encode("admin123")).roles("ADMIN","USER");
    }

    @Override //overriding the spring secuirty normal funcitoni
    public void configure(HttpSecurity http) throws Exception{

        http.authorizeRequests().//we need to add this to setup the roles after it
                antMatchers("/admin").hasRole("ADMIN").
                antMatchers("/user").hasRole("USER").
                anyRequest().
                authenticated().
                and().
                formLogin();





    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();

    }


}
