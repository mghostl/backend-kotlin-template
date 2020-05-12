package com.mghostl.core.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.factory.PasswordEncoderFactories

@Profile("local")
@Configuration
@EnableWebSecurity
class LocalSecurityConfiguration : WebSecurityConfigurerAdapter() {

    @Value("\${spring.security.user.name}")
    lateinit var username: String

    @Value("\${spring.security.user.password}")
    lateinit var password: String

    override fun configure(http: HttpSecurity) {
        http
            .authorizeRequests()
            .antMatchers("/h2-console/**", "/actuator/**", "/admin/**").hasRole("ADMIN")
            .and().httpBasic()
            // .and().headers().disable() // only for local usage !!
            .and()
            .csrf().disable()
    }

    override fun configure(auth: AuthenticationManagerBuilder) {
        val encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder()
        auth
            .inMemoryAuthentication()
            .withUser(username)
            .password(encoder.encode(password))
            .roles("ADMIN")
    }
}