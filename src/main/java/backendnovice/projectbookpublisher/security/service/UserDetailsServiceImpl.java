/**
 * @author : backendnovice@gmail.com
 * @date : 2023-06-30
 * @desc : Customize UserDetailsService for authentication.
 *
 * changelog :
 */

package backendnovice.projectbookpublisher.security.service;

import backendnovice.projectbookpublisher.member.domain.Member;
import backendnovice.projectbookpublisher.member.repository.MemberRepository;
import backendnovice.projectbookpublisher.security.domain.UserDetailsImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final MemberRepository memberRepository;

    public UserDetailsServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid Member Email: " + email));

        return new UserDetailsImpl(member);
    }
}
