/**
 * @author : backendnovice@gmail.com
 * @date : 2023-07-09
 * @desc : Configure spring boot starter security.
 *
 * changelog :
 * 2023-07-04 - backendnovice@gmail.com - Granting role to logout, withdraw page
 * 2023-07-05 - backendnovice@gmail.com - Granting role to change-password page
 * 2023-07-06 - backendnovice@gmail.com - Granting role to favicon, image
 * 2023-07-09 - backendnovice@gmail.com - Granting role to verify-email page
 */

package backendnovice.projectbookpublisher.security.config;

import backendnovice.projectbookpublisher.member.vo.RoleType;
import backendnovice.projectbookpublisher.security.service.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final UserDetailsServiceImpl userDetailsServiceImpl;

    public SecurityConfig(UserDetailsServiceImpl userDetailsServiceImpl) {
        this.userDetailsServiceImpl = userDetailsServiceImpl;
    }

    private static final String[] LINK_USER = {
            "/member/profiles", "/member/failure", "/member/logout", "/member/withdraw",
            "/member/support/change-password", "/books/register"
    };
    private static final String[] LINK_PUBLIC = {
            "/member/login", "/member/register", "/api/v1/member/login", "/api/v1/member/register", "/member/failure",
            "/email/verify/**"
    };
    private static final String[] LINK_COMMON = {
            "/css/**", "/js/**", "/layout/**", "/image/**", "favicon.ico", "/", "/home", "/about", "/books/list/**"
    };

    /**
     * Register PasswordEncoder as bean.
     * @return
     *      BCryptPasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Provide spring security filter chains.
     * @return
     *      Security filter chain
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                // Role settings.
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers(LINK_USER).hasRole(RoleType.USER.getName())
                        .requestMatchers(LINK_PUBLIC).anonymous()
                        .requestMatchers(LINK_COMMON).permitAll()
                        .anyRequest().authenticated()
                )
                // Login settings.
                .formLogin((login) -> login
                        .loginPage("/member/login")
                        .successHandler((request, response, authentication) -> {
                            response.sendRedirect("/member/profiles");
                        })
                        .failureUrl("/member/failure")
                        .permitAll()
                )
                // Logout settings.
                .logout((logout) -> logout
                        .logoutUrl("/member/logout")
                        .logoutSuccessHandler((request, response, authentication) -> {
                            response.sendRedirect("/member/login");
                        })
                        .permitAll()
                );

        // Basic form, CSRF, CORS settings.
        httpSecurity
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable);

        return httpSecurity.build();
    }

    /**
     * Register authentication bean using UserDetailsService and PasswordEncoder
     * @return
     *      DaoAuthenticationProvider
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();

        authenticationProvider.setUserDetailsService(userDetailsServiceImpl);
        authenticationProvider.setPasswordEncoder(passwordEncoder());

        return authenticationProvider;
    }
}
