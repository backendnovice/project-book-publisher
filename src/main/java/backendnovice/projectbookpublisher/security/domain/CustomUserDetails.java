/**
 * @author : backendnovice@gmail.com
 * @date : 2023-06-30
 * @desc : 스프링 시큐리티에서 사용되는 회원 데이터를 제공하는 클래스.
 *
 * 변경 내역 :
 */

package backendnovice.projectbookpublisher.security.domain;

import backendnovice.projectbookpublisher.member.domain.MemberEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {
    private MemberEntity member;

    public CustomUserDetails(MemberEntity member) {
        this.member = member;
    }

    /**
     * 회원 권한을 반환하는 메소드.
     * @return
     *      회원 권한
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();

        authorities.add(new SimpleGrantedAuthority(member.getRoles().get()));

        return authorities;
    }

    /**
     * 회원 이메일을 반환하는 메소드.
     * @return
     *      회원 이메일
     */
    @Override
    public String getUsername() {
        return member.getEmail();
    }

    /**
     * 회원 비밀번호를 반환하는 메소드.
     * @return
     *      회원 비밀번호
     */
    @Override
    public String getPassword() {
        return member.getPassword();
    }

    /**
     * 회원 만료 여부를 반환하는 메소드.
     * @return
     *      회원 만료 여부
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 회원 잠금 여부를 반환하는 메소드.
     * @return
     *      회원 잠금 여부
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 회원 비밀번호의 만료 여부를 반환하는 메소드.
     * @return
     *      비밀번호 만료 여부
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 회원 활성화 여부를 반환하는 메소드.
     * @return
     *      회원 활성화 여부
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
