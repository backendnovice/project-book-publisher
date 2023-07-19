/**
 * @author    : backendnovice@gmail.com
 * @date      : 2023-07-19
 * @desc      : UserDetails를 구현하는 클래스.
 * @changelog :
 * 23-07-09 - backendnovice@gmail.com - 회원 활성화 메소드 수정
 * 23-07-19 - backendnovice@gmail.com - 주석 한글화 수정
 */

package backendnovice.projectbookpublisher.security.domain;

import backendnovice.projectbookpublisher.member.domain.MemberEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserDetailsImpl implements UserDetails {
    private final String PREFIX = "ROLE_";
    private String email;
    private String password;
    private String role;
    private boolean isEnabled;

    public UserDetailsImpl(MemberEntity memberEntity) {
        this.email = memberEntity.getEmail();
        this.password = memberEntity.getPassword();
        this.role = memberEntity.getRoles().getName();
        this.isEnabled = memberEntity.isEnabled();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();

        authorities.add(new SimpleGrantedAuthority(PREFIX + role));

        return authorities;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }
}
