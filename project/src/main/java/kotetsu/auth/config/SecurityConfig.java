package kotetsu.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import kotetsu.auth.filter.BearerAuthenticationFilter;
import kotetsu.auth.service.MyUserDetailsService;
import kotetsu.auth.util.JwtClaimsGetter;

@Configuration
@EnableMethodSecurity(jsr250Enabled = true)
public class SecurityConfig {
    final JwtClaimsGetter jwtClaimsGetter;

    final MyUserDetailsService userDetailsService;

    public SecurityConfig(final MyUserDetailsService userDetailsService, final JwtClaimsGetter jwtClaimsGetter) {
        this.userDetailsService = userDetailsService;
        this.jwtClaimsGetter = jwtClaimsGetter;
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.getSharedObject(AuthenticationManagerBuilder.class)
            .userDetailsService(userDetailsService)
            .passwordEncoder(passwordEncoder())
            .and()
            .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .httpBasic(Customizer.withDefaults())
            .addFilterAfter(new BearerAuthenticationFilter(jwtClaimsGetter), UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }
}
