/**
 * @author : backendnovice@gmail.com
 * @date : 2023-07-09
 * @desc : Customize UserDetails for authentication.
 *
 * changelog :
 * 2023-07-09 - backendnovice@gmail.com - Add user enabled option
 */

package backendnovice.projectbookpublisher.security.domain;

import backendnovice.projectbookpublisher.member.domain.Member;
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

    public UserDetailsImpl(Member member) {
        this.email = member.getEmail();
        this.password = member.getPassword();
        this.role = member.getRoles().getName();
        this.isEnabled = member.isEnabled();
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
