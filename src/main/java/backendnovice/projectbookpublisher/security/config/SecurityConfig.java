/**
 * @author : backendnovice@gmail.com
 * @date : 2023-06-30
 * @desc : 스프링 시큐리티를 설정하는 클래스.
 *
 * 변경 내역 :
 */

package backendnovice.projectbookpublisher.security.config;

import backendnovice.projectbookpublisher.member.domain.MemberRole;
import backendnovice.projectbookpublisher.security.service.CustomDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final CustomDetailsService customDetailsService;

    public SecurityConfig(CustomDetailsService customDetailsService) {
        this.customDetailsService = customDetailsService;
    }

    private static final String[] LINK_USER = {
            "member/profiles"
    };

    private static final String[] LINK_PUBLIC = {
            "/member/login", "/member/register", "/api/v1/member/login", "/api/v1/member/register"
    };

    private static final String[] LINK_RESOURCE = {
            "/css/**", "/js/**", "/layout/**"
    };

    /**
     * BCrypt 암호화 및 복호화 객체를 제공하는 메소드.
     * @return
     *      암호화 기능을 제공하는 객체
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 스프링 시큐리티 필터를 엮어서 제공하는 메소드.
     * @return
     *      필터 체인
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers(LINK_USER).hasRole(MemberRole.USER.get())
                        .requestMatchers(LINK_PUBLIC).anonymous()
                        .requestMatchers(LINK_RESOURCE).permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(login -> login
                        .loginPage("/member/login")
                        .successHandler(((request, response, authentication) -> {
                            response.sendRedirect("/member/profiles");
                        }))
                        .failureUrl("/member/loginFailure")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/member/logout")
                        .logoutSuccessUrl("/member/login")
                        .permitAll()
                );

        httpSecurity
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable);

        return httpSecurity.build();
    }

    /**
     * 회원 아이디와 암호를 인증하는 메소드.
     * @return
     *      인증 객체
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();

        authenticationProvider.setUserDetailsService(customDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());

        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
