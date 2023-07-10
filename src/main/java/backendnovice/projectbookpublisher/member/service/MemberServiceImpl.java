/**
 * @author : backendnovice@gmail.com
 * @date : 2023-07-10
 * @desc : 회원 관련 메소드를 구현하는 클래스.
 *
 * 변경 내역 :
 * 2023-06-29 - backendnovice@gmail.com - doRegister() 리턴 타입 수정
 * 2023-06-30 - backendnovice@gmail.com - 코드화 주석 변경 내역 추가
 * 2023-07-04 - backendnovice@gmail.com - 회원가입 탈퇴 메소드 구현
 * 2023-07-05 - backendnovice@gmail.com - 비밀번호 수정 메소드 구현
 * 2023-07-09 - backendnovice@gmail.com - 이메일 인증 메소드 구현
 * 2023-07-10 - backendnovice@gmail.com - 이메일 재인증 메소드 구현
 */

package backendnovice.projectbookpublisher.member.service;

import backendnovice.projectbookpublisher.member.domain.CodeEntity;
import backendnovice.projectbookpublisher.member.domain.CodeType;
import backendnovice.projectbookpublisher.member.dto.MemberDTO;
import backendnovice.projectbookpublisher.member.domain.MemberEntity;
import backendnovice.projectbookpublisher.member.repository.CodeRepository;
import backendnovice.projectbookpublisher.member.repository.MemberRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final CodeRepository codeRepository;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender javaMailSender;

    public MemberServiceImpl(MemberRepository memberRepository, CodeRepository codeRepository
            , PasswordEncoder passwordEncoder, JavaMailSender javaMailSender) {
        this.memberRepository = memberRepository;
        this.codeRepository = codeRepository;
        this.passwordEncoder = passwordEncoder;
        this.javaMailSender = javaMailSender;
    }

    @Override
    public boolean doRegister(MemberDTO memberDTO) {
        String email = memberDTO.getEmail();
        String password = memberDTO.getPassword();

        // 패스워드가 패턴과 일치할 경우.
        if (checkPasswordPattern(password)) {
            memberDTO.setPassword(passwordEncoder.encode(password));
            MemberEntity member = memberRepository.save(dtoToEntity(memberDTO));

            // 인증 메일 전송.
            try {
                sendVerifyEmail(member);
            }catch (MessagingException | UnsupportedEncodingException e) {
                e.printStackTrace();
                return false;
            }

            return true;
        }

        // 패스워드가 다르거나 패턴과 일치하지 않을 경우.
        return false;
    }

    @Override
    @Transactional
    public boolean doWithdraw(String email) {
        // 제거된 컬럼 개수가 0보다 클 경우 true 값 반환한다.
        return memberRepository.deleteByEmail(email) > 0;
    }

    @Override
    public void doChangePassword(String email, String password) {
        MemberEntity member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Member Email : " + email));

        member.setPassword(passwordEncoder.encode(password));

        memberRepository.save(member);
    }

    @Override
    public void doResendEmail(String email) {
        MemberEntity member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Member Email : " + email));

        try {
            sendVerifyEmail(member);
        }catch (MessagingException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
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

    @Override
    public boolean validateEmailVerification(String value, CodeType type) {
        CodeEntity code = codeRepository.findByValueAndType(value, type)
                .orElseThrow(IllegalArgumentException::new);
        MemberEntity member = code.getMember();

        // 코드를 만료하고 저장한다.
        if(code.isValid()) {
            code.setIsValid(false);
            codeRepository.save(code);
        }

        // 회원을 활성화하고 저장한다.
        if(!member.isEnabled()) {
            member.setIsEnabled(true);
            memberRepository.save(member);
            return true;
        }

        return false;
    }

    /**
     * 인증 메일 전송 메소드.
     * @param member
     *      회원 엔티티
     * @throws MessagingException
     *      메시지 연관 에러
     * @throws UnsupportedEncodingException
     *      인코딩 연관 에러
     */
    private void sendVerifyEmail(MemberEntity member)
            throws MessagingException, UnsupportedEncodingException {
        CodeEntity code = generateVerifyCode(member);
        String email = member.getEmail();
        String from = "backendnovice@gmail.com";
        String sender = "Book's Pub";
        String title = "Please verify your Registration";
        String content = "<h2>Welcome to Book's Publish Project</h2>"
                + "<p>Please click the link below to complete Registration.</p>"
                + "<a href=\"" + "http://localhost:8080/member/verify?value=" + code.getValue() + "&type="
                + code.getType() + "\" target=\"_blank\">Verify</a>"
                + "<p>Thank you.<br>by" + sender + "</p>";

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(from, sender);
        helper.setTo(email);
        helper.setSubject(title);
        helper.setText(content, true);

        javaMailSender.send(message);
    }

    /**
     * 랜덤 코드를 생성하는 메소드.
     * @return
     *      랜덤 코드
     */
    private CodeEntity generateVerifyCode(MemberEntity member) {
        String newCode = RandomStringUtils.randomAlphanumeric(64);

        CodeEntity code = CodeEntity.builder()
                .type(CodeType.REGISTER)
                .value(newCode)
                .isValid(true)
                .member(member).build();

        return codeRepository.save(code);
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
