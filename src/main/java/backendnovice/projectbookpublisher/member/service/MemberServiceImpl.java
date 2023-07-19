/**
 * @author    : backendnovice@gmail.com
 * @date      : 2023-07-19
 * @desc      : 회원과 관련된 서비스 메소드를 구현하는 클래스.
 * @changelog :
 * 23-06-29 - backendnovice@gmail.com - 회원가입 반환 타입 변경 (String -> boolean)
 * 23-07-04 - backendnovice@gmail.com - 회원탈퇴 메소드 구현
 * 23-07-05 - backendnovice@gmail.com - 비밀번호 변경 메소드 구현
 * 23-07-19 - backendnovice@gmail.com - 주석 한글화 수정
 */

package backendnovice.projectbookpublisher.member.service;

import backendnovice.projectbookpublisher.email.service.EmailService;
import backendnovice.projectbookpublisher.member.dto.MemberDTO;
import backendnovice.projectbookpublisher.member.domain.MemberEntity;
import backendnovice.projectbookpublisher.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public MemberServiceImpl(MemberRepository memberRepository, PasswordEncoder passwordEncoder
            , EmailService emailService) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    @Override
    public boolean register(MemberDTO memberDTO) {
        String email = memberDTO.getEmail();
        String password = memberDTO.getPassword();

        if (checkPasswordPattern(password)) {
            memberDTO.setPassword(passwordEncoder.encode(password));
            memberRepository.save(convertToEntity(memberDTO));
            emailService.sendVerifyEmail(email);

            return true;
        }

        return false;
    }

    @Override
    @Transactional
    public boolean withdraw(String email) {
        // If removed columns count greater than 0, return true.
        return memberRepository.deleteByEmail(email) > 0;
    }

    @Override
    public void changePassword(MemberDTO memberDTO) {
        MemberEntity memberEntity = memberRepository.findByEmail(memberDTO.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid Member Email"));

        memberEntity.setPassword(passwordEncoder.encode(memberDTO.getPassword()));

        memberRepository.save(memberEntity);
    }

    @Override
    public boolean checkLogin(MemberDTO memberDTO) {
        Optional<MemberEntity> member = memberRepository.findByEmail(memberDTO.getEmail());

        return member.filter(memberEntity -> passwordEncoder
                        .matches(memberDTO.getPassword(), memberEntity.getPassword())).isPresent();
    }

    @Override
    public boolean checkRegister(MemberDTO memberDTO) {
        return memberRepository.existsByEmail(memberDTO.getEmail());
    }

    @Override
    public MemberEntity getMemberByEmail(String email) {
        Optional<MemberEntity> member = memberRepository.findByEmail(email);

        if(member.isPresent()) {
            return member.get();
        }else {
            throw new IllegalArgumentException("There is no Member with ID.");
        }
    }

    /**
     * 비밀번호 조건을 검사한다.
     * @param password
     *      회원 비밀번호
     * @return
     *      검사 결과
     */
    private boolean checkPasswordPattern(String password) {
        // 최소 8자의 비밀번호. 대소문자, 특수문자를 하나씩 포함하여야 함.
        Pattern pattern = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$");
        Matcher matcher = pattern.matcher(password);

        return matcher.find();
    }

    /**
     * MemberDTO를 MemberEntity로 변환한다.
     * @param memberDTO
     *      MemberDTO
     * @return
     *      MemberEntity
     */
    private MemberEntity convertToEntity(MemberDTO memberDTO) {
        return MemberEntity.builder()
                .email(memberDTO.getEmail())
                .password(memberDTO.getPassword())
                .phone(memberDTO.getPhone()).build();
    }

    /**
     * MemberEntity를 MemberDTO로 변환한다.
     * @param memberEntity
     *      MemberEntity
     * @return
     *      MemberDTO
     */
    private MemberDTO convertToDto(MemberEntity memberEntity) {
        return MemberDTO.builder()
                .id(memberEntity.getId())
                .email(memberEntity.getEmail())
                .password(memberEntity.getPassword())
                .phone(memberEntity.getPhone()).build();
    }
}
