/**
 * @author    : backendnovice@gmail.com
 * @date      : 2023-07-20
 * @desc      : 회원과 관련된 서비스 메소드를 구현하는 클래스.
 * @changelog :
 * 23-06-29 - backendnovice@gmail.com - 회원가입 반환 타입 변경 (String -> boolean)
 * 23-07-04 - backendnovice@gmail.com - 회원탈퇴 메소드 구현
 * 23-07-05 - backendnovice@gmail.com - 비밀번호 변경 메소드 구현
 * 23-07-19 - backendnovice@gmail.com - 주석 한글화 수정
 * 23-07-20 - backendnovice@gmail.com - 코드 리팩토링 수행
 * 23-07-22 - backendnovice@gmail.com - 이메일 서비스 종속성 제거
 */

package backendnovice.projectbookpublisher.member.service;

import backendnovice.projectbookpublisher.member.dto.MemberDTO;
import backendnovice.projectbookpublisher.member.domain.MemberEntity;
import backendnovice.projectbookpublisher.member.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberServiceImpl(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public boolean register(MemberDTO memberDTO) {
        // 패스워드가 패턴에 일치할 경우, 순서대로 암호화 -> DB 저장 -> 메일 전송을 수행한다.
        if (checkPasswordPattern(memberDTO)) {
            memberDTO.setPassword(passwordEncoder.encode(memberDTO.getPassword()));

            memberRepository.save(convertToEntity(memberDTO));

            return true;
        }

        return false;
    }

    @Override
    @Transactional
    public void withdraw(MemberDTO memberDTO) {
        try {
            memberRepository.deleteByEmail(memberDTO.getEmail()).orElseThrow(NoSuchElementException::new);
        }catch (NoSuchElementException e) {
            log.error("존재하지 않는 이메일입니다: {}", memberDTO.getEmail());
        }
    }

    @Override
    @Transactional
    public void changePassword(MemberDTO memberDTO) {
        try {
            MemberEntity member = getMemberByEmail(memberDTO);

            member.setPassword(passwordEncoder.encode(memberDTO.getPassword()));

            memberRepository.save(member);
        }catch(NullPointerException e) {
            log.error("비밀번호가 null 값을 가집니다: {}", memberDTO.getPassword());
        }
    }

    @Override
    public boolean checkLogin(MemberDTO memberDTO) {
        MemberEntity member = getMemberByEmail(memberDTO);

        try {
            boolean isCorrect = passwordEncoder.matches(memberDTO.getPassword(), member.getPassword());

            return isCorrect;
        }catch (NullPointerException e) {
            log.error("비밀번호가 null 값을 가집니다: {}", member.getPassword());

            return false;
        }
    }

    @Override
    public boolean checkRegister(MemberDTO memberDTO) {
        return memberRepository.existsByEmail(memberDTO.getEmail());
    }

    @Override
    public MemberEntity getMemberByEmail(MemberDTO memberDTO) {
        try {
            MemberEntity member = memberRepository.findByEmail(memberDTO.getEmail())
                    .orElseThrow(NoSuchElementException::new);

            return member;
        }catch(NoSuchElementException e) {
            log.error("존재하지 않는 이메일입니다: {}", memberDTO.getEmail());

            return null;
        }
    }

    /**
     * 비밀번호 조건을 검사한다.
     * @param memberDTO
     *      MemberDTO
     * @return
     *      검사 결과
     */
    private boolean checkPasswordPattern(MemberDTO memberDTO) {
        String password = memberDTO.getPassword();

        // 비밀번호는 8 자리 이상의 최소 하나의 대소문자, 특수문자를 포함하는 문자열이여야 한다.
        Pattern pattern = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$");

        Matcher matcher = pattern.matcher(password);

        boolean isCorrect = matcher.find();

        try {
            if(password == null || password.isEmpty()) {
                throw new IllegalArgumentException("비밀번호가 null 값을 가지거나 비어있습니다.");
            }
            if(!isCorrect) {
                throw new IllegalArgumentException("비밀번호가 패턴과 일치하지 않습니다");
            }
        }catch (IllegalArgumentException e) {
            log.error(e.getMessage());
        }

        return isCorrect;
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
