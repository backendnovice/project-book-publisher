/**
 * @author : backendnovice@gmail.com
 * @date : 2023-06-29
 * @desc : 회원 관련 메소드를 구현하는 클래스.
 */

package backendnovice.projectbookpublisher.member.service;

import backendnovice.projectbookpublisher.member.dto.MemberDTO;
import backendnovice.projectbookpublisher.member.domain.MemberEntity;
import backendnovice.projectbookpublisher.member.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;

    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public boolean doRegister(MemberDTO memberDTO) {
        String password = memberDTO.getPassword();

        // 패스워드가 조건과 일치할 경우, 삽입하고 로그인 페이지 리다이렉션.
        if (checkPasswordPattern(password)) {
            memberRepository.save(dtoToEntity(memberDTO));
            return true;
        }

        // 실패할 경우 회원가입 페이지 리다이렉션.
        return false;
    }

    @Override
    public boolean validateLogin(MemberDTO memberDTO) {
        Optional<MemberEntity> member = memberRepository.findByEmail(memberDTO.getEmail());

        // 이메일과 일치하는 데이터가 존재할경우, 패스워드를 비교하여 반환한다.
        if (member.isPresent())
            return member.get().getPassword().equals(memberDTO.getPassword());

        return false;
    }

    @Override
    public boolean validateRegister(MemberDTO memberDTO) {
        boolean isExists = memberRepository.existsByEmail(memberDTO.getEmail());

        return isExists;
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
