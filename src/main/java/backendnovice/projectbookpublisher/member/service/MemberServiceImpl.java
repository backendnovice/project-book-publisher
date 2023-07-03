/**
 * @author : backendnovice@gmail.com
 * @date : 2023-06-30
 * @desc : 회원 관련 메소드를 구현하는 클래스.
 *
 * 변경 내역 :
 * 2023-06-29 - backendnovice@gmail.com - doRegister() 리턴 타입 수정
 * 2023-06-30 - backendnovice@gmail.com - 코드화 주석 변경 내역 추가
 */

package backendnovice.projectbookpublisher.member.service;

import backendnovice.projectbookpublisher.member.dto.MemberDTO;
import backendnovice.projectbookpublisher.member.domain.MemberEntity;
import backendnovice.projectbookpublisher.member.repository.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberServiceImpl(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public boolean doRegister(MemberDTO memberDTO) {
        String password = memberDTO.getPassword();

        // 패스워드가 같고 패턴과 일치할 경우.
        if (checkPasswordPattern(password)) {
            memberDTO.setPassword(passwordEncoder.encode(password));
            memberRepository.save(dtoToEntity(memberDTO));
            return true;
        }

        // 패스워드가 다르거나 패턴과 일치하지 않을 경우.
        return false;
    }

    @Override
    public boolean validateLogin(MemberDTO memberDTO) {
        Optional<MemberEntity> member = memberRepository.findByEmail(memberDTO.getEmail());

        // 이메일과 일치하는 데이터가 존재할경우, 패스워드를 비교하여 반환한다.
        return member.filter(memberEntity -> passwordEncoder
                .matches(memberDTO.getPassword(), memberEntity.getPassword()))
                .isPresent();
    }

    @Override
    public boolean validateRegister(MemberDTO memberDTO) {
        return memberRepository.existsByEmail(memberDTO.getEmail());
    }

    /**
     * 비밀번호 조건을 검사하는 메소드.
     * @param password
     *      회원 비밀번호
     * @return
     *      조건 일치 여부
     */
    private boolean checkPasswordPattern(String password) {
        // 최소 한개의 대소문자 + 최소 한개의 숫자 + 최소 8자.
        Pattern pattern = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$");
        Matcher matcher = pattern.matcher(password);

        return matcher.find();
    }
}
