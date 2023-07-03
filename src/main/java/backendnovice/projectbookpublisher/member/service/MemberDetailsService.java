/**
 * @author : backendnovice@gmail.com
 * @date : 2023-06-30
 * @desc : 스프링 시큐리티 회원 데이터를 불러오는 클래스.
 *
 * 변경 내역 :
 */

package backendnovice.projectbookpublisher.member.service;

import backendnovice.projectbookpublisher.member.domain.MemberEntity;
import backendnovice.projectbookpublisher.member.repository.MemberRepository;
import backendnovice.projectbookpublisher.member.domain.MemberDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MemberDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;

    public MemberDetailsService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /**
     * 회원 이메일을 기반으로 회원을 탐색하는 메소드.
     * @param email 
     *      회원 이메일
     * @return
     *      회원 데이터
     * @throws UsernameNotFoundException
     *      회원 데이터를 찾을 수 없는 경우 발생하는 에러
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        MemberEntity member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid Member Email: " + email));

        return new MemberDetails(member);
    }
}
