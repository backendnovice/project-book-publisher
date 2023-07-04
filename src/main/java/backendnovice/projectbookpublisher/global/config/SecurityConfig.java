/**
 * @author : backendnovice@gmail.com
 * @date : 2023-07-04
 * @desc : 스프링 시큐리티를 설정하는 클래스.
 *
 * 변경 내역 :
 * 2023-07-04 - backendnovice@gmail.com - 로그아웃 및 회원탈퇴 권한 매핑
 */

package backendnovice.projectbookpublisher.global.config;

import backendnovice.projectbookpublisher.member.domain.MemberRole;
import backendnovice.projectbookpublisher.member.service.MemberDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final MemberDetailsService memberDetailsService;

    public SecurityConfig(MemberDetailsService memberDetailsService) {
        this.memberDetailsService = memberDetailsService;
    }

    private static final String[] LINK_USER = {
            "/member/profiles", "/member/failure", "/member/logout", "/member/withdraw"
    };
    private static final String[] LINK_PUBLIC = {
            "/member/login", "/member/register", "/api/v1/member/login", "/api/v1/member/register", "/member/failure"
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
    public PasswordEncoder passwordEncoder() {
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
                // 롤 매핑 설정.
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers(LINK_USER).hasRole(MemberRole.USER.getName())
                        .requestMatchers(LINK_PUBLIC).anonymous()
                        .requestMatchers(LINK_RESOURCE).permitAll()
                        .anyRequest().authenticated()
                )
                // 로그인 설정.
                .formLogin((login) -> login
                        .loginPage("/member/login")
                        .successHandler((request, response, authentication) -> {
                            response.sendRedirect("/member/profiles");
                        })
                        .failureUrl("/member/failure")
                        .permitAll()
                )
                // 로그아웃 설정.
                .logout((logout) -> logout
                        .logoutUrl("/member/logout")
                        .logoutSuccessHandler((request, response, authentication) -> {
                            response.sendRedirect("/member/login");
                        })
                        .permitAll()
                );

        // 기본 폼, CSRF, CORS 비활성화.
        httpSecurity
                .httpBasic(httpBasic -> httpBasic.disable())
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.disable());

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

        authenticationProvider.setUserDetailsService(memberDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());

        return authenticationProvider;
    }
}
