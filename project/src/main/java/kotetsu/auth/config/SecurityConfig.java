package kotetsu.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import kotetsu.auth.service.ClientDetailsService;
import kotetsu.auth.service.MyUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final MyUserDetailsService myUserDetailsService;

    private final ClientDetailsService clientDetailsService;

    public SecurityConfig(
        final MyUserDetailsService myUserDetailsService,
        final ClientDetailsService clientDetailsService
    ) {
        this.myUserDetailsService = myUserDetailsService;
        this.clientDetailsService = clientDetailsService;
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private AuthenticationProvider createAuthenticationProvider(
        final UserDetailsService userDetailsService,
        final PasswordEncoder passwordEncoder
    ) {
        final DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return authenticationProvider;
    }

    @Bean
    @Order(1)
    public SecurityFilterChain basicAuthfilterChain(HttpSecurity http) throws Exception {
    http
        .securityMatcher("/api/oauth2/**")
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/api/oauth2/certs").permitAll()
            .anyRequest().authenticated()
        )
        .httpBasic(basic -> basic
            .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
        )
        .exceptionHandling(exception -> exception
            .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
        )
        .authenticationProvider(createAuthenticationProvider(
            clientDetailsService,
            getPasswordEncoder()
        ))
        .sessionManagement(session -> session
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        )
        .csrf(csrf -> csrf.disable());
        return http.build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain loginSessionfilterChain(HttpSecurity http) throws Exception {
    http
        .securityMatcher("/**")
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()
            .anyRequest().authenticated()
        )
        .authenticationProvider(createAuthenticationProvider(
            myUserDetailsService,
            getPasswordEncoder()
        ))
        .formLogin(form -> form
            .loginPage("/login")
            .successHandler(mySuccessHandler())
            .permitAll()
        )
        .logout(logout -> logout
            .logoutUrl("/logout")
            .logoutSuccessUrl("/login?logout")
            .invalidateHttpSession(true)
            .deleteCookies("JSESSIONID")
        )
        .sessionManagement(session -> session.
            sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
            .maximumSessions(1)
            .maxSessionsPreventsLogin(false)
        );

        return http.build();
    }

    @Bean
    public AuthenticationSuccessHandler mySuccessHandler() {
        return (request, response, authentication) -> {
            SavedRequest savedRequest = 
                new HttpSessionRequestCache().getRequest(request, response);

            if (savedRequest != null) {
                String targetUrl = savedRequest.getRedirectUrl();
                response.sendRedirect(targetUrl);
                return;
            }

            response.sendRedirect("/");
        };
    }
}
