package com.library.library.security;

import com.library.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

@Configuration
public class SecurityConfig {
    CustomAuthenticationSuccessHandler successHandler;
    //  private AuthenticationSuccessHandler successHandler;
    private static final String[] PUBLIC_MATCHERS = {
            "/css/**",
            "/fonts/**",
            "/image/**",
            "/images/**",
            "/js/**",
            // "/",
            "/newUser",
            "/forgetPassword",
            "/login",
            "/bookshelf",
            "/bookDetail/**",
            "/hours",
            "/faq",
            "/searchByCategory",
            "/searchBook/**",
            "/sorting/**"
    };

    @Autowired
    public SecurityConfig(CustomAuthenticationSuccessHandler successHandler) {
        this.successHandler = successHandler;
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(UserService userService) {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userService); //set the custom user details service
    //    auth.setPasswordEncoder(passwordEncoder()); //set the password encoder - bcrypt
        return auth;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(configurer ->
                        configurer
                                .requestMatchers(PUBLIC_MATCHERS).permitAll()
                                .requestMatchers("/").hasRole("USER")
                                .requestMatchers("/admin/**").hasRole("ADMIN")
                                .anyRequest().authenticated()
                )

                .formLogin(form ->
                        form
                                .loginPage("/")//.loginPage("/showMyLoginPage")
                               // .loginPage("/showMyLoginPage")
                                .successHandler(successHandler)
                                .loginProcessingUrl("/login")//.loginProcessingUrl("/authenticateTheUser")
                                .failureHandler(new SimpleUrlAuthenticationFailureHandler("/login?error"))
                                .permitAll()
                )
                .logout(logout -> logout.permitAll() )
                .exceptionHandling(configurer ->
                        configurer.accessDeniedPage("/access-denied")
                );

        return http.build();
    }


}
