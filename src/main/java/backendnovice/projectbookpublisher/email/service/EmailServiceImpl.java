/**
 * @author    : backendnovice@gmail.com
 * @date      : 2023-07-21
 * @desc      : 이메일과 관련된 서비스 메소드를 구현하는 클래스.
 * @changelog :
 * 23-07-16 - backendnovice@gmail.com - MemberServiceImpl 에서 분리
 * 23-07-19 - backendnovice@gmail.com - 주석 한글화 수정
 * 23-07-21 - backendnovice@gmail.com - 예외 처리 및 리팩토링 수행
 */

package backendnovice.projectbookpublisher.email.service;

import backendnovice.projectbookpublisher.email.domain.EmailCodeEntity;
import backendnovice.projectbookpublisher.email.dto.EmailCodeDTO;
import backendnovice.projectbookpublisher.email.repository.EmailCodeRepository;
import backendnovice.projectbookpublisher.email.vo.CodeType;
import backendnovice.projectbookpublisher.member.domain.MemberEntity;
import backendnovice.projectbookpublisher.member.dto.MemberDTO;
import backendnovice.projectbookpublisher.member.service.MemberService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.util.NoSuchElementException;

@Slf4j
@Service
public class EmailServiceImpl implements EmailService {
    private final EmailCodeRepository emailCodeRepository;
    private final MemberService memberService;
    private final JavaMailSender javaMailSender;

    public EmailServiceImpl(EmailCodeRepository emailCodeRepository, MemberService memberService
            , JavaMailSender javaMailSender) {
        this.emailCodeRepository = emailCodeRepository;
        this.memberService = memberService;
        this.javaMailSender = javaMailSender;
    }

    @Override
    @Transactional
    public void sendVerifyEmail(String email) {
        try {
            makeVerifyEmail(email);
        }catch (MessagingException e) {
            log.error("메일 전송에서 예외가 발생했습니다.");
        }catch (UnsupportedEncodingException e) {
            log.error("인코딩 예외가 발생했습니다.");
        }
    }

    @Override
    @Transactional
    public boolean checkVerifyEmail(EmailCodeDTO emailCodeDTO) {
        try {
            EmailCodeEntity emailCode = emailCodeRepository.findByKeyAndType(emailCodeDTO.getKey(), emailCodeDTO.getType())
                    .orElseThrow(() -> new NoSuchElementException("키 또는 코드가 일치하지 않습니다"));

            if(emailCode.isExpired()) {
                throw new IllegalArgumentException("이미 만료된 코드입니다");
            }

            if(emailCode.getMember().isEnabled()) {
                emailCode.setExpired(true);

                emailCodeRepository.save(emailCode);

                throw new IllegalArgumentException("이미 활성화된 회원입니다");
            }

            emailCode.setExpired(true);

            emailCode.getMember().setEnabled(true);

            emailCodeRepository.save(emailCode);

            return true;
        }catch (NoSuchElementException | IllegalArgumentException e) {
            log.error(e.getMessage() + ": {}", emailCodeDTO);

            return false;
        }
    }

    /**
     * 인증 메일 재전송을 수행한다.
     * @param email
     *      수신 이메일
     * @throws MessagingException
     *      메시지 연관 에러
     * @throws UnsupportedEncodingException
     *      인코딩 연관 에러
     */
    private void makeVerifyEmail(String email)
            throws MessagingException, UnsupportedEncodingException {
        EmailCodeEntity code = saveCodeToDB(email);

        String to = email;
        String from = "backendnovice@gmail.com";
        String sender = "Book's Pub";
        String title = "[Book's Pub] 이메일 인증을 완료해주세요.";
        String content = "<h2>Book's Pub에 오신것을 환경합니다.</h2>"
                + "<p>회원가입을 완료하려면 아래의 링크를 클릭해주세요.</p>"
                + "<a href=\"" + "http://localhost:8080/email/verify?value=" + code.getKey() + "&type="
                + code.getType() + "\" target=\"_blank\">이메일 인증 완료하기</a>"
                + "<p>감사합니다.<br>by" + sender + "</p>";

        MimeMessage message = javaMailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(from, sender);
        helper.setTo(to);
        helper.setSubject(title);
        helper.setText(content, true);

        javaMailSender.send(message);
    }

    /**
     * 인증 코드를 무작위로 생성하고 DB에 저장한다.
     * @return
     *      회원 이메일
     */
    private EmailCodeEntity saveCodeToDB(String email) {
        String key = RandomStringUtils.randomAlphanumeric(64);

        MemberEntity member = memberService.getMemberByEmail(MemberDTO.builder()
                .email(email)
                .build());

        EmailCodeEntity emailCode = EmailCodeEntity.builder()
                .key(key)
                .type(CodeType.REGISTER)
                .member(member)
                .expired(false)
                .build();

        return emailCodeRepository.save(emailCode);
    }
}
