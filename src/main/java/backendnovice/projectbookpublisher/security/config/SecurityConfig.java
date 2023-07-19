/**
 * @author    : backendnovice@gmail.com
 * @date      : 2023-07-19
 * @desc      : Spring boot starter security를 설정하는 클래스.
 * @changelog :
 * 23-06-29 - backendnovice@gmail.com - 로그아웃, 회원탈퇴 권한 설정
 * 23-07-04 - backendnovice@gmail.com - 비밀번호 변경 권한 설정
 * 23-07-06 - backendnovice@gmail.com - favicon, 이미지 경로 권한 설정
 * 23-07-06 - backendnovice@gmail.com - 이메일 인증 뷰 권한 설정
 * 23-07-16 - backendnovice@gmail.com - 책 조회, 등록 뷰 권한 설정
 * 23-07-19 - backendnovice@gmail.com - 책 목록 뷰 권한 설정
 * 23-07-19 - backendnovice@gmail.com - 주석 한글화 수정
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
    private static final String[] LINK_ANONYMOUS = {
            "/member/login", "/member/register", "/api/v1/member/login", "/api/v1/member/register", "/member/failure",
            "/email/verify/**"
    };
    private static final String[] LINK_COMMON = {
            "/css/**", "/js/**", "/layout/**", "/image/**", "favicon.ico", "/", "/home", "/about", "/books/list/**",
            "/books/read/**", "/books/list/**"
    };

    /**
     * PasswordEncoder를 Bean으로 등록한다.
     * @return
     *      BCryptPasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * SecurityFilterChain을 Bean으로 등록한다.
     * @return
     *      SecurityFilterChain
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                // 역할 설정.
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers(LINK_USER).hasRole(RoleType.USER.getName())
                        .requestMatchers(LINK_ANONYMOUS).anonymous()
                        .requestMatchers(LINK_COMMON).permitAll()
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

        // 베이직 폼, CSRF, CORS 비활성화.
        httpSecurity
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable);

        return httpSecurity.build();
    }

    /**
     * DaoAuthenticationProvider를 Bean으로 등록한다.
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
