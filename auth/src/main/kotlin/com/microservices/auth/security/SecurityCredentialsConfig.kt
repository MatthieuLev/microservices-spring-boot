package com.microservices.auth.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import javax.servlet.http.HttpServletResponse


@EnableWebSecurity    // Enable security config. This annotation denotes config for spring security.
class SecurityCredentialsConfig : WebSecurityConfigurerAdapter() {

    @Autowired
    private val userDetailsService: UserDetailsService? = null

    @Autowired
    private val jwtConfig: JwtConfig? = null

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http
            .csrf().disable()
            // make sure we use stateless session; session won't be used to store user's state.
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            // handle an authorized attempts
            .exceptionHandling()
            .authenticationEntryPoint { req, rsp, e -> rsp.sendError(HttpServletResponse.SC_UNAUTHORIZED) }
            .and()
            // Add a filter to validate user credentials and add token in the response header

            // What's the authenticationManager()?
            // An object provided by WebSecurityConfigurerAdapter, used to authenticate the user passing user's credentials
            // The filter needs this auth manager to authenticate the user.
            .addFilter(JwtUsernameAndPasswordAuthenticationFilter(authenticationManager(), jwtConfig!!))
            .authorizeRequests()
            // allow all POST requests
            .antMatchers(HttpMethod.POST, jwtConfig.uri).permitAll()
            // any other requests must be authenticated
            .anyRequest().authenticated()
    }

    // Spring has UserDetailsService interface, which can be overriden to provide our implementation for fetching user from database (or any other source).
    // The UserDetailsService object is used by the auth manager to load the user from database.
    // In addition, we need to define the password encoder also. So, auth manager can compare and verify passwords.
    @Throws(Exception::class)
    override fun configure(auth: AuthenticationManagerBuilder?) {
        auth!!.userDetailsService<UserDetailsService>(userDetailsService).passwordEncoder(passwordEncoder())
    }

    @Bean
    fun jwtConfig(): JwtConfig {
        return JwtConfig()
    }

    @Bean
    fun passwordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }
}