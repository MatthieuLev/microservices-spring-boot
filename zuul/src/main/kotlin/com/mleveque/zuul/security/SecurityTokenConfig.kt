package com.mleveque.zuul.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import javax.servlet.http.HttpServletResponse

// Enable security config. This annotation denotes config for spring security.
@EnableWebSecurity
class SecurityTokenConfig : WebSecurityConfigurerAdapter() {
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
            .authenticationEntryPoint { _, rsp, _ -> rsp.sendError(HttpServletResponse.SC_UNAUTHORIZED) }
            .and()
            // Add a filter to validate the tokens with every request
            .addFilterAfter(JwtTokenAuthenticationFilter(jwtConfig!!), UsernamePasswordAuthenticationFilter::class.java)
            // authorization requests config
            .authorizeRequests()
            // allow all who are accessing "auth" service
            .antMatchers(HttpMethod.POST, jwtConfig.uri).permitAll()
            // must be an admin if trying to access admin area (authentication is also required here)
            .antMatchers("/gallery" + "/admin/**").hasRole("ADMIN")
            // Any other request must be authenticated
            .anyRequest().authenticated()
    }

    @Bean
    fun jwtConfig(): JwtConfig {
        return JwtConfig()
    }
}